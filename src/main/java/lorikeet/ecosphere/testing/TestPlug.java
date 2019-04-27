package lorikeet.ecosphere.testing;


import lorikeet.ecosphere.Plug;
import lorikeet.ecosphere.Crate;
import lorikeet.ecosphere.Edict1;
import lorikeet.ecosphere.Edict2;
import lorikeet.ecosphere.Edict3;
import lorikeet.ecosphere.Edict4;
import lorikeet.ecosphere.Edict5;
import lorikeet.ecosphere.Meta;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestPlug implements Plug {

    private final String cid;
    private CrateGraphNode rootGraphNode;

    public TestPlug() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
    }

    private TestPlug(CrateGraphNode root) {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
        this.rootGraphNode = root;
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Edict1<ReturnType, ParameterType> edict, ParameterType parameter) {
        edict.inject(this.prepareGraphNode(edict, parameter));
        return edict.invoke(parameter);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Edict2<ReturnType, ParameterType1, ParameterType2> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        edict.inject(this.prepareGraphNode(edict, parameter1, parameter2));
        return edict.invoke(parameter1, parameter2);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Edict3<ReturnType, ParameterType1, ParameterType2, ParameterType3> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        edict.inject(this.prepareGraphNode(edict, parameter1, parameter2, parameter3));
        return edict.invoke(parameter1, parameter2, parameter3);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Edict4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    ) {
        edict.inject(this.prepareGraphNode(edict, parameter1, parameter2, parameter3, parameter4));
        return edict.invoke(parameter1, parameter2, parameter3, parameter4);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Edict5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    ) {
        edict.inject(this.prepareGraphNode(edict, parameter1, parameter2, parameter3, parameter4, parameter5));
        return edict.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
    }

    private Plug prepareGraphNode(Crate crate, Object... params) {
        List<CrateParameter> parameters = new ArrayList<>();

        this.populateParameters(crate.getMeta(), Arrays.asList(params), parameters);

        CrateGraphNode createdNode = new CrateGraphNode(
            crate.getClass().getName(),
            parameters,
            Instant.now(),
            new ArrayList<>()
        );

        if (this.rootGraphNode == null) {
            this.rootGraphNode = createdNode;
        } else {
            this.rootGraphNode.getChildren().add(createdNode);
        }

        return new TestPlug(createdNode);
    }

    private void populateParameters(Meta meta, List<Object> params, List<CrateParameter> parameters) {
        for (int i = 0; i < params.size(); i++) {
            final String parameterName = meta.getParameters().fetch(i)
                .filter(name -> name != null && !name.trim().isEmpty())
                .orElse(null);
            if (parameterName == null) {
                continue;
            }


            parameters.add(new CrateParameter(parameterName, params.get(i)));
        }
    }

    public CrateGraph getGraph() {
        return new CrateGraph(this.rootGraphNode);
    }
}
