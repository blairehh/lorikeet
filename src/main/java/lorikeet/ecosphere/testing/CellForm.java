package lorikeet.ecosphere.testing;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.meta.ParameterMeta;

import java.lang.reflect.Method;
import java.util.Objects;

public class CellForm {

    private final Class<? extends Cell> form;
    private final Method invokeMethod;
    private final Method connectMethod;
    private final Seq<ParameterMeta> parameters;

    public CellForm(Class<? extends Cell> form, Method invokeMethod, Method connectMethod, Seq<ParameterMeta> parameters) {
        this.form = form;
        this.invokeMethod = invokeMethod;
        this.connectMethod = connectMethod;
        this.parameters = parameters;
    }

    public Class<? extends Cell> getForm() {
        return this.form;
    }

    public Method getInvokeMethod() {
        return this.invokeMethod;
    }

    public Method getConnectMethod() {
        return this.connectMethod;
    }

    public Seq<ParameterMeta> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        CellForm cellForm = (CellForm) o;

        return Objects.equals(this.getForm(), cellForm.getForm())
            && Objects.equals(this.getInvokeMethod(), cellForm.getInvokeMethod())
            && Objects.equals(this.getConnectMethod(), cellForm.getConnectMethod())
            && Objects.equals(this.getParameters(), cellForm.getParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getForm(), this.getInvokeMethod(), this.getConnectMethod(), this.getParameters());
    }
}
