package lorikeet.lobe.testing;


import lorikeet.Seq;
import lorikeet.lobe.Action1;
import lorikeet.lobe.Action2;
import lorikeet.lobe.Action3;
import lorikeet.lobe.Action4;
import lorikeet.lobe.Action5;
import lorikeet.lobe.ActionPotential;
import lorikeet.lobe.Tract;

public class TestCase<T> {

    private final ActionPotential<T> action;
    protected final TestTract tract;
    protected Interaction interaction;

    public TestCase(ActionPotential<T> action) {
        this.action = action;
        this.tract = new TestTract();
        this.interaction = null;
    }

    public TestCase(TestCase<T> testCase) {
        this.action = testCase.action;
        this.tract = testCase.tract;
        this.interaction = testCase.interaction;
    }

    public static <R, P1> TestCase<R> test(Action1<R, P1> action, P1 parameter) {
        final ActionPotential<R> potential = new ActionPotential<>() {
            @Override
            public R invoke() {
                return action.invoke(parameter);
            }

            @Override
            public void connect(Tract tract) {
                action.connect(tract);
            }
        };
        return new TestCase<>(potential);
    }

    public static <R, P1, P2> TestCase<R> test(Action2<R, P1, P2> action, P1 parameter1, P2 parameter2) {
        final ActionPotential<R> potential = new ActionPotential<>() {
            @Override
            public R invoke() {
                return action.invoke(parameter1, parameter2);
            }

            @Override
            public void connect(Tract tract) {
                action.connect(tract);
            }
        };
        return new TestCase<>(potential);
    }

    public static <R, P1, P2, P3> TestCase<R> test(
        Action3<R, P1, P2, P3> action,
        P1 parameter1,
        P2 parameter2,
        P3 parameter3
    ) {
        final ActionPotential<R> potential = new ActionPotential<>() {
            @Override
            public R invoke() {
                return action.invoke(parameter1, parameter2, parameter3);
            }

            @Override
            public void connect(Tract tract) {
                action.connect(tract);
            }
        };
        return new TestCase<>(potential);
    }

    public static <R, P1, P2, P3, P4> TestCase<R> test(
        Action4<R, P1, P2, P3, P4> action,
        P1 parameter1,
        P2 parameter2,
        P3 parameter3,
        P4 parameter4
    ) {
        final ActionPotential<R> potential = new ActionPotential<>() {
            @Override
            public R invoke() {
                return action.invoke(parameter1, parameter2, parameter3, parameter4);
            }

            @Override
            public void connect(Tract tract) {
                action.connect(tract);
            }
        };
        return new TestCase<>(potential);
    }

    public static <R, P1, P2, P3, P4, P5> TestCase<R> test(
        Action5<R, P1, P2, P3, P4, P5> action,
        P1 parameter1,
        P2 parameter2,
        P3 parameter3,
        P4 parameter4,
        P5 parameter5
    ) {
        final ActionPotential<R> potential = new ActionPotential<>() {
            @Override
            public R invoke() {
                return action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
            }

            @Override
            public void connect(Tract tract) {
                action.connect(tract);
            }
        };
        return new TestCase<>(potential);
    }


    public <R, P1> InteractionTestCase<T, R> when(Action1<R, P1> action, P1 parameter) {
        this.interaction = Interaction.of(action, parameter);
        return new InteractionTestCase<>(this);
    }

    public <R, P1, P2> InteractionTestCase<T, R> when(Action2<R, P1, P2> action, P1 parameter1, P2 parameter2) {
        this.interaction = Interaction.of(action, parameter1, parameter2);
        return new InteractionTestCase<>(this);
    }

    public <R, P1, P2, P3> InteractionTestCase<T, R> when(
        Action3<R, P1, P2, P3> action,
        P1 parameter1,
        P2 parameter2,
        P3 parameter3
    ) {
        this.interaction = Interaction.of(action, parameter1, parameter2, parameter3);
        return new InteractionTestCase<>(this);
    }

    public <R, P1, P2, P3, P4> InteractionTestCase<T, R> when(
        Action4<R, P1, P2, P3, P4> action,
        P1 parameter1,
        P2 parameter2,
        P3 parameter3,
        P4 parameter4
    ) {
        this.interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4);
        return new InteractionTestCase<>(this);
    }

    public <R, P1, P2, P3, P4, P5> InteractionTestCase<T, R> when(
        Action5<R, P1, P2, P3, P4, P5> action,
        P1 parameter1,
        P2 parameter2,
        P3 parameter3,
        P4 parameter4,
        P5 parameter5
    ) {
        this.interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4, parameter5);
        return new InteractionTestCase<>(this);
    }


    public T evaluate() {
        this.action.connect(this.tract);
        return this.action.invoke();
    }

    public Seq<Interaction> getInteractions() {
        return this.tract.getInteractions();
    }


}
