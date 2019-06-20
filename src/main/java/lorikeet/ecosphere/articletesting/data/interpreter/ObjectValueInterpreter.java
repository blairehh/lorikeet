package lorikeet.ecosphere.articletesting.data.interpreter;

import lorikeet.Dict;
import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.NotSupportedValue;
import lorikeet.ecosphere.articletesting.data.ObjectValue;
import lorikeet.ecosphere.articletesting.data.Value;

import java.lang.reflect.Field;
    import java.lang.reflect.Modifier;

public class ObjectValueInterpreter implements ValueInterpreter {

    private final Interpreter interpreter = new Interpreter();

    @Override
    public Opt<Value> interpret(Object value) {
        if (value == null || !shouldInterpretClass(value.getClass())) {
            return Opt.empty();
        }

        final String className = value.getClass().getName();
        Dict<String, Value> data = Dict.empty();

        for (Field field : value.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            try {
                field.setAccessible(true);
                if (!field.canAccess(value)) {
                    continue;
                }
                data = data.push(field.getName(), this.interpreter.interpret(field.get(value)));
            } catch (IllegalAccessException | SecurityException e) {
                return Opt.of(new NotSupportedValue());
            }
        }

        return Opt.of(new ObjectValue(className, data));
    }

    private static boolean shouldInterpretClass(Class<?> c) {
        return !c.getPackage().getName().startsWith("java.") && !c.getPackage().getName().startsWith("javax.");
    }
}
