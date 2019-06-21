package lorikeet.ecosphere.testing;

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
    private Seq<Interaction> interactions;

    public TestAxon() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
        this.interactions = Seq.empty();
    }

    public Seq<Interaction> getInteractions() {
        return this.interactions;
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        action.inject(this);
        this.interactions = this.interactions.push(Interaction.of(action, parameter));
        return action.invoke(parameter);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        action.inject(this);
        this.interactions = this.interactions.push(Interaction.of(action, parameter1, parameter2));
        return action.invoke(parameter1, parameter2);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Action3<ReturnType, ParameterType1, ParameterType2, ParameterType3> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        action.inject(this);
        this.interactions = this.interactions.push(Interaction.of(action, parameter1, parameter2, parameter3));
        return action.invoke(parameter1, parameter2, parameter3);
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
        this.interactions = this.interactions.push(Interaction.of(action, parameter1, parameter2, parameter3, parameter4));
        return action.invoke(parameter1, parameter2, parameter3, parameter4);
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
        this.interactions = this.interactions.push(Interaction.of(action, parameter1, parameter2, parameter3, parameter4, parameter5));
        return action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
    }

}
