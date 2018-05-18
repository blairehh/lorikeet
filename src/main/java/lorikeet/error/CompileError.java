package lorikeet.error;

import lorikeet.util.Console;

public interface CompileError {
    default boolean isOfType(Class<? extends CompileError> klass) {
        return this.getClass().equals(klass);
    }

    public void outputTo(Console console);
}
