package lorikeet.ecosphere.testing;

import lorikeet.Opt;
import lorikeet.Seq;

import java.util.Objects;

public class CellStructure {
    private final Seq<CellForm> forms;

    public CellStructure(Seq<CellForm> forms) {
        this.forms = forms;
    }

    public Seq<CellForm> getForms() {
        return this.forms;
    }

    public Opt<CellForm> formFor(int parameterCount) {
        return this.forms
            .stream()
            .filter(form -> form.getParameters().size() == parameterCount)
            .collect(Seq.collector())
            .first();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        CellStructure that = (CellStructure) o;

        return Objects.equals(this.getForms(), that.getForms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getForms());
    }
}
