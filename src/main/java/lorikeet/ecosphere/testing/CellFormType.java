package lorikeet.ecosphere.testing;

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
    ACTION_1            (Action1.class, "action1"),
    ACTION_2            (Action2.class, "action2"),
    ACTION_3            (Action3.class, "action3"),
    ACTION_4            (Action4.class, "action4"),
    ACTION_5            (Action5.class, "action5");

    private final Class<? extends Cell> javaClass;
    private final String name;

    private CellFormType(Class<? extends Cell> javaClass, String name) {
        this.name = name;
        this.javaClass = javaClass;
    }

    public Class<? extends Cell> getJavaClass() {
        return this.javaClass;
    }

    public String getName() {
        return this.name;
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
}
