package lorikeet.core;

import java.util.Collection;
import java.util.List;
import org.pcollections.TreePVector;

public class Seq<T> {

    private final TreePVector<T> vector;

    public Seq() {
        this.vector = TreePVector.empty();
    }

    public Seq(T... values) {
        this.vector = TreePVector.from(List.of(values));
    }

    public Seq(Collection<T> collection) {
        this.vector = TreePVector.from(collection);
    }
}