package lorikeet.lobe.testing;

public class YieldExpect<ActionReturn, YieldReturn> extends ActionExpect<ActionReturn> {

    YieldExpect(ActionExpect<ActionReturn> actionExpect) {
        super(actionExpect);
    }

    public ActionExpect<ActionReturn> andReturn(YieldReturn expectedReturnValue) {
        super.interaction = super.interaction.withReturnValue(expectedReturnValue);
        return new ActionExpect<>(this);
    }

    public ActionExpect<ActionReturn> andThrow(Class<? extends Exception> expectedException) {
        super.interaction = super.interaction.withExceptionThrown(expectedException);
        return new ActionExpect<>(this);
    }

}
