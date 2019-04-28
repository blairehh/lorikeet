package lorikeet.ecosphere.meta;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.Crate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class MetaFromDbgAnnotations {


    public static Meta meta(Crate crate, int invokeParameterCount) {
        Seq<ParameterMeta> parameters = Seq.empty();
        final Annotation[][] parameterAnnotations = determineInvokeMethod(crate, invokeParameterCount)
            .getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            final int index = i;
            final Annotation[] annotations = parameterAnnotations[i];
            final ParameterMeta parameter = findTagAnnotation(annotations)
                .map(tag -> parameterMeta(index , tag))
                .orElse(parameterMeta(index));
            parameters = parameters.push(parameter);
        }
        return new Meta(parameters);
    }

    private static Method determineInvokeMethod(Crate crate, int invokeParameterCount) {
        final Seq<Method> methods = Seq.of(crate.getClass().getDeclaredMethods());
        return methods.stream()
            .filter(method -> method.getName().equals("invoke"))
            .filter(method -> method.getParameterCount() == invokeParameterCount)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("could not find invoke method on class " + crate.getClass().getName()));
    }

    private static Opt<Dbg> findTagAnnotation(Annotation[] annotations) {
        return Stream.of(annotations)
            .filter(annotation -> annotation.annotationType().equals(Dbg.class))
            .map(annotation -> (Dbg)annotation)
            .collect(Seq.collector())
            .first();
    }

    private static ParameterMeta parameterMeta(int parameterIndex) {
        return new ParameterMeta(parameterIndex);
    }

    private static ParameterMeta parameterMeta(int parameterIndex, Dbg dbg) {
        return new ParameterMeta(parameterIndex, dbg.value(), dbg.useHash(), dbg.ignore());
    }
}
