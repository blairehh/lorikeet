package lorikeet.ecosphere.articletesting;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;

import java.util.stream.Stream;

public enum CellFormType {
    ACTION_1            (Action1.class, CellKind.ACTION,"action1", 1),
    ACTION_2            (Action2.class, CellKind.ACTION,"action2", 2),
    ACTION_3            (Action3.class, CellKind.ACTION,"action3", 3),
    ACTION_4            (Action4.class, CellKind.ACTION,"action4", 4),
    ACTION_5            (Action5.class, CellKind.ACTION,"action5", 5);

    private final Class<? extends Cell> javaClass;
    private final CellKind kind;
    private final String name;
    private final int numberOfInputArguments;

    private CellFormType(Class<? extends Cell> javaClass, CellKind kind, String name, int numberOfInputArguments) {
        this.name = name;
        this.kind = kind;
        this.javaClass = javaClass;
        this.numberOfInputArguments = numberOfInputArguments;
    }

    public Class<? extends Cell> getJavaClass() {
        return this.javaClass;
    }

    public CellKind getKind() {
        return this.kind;
    }

    public String getName() {
        return this.name;
    }

    public int getNumberOfInputArguments() {
        return this.numberOfInputArguments;
    }

    public static Opt<CellFormType> fromName(String name) {
        return Stream.of(CellFormType.values())
            .filter(type -> type.getName().equalsIgnoreCase(name))
            .collect(Seq.collector())
            .first();
    }

    public static Opt<CellFormType> fromJavaClass(Class<?> klass) {
        return Stream.of(CellFormType.values())
            .filter(type -> type.getJavaClass().equals(klass))
            .collect(Seq.collector())
            .first();
    }

    public static Opt<CellFormType> fromJavaClassName(String className) {
        return Stream.of(CellFormType.values())
        .filter(type -> type.getJavaClass().getName().equals(className))
        .collect(Seq.collector())
        .first();
    }

    public static Seq<String> asJavaClassNames() {
        return Stream.of(CellFormType.values())
            .map(type -> type.getJavaClass().getName())
            .collect(Seq.collector());
    }

    public static Seq<CellFormType> ofKind(CellKind kind) {
        return stream()
            .filter(type -> type.getKind() == kind)
            .collect(Seq.collector());
    }

    public static Stream<CellFormType> stream() {
        return Stream.of(CellFormType.values());
    }
}
