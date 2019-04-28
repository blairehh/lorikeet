package lorikeet.ecosphere;

public class CreateSavingsDeposit implements Edict1<Boolean, Double> {

    private Plug plug;

    private boolean createSavingsAccount(double balance) {
        return true;
    }

    @Override
    public Boolean invoke(Double parameter) {
        return null;
    }

    @Override
    public void inject(Plug plug) {
        this.plug = plug;
    }
}
