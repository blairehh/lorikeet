package lorikeet.ecosphere;

public class CreateSavingsDeposit implements Edict1<Boolean, Double> {

    private Plug plug;

    private boolean createSavingsAccount(double balance) {
        throw new RuntimeException();
    }

    @Override
    public Boolean invoke(Double parameter) {
        return this.createSavingsAccount(parameter);
    }

    @Override
    public void inject(Plug plug) {
        this.plug = plug;
    }
}
