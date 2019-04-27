package lorikeet;

import java.util.function.Function;

@FunctionalInterface
public interface Fun<Input, Output> extends Function<Input, Output> {

}
