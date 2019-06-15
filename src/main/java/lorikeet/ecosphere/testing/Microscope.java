package lorikeet.ecosphere.testing;

import lorikeet.Opt;
import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.meta.Dbg;
import lorikeet.ecosphere.meta.ParameterMeta;
import lorikeet.error.CouldNotFindConnectMethodOnCell;
import lorikeet.error.CouldNotFindInvokeMethodOnCell;
import lorikeet.error.CouldNotFindCellFormTypeFromParameterizedType;
import lorikeet.error.CouldNotLoadCellParameterClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

public class Microscope {

    private static final String METHOD_NAME_INVOKE      = "invoke";
    private static final String METHOD_NAME_CONNECT     = "inject";

    public CellStructure inspect(Class<? extends Cell> cellClass) {
        final Seq<ParameterizedType> cellTypes = filterCellTypes(cellClass);

        final Seq<CellForm> forms = cellTypes.stream()
            .map(type -> buildCellForm(cellClass, type))
            .filter(form -> form.isPresent())
            .map(Err::orPanic)
            .collect(Seq.collector());

        return new CellStructure(forms);
    }

    private static Seq<ParameterizedType> filterCellTypes(Class<? extends Cell> cellClass) {
        Seq<ParameterizedType> cellTypes = Seq.empty();
        for (int i = 0; i < cellClass.getGenericInterfaces().length; i++) {
            final Type type = cellClass.getGenericInterfaces()[i];
            if (!isGenericTypeCellForm(type)) {
                continue;
            }
            cellTypes = cellTypes.push((ParameterizedType)type);
        }
        return cellTypes;
    }

    private static boolean isGenericTypeCellForm(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        final ParameterizedType parameterizedType = (ParameterizedType)type;
        return CellFormType.asJavaClassNames().contains(parameterizedType.getRawType().getTypeName());
    }

    private static Err<CellForm> buildCellForm(Class<? extends Cell> cellClass, ParameterizedType type) {
        final Opt<CellFormType> formTypeOpt = CellFormType.fromJavaClassName(type.getRawType().getTypeName());
        if (!formTypeOpt.isPresent()) {
            return Err.failure(new CouldNotFindCellFormTypeFromParameterizedType());
        }
        final CellFormType formType = formTypeOpt.orPanic();

        final Opt<Method> invokeMethodOpt = findInvokeMethod(cellClass, formType);
        if (!invokeMethodOpt.isPresent()) {
            return Err.failure(new CouldNotFindInvokeMethodOnCell());
        }
        final Method invokeMethod = invokeMethodOpt.orPanic();

        final Opt<Method> connectMethodOpt = findConnectMethod(cellClass);
        if (!connectMethodOpt.isPresent()) {
            return Err.failure(new CouldNotFindConnectMethodOnCell());
        }
        final Method connectMethod = connectMethodOpt.orPanic();


        final Annotation[][] parameterAnnotations = invokeMethod.getParameterAnnotations();

        Seq<ParameterMeta> parameters = Seq.empty();
        for (int i = 1; i < type.getActualTypeArguments().length; i++) {
            final int position = i - 1;
            final Type parameterType = type.getActualTypeArguments()[i];
            try {
                final Class<?> parameterClass = Microscope.class.getClassLoader().loadClass(parameterType.getTypeName());
                final ParameterMeta parameterMeta = findTagAnnotation(parameterAnnotations[position])
                    .map(dbg -> new ParameterMeta(position, dbg.value(), dbg.useHash(), dbg.ignore(), parameterClass))
                    .orElse(new ParameterMeta(position, parameterClass));

                parameters = parameters.push(parameterMeta);
            } catch (ClassNotFoundException e) {
                return Err.failure(new CouldNotLoadCellParameterClass(e));
            }
        }

        return Err.of(new CellForm(formType, invokeMethod, connectMethod, parameters));
    }

    private static Opt<Method> findInvokeMethod(Class<? extends Cell> cellClass, CellFormType formType) {
        final Seq<Method> methods = Seq.of(cellClass.getDeclaredMethods());
        return methods.stream()
            .filter(method -> method.getName().equals(METHOD_NAME_INVOKE))
            .filter(method -> method.getParameterCount() == formType.getNumberOfInputArguments())
            .collect(Seq.collector())
            .first();
    }

    private static Opt<Method> findConnectMethod(Class<? extends Cell> cellClass) {
        final Seq<Method> methods = Seq.of(cellClass.getDeclaredMethods());
        return methods.stream()
            .filter(method -> method.getName().equals(METHOD_NAME_CONNECT))
            .filter(method -> method.getParameterCount() == 1)
            .collect(Seq.collector())
            .first();
    }

    private static Opt<Dbg> findTagAnnotation(Annotation[] annotations) {
        return Stream.of(annotations)
            .filter(annotation -> annotation.annotationType().equals(Dbg.class))
            .map(annotation -> (Dbg)annotation)
            .collect(Seq.collector())
            .first();
    }

}
