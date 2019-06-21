package lorikeet.ecosphere.testing;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;
import lorikeet.ecosphere.Axon;

import java.util.UUID;

public class TestAxon implements Axon {
    private final String cid;
    private Seq<Interaction> stubs;
    private Seq<Interaction> interactions;

    public TestAxon() {
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
        return (Opt<A>)stubbed.getReturnValue();
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        action.inject(this);
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
        action.inject(this);
        try {
            final ReturnType returnValue = action.invoke(parameter1, parameter2);
            final Interaction interaction = Interaction.of(action, parameter1, parameter2)
                .withReturnValue(returnValue);
            this.interactions = this.interactions.push(interaction);
            return returnValue;
        } catch (RuntimeException e) {
            final Interaction interaction = Interaction.of(action, parameter1, parameter2)
                .withExceptionThrown(e.getClass());
            this.interactions = this.interactions.push(interaction);
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
        action.inject(this);
        try {
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3);
            final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3)
                .withReturnValue(returnValue);
            this.interactions = this.interactions.push(interaction);
            return returnValue;
        } catch (RuntimeException e) {
            final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3)
                .withExceptionThrown(e.getClass());
            this.interactions = this.interactions.push(interaction);
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
        action.inject(this);
        try {
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4);
            final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4)
                .withReturnValue(returnValue);
            this.interactions = this.interactions.push(interaction);
            return returnValue;
        } catch (RuntimeException e) {
            final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4)
                .withExceptionThrown(e.getClass());
            this.interactions = this.interactions.push(interaction);
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
        action.inject(this);
        try {
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
            final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4, parameter5)
                .withReturnValue(returnValue);
            this.interactions = this.interactions.push(interaction);
            return returnValue;
        } catch (RuntimeException e) {
            final Interaction interaction = Interaction.of(action, parameter1, parameter2, parameter3, parameter4, parameter5)
                .withExceptionThrown(e.getClass());
            this.interactions = this.interactions.push(interaction);
            throw e;
        }

    }

}
