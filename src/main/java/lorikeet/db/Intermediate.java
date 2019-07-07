package lorikeet.db;

import lorikeet.Opt;

public interface Intermediate {
    Opt<String> string(String identifier);
    Opt<Number> number(String identifier);
    Opt<Integer> integer(String identifier);
    Opt<Double> decimal(String identifier);
    Opt<Boolean> bool(String identifier);
}
