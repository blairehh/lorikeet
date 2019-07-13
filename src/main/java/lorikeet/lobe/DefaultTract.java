package lorikeet.lobe;

import lorikeet.Seq;
import lorikeet.db.QueryDriver;
import lorikeet.db.QueryPlan;
import java.util.UUID;


public class DefaultTract implements Tract {

    private final String cid;
    private final QueryDriver queryDriver;

    public DefaultTract() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
        this.queryDriver = null;
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        action.connect(this);
        return action.invoke(parameter);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        action.connect(this);
        return action.invoke(parameter1, parameter2);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Action3<ReturnType, ParameterType1, ParameterType2, ParameterType3> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        action.connect(this);
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
        action.connect(this);
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
        action.connect(this);
        return action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
    }

    public final <ProductType, VariableType> Seq<ProductType> query(
        Query1<ProductType, VariableType> query,
        VariableType variable
    ) {
        return this.queryDriver.query(query.getQuery(variable));
    }

}
