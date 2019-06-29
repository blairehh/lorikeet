package lorikeet.lobe;

public class Action {
    private Tract tract;

    public void connect(Tract tract) {
        this.tract = tract;
    }

    public String cid() {
        return this.tract.getCid();
    }

    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        return this.tract.yield(action, parameter);
    }

    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        return this.tract.yield(action, parameter1, parameter2);
    }

    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Action3<ReturnType, ParameterType1, ParameterType2, ParameterType3> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        return this.tract.yield(action, parameter1, parameter2, parameter3);
    }

    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Action4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    ) {
        return this.tract.yield(action, parameter1, parameter2, parameter3, parameter4);
    }

    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Action5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    ) {
        return this.tract.yield(action, parameter1, parameter2, parameter3, parameter4, parameter5);
    }

}
