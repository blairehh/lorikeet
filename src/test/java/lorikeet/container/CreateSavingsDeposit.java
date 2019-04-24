package lorikeet.container;

public class CreateSavingsDeposit implements Edict1<Boolean, Double> {

    private ActionContainer action;

    private boolean createSavingsAccount(double balance) {
        return true;
    }

    @Override
    public Boolean invoke(Double parameter) {
        return null;
    }

    @Override
    public void inject(ActionContainer action) {
        this.action = action;
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("balance");
    }
}
