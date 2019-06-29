package lorikeet.lobe.testing;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.lobe.Action1;
import lorikeet.lobe.Action2;
import lorikeet.lobe.Action3;
import lorikeet.lobe.Action4;
import lorikeet.lobe.Action5;
import lorikeet.lobe.Tract;

import java.util.UUID;

public class TestTract implements Tract {
    private final String cid;
    private Seq<Interaction> stubs;
    private Seq<Interaction> interactions;

    public TestTract() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
        this.interactions = Seq.empty();
        this.stubs = Seq.empty();
    }

    public Seq<Interaction> getInteractions() {
        return this.interactions;
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    public void addStub(Interaction interaction) {
        this.stubs = this.stubs.push(interaction);
    }

    private <A> Opt<A> stubbedValue(Interaction interaction) {
        final Interaction stubbed = this.stubs.stream()
            .filter(stub -> stub.invokeEquals(interaction))
            .collect(Seq.collector())
            .first()
            .orNull();

        if (stubbed == null) {
            return Opt.empty();
        }

        if (stubbed.getExceptionToThrow().isPresent()) {
            throw stubbed.getExceptionToThrow().orPanic();
        }

        return (Opt<A>)stubbed.getReturnValue();
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        action.connect(this);
        final Interaction interaction = Interaction.of(action, parameter);
        try {
            final ReturnType returnValue = (ReturnType)this.stubbedValue(interaction).orElseGet(() -> action.invoke(parameter));
            this.interactions = this.interactions.push(interaction.withReturnValue(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            this.interactions = this.interactions.push(interaction.withExceptionThrown(e.getClass()));
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        action.connect(this);
        final Interaction interaction = Interaction.of(action, parameter1, parameter2);
        try {
            final ReturnType returnValue =(ReturnType)this.stubbedValue(interaction)
                .orElseGet(() -> action.invoke(parameter1, parameter2));
            this.interactions = this.interactions.push(interaction.withReturnValue(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            this.interactions = this.interactions.push(interaction.withExceptionThrown(e.getClass()));
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Action3<ReturnType, ParameterType1, ParameterType2, ParameterType3> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        action.connect(this);
        final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3);
        try {
            final ReturnType returnValue = (ReturnType)this.stubbedValue(interaction)
                .orElseGet(() -> action.invoke(parameter1, parameter2, parameter3));
            this.interactions = this.interactions.push(interaction.withReturnValue(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            this.interactions = this.interactions.push(interaction.withExceptionThrown(e.getClass()));
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Action4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    ) {
        action.connect(this);
        final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4);
        try {
            final ReturnType returnValue = (ReturnType)this.stubbedValue(interaction)
                .orElseGet(() -> action.invoke(parameter1, parameter2, parameter3, parameter4));
            this.interactions = this.interactions.push(interaction.withReturnValue(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            this.interactions = this.interactions.push(interaction.withExceptionThrown(e.getClass()));
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Action5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    ) {
        action.connect(this);
        final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4, parameter5);
        try {
            final ReturnType returnValue = (ReturnType)this.stubbedValue(interaction)
                .orElseGet(() -> action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5));
            this.interactions = this.interactions.push(interaction .withReturnValue(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            this.interactions = this.interactions.push(interaction.withExceptionThrown(e.getClass()));
            throw e;
        }

    }

}
