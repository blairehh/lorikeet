package lorikeet.ecosphere.testing;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.meta.Dbg;
import lorikeet.ecosphere.meta.Meta;
import lorikeet.ecosphere.meta.ParameterMeta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

public class Microscope {

    private static final Seq<Class<? extends Cell>> ACTION_CLASSES = Seq.of(
        Action1.class,
        Action2.class,
        Action3.class,
        Action4.class,
        Action5.class
    );

    private static final Seq<String> ACTION_CLASSES_NAMES = ACTION_CLASSES.map(Class::getName);

    public CellStructure inspect(Class<? extends Cell> cellClass) {
        final Seq<ParameterizedType> cellTypes = filterCellTypes(cellClass);

        final Seq<CellForm> forms = cellTypes.map(type -> buildCellForm(cellClass, type));
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
        return ACTION_CLASSES_NAMES.contains(parameterizedType.getRawType().getTypeName());
    }

    private static CellForm buildCellForm(Class<? extends Cell> cellClass, ParameterizedType type) {
        final Class<? extends Cell> formClass = ACTION_CLASSES.filter(actionClass -> type.getRawType().getTypeName().equals(actionClass.getName()))
            .orPanic(); //@TODO supply exception here
        final Method invokeMethod = findInvokeMethod(cellClass, type.getActualTypeArguments().length - 1);
        final Annotation[][] parameterAnnotations = invokeMethod.getParameterAnnotations();

        Seq<ParameterMeta> parameters = Seq.empty();
        for (int i = 1; i < type.getActualTypeArguments().length; i++) {
            final int position = i - 1;
            final Type parameterType = type.getActualTypeArguments()[i];
            try {
                final Class<?> parameterClass = Microscope.class.getClassLoader().loadClass(parameterType.getTypeName());
                final ParameterMeta parameterMeta = findTagAnnotation(parameterAnnotations[position])
                    .ifnot(() -> System.out.println("did not find debug tag"))
                    .map(dbg -> new ParameterMeta(position, dbg.value(), dbg.useHash(), dbg.ignore(), parameterClass))
                    .orElse(new ParameterMeta(position, parameterClass));

                parameters = parameters.push(parameterMeta);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // @TODO add the connect method
        return new CellForm(formClass, invokeMethod, null, parameters);
    }


    private static Method findInvokeMethod(Class<? extends Cell> cellClass, int invokeParameterCount) {
        final Seq<Method> methods = Seq.of(cellClass.getDeclaredMethods());
        return methods.stream()
            .filter(method -> method.getName().equals("invoke"))
            .filter(method -> method.getParameterCount() == invokeParameterCount)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("could not find invoke method on class " + cellClass.getName()));
    }

    private static Method findConnectMethod(Class<? extends Cell> cellClass) {
        final Seq<Method> methods = Seq.of(cellClass.getDeclaredMethods());
        return methods.stream()
            .filter(method -> method.getName().equals("inject"))
            .filter(method -> method.getParameterCount() == 1)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("could not find connect method on class " + cellClass.getName()));
    }

    private static Opt<Dbg> findTagAnnotation(Annotation[] annotations) {
        return Stream.of(annotations)
            .filter(annotation -> annotation.annotationType().equals(Dbg.class))
            .map(annotation -> (Dbg)annotation)
            .collect(Seq.collector())
            .first();
    }

}
