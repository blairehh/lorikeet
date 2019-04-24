package lorikeet.container;

public class OpenAccount implements Edict1<Boolean, String> {

    private ActionContainer action;

    private boolean openAccount(String email) {
        action.yield(new CreateSavingsDeposit(), 0.0);
        action.yield(new IssueDebitCard(), "mastercard");
        return true;
    }

    @Override
    public Boolean invoke(String parameter) {
        return this.openAccount(parameter);
    }

    @Override
    public void inject(ActionContainer action) {
        this.action = action;
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("email");
    }
}
