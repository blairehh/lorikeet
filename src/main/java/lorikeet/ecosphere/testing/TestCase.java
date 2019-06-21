package lorikeet.ecosphere.testing;


import lorikeet.Seq;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.ActionPotential;
import lorikeet.ecosphere.Axon;

public class TestCase<T> {

    private final ActionPotential<T> action;
    private final TestAxon axon;

    public TestCase(ActionPotential<T> action) {
        this.action = action;
        this.axon = new TestAxon();
    }

    public static <R, P1> TestCase<R> test(Action1<R, P1> action, P1 parameter) {
        final ActionPotential<R> potential = new ActionPotential<>() {
            @Override
            public R invoke() {
                return action.invoke(parameter);
            }

            @Override
            public void connect(Axon axon) {
                action.inject(axon);
            }
        };
        return new TestCase<>(potential);
    }

    public T evaluate() {
        this.action.connect(this.axon);
        return this.action.invoke();
    }

    public Seq<Interaction> getInteractions() {
        return this.axon.getInteractions();
    }


}
