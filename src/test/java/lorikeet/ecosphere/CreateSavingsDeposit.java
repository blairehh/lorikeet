package lorikeet.ecosphere;

import lorikeet.ecosphere.articletesting.meta.Dbg;

public class CreateSavingsDeposit implements Action1<Boolean, Double> {

    private Axon axon;

    private boolean createSavingsAccount(double balance) {
        throw new RuntimeException();
    }

    @Override
    public Boolean invoke(@Dbg(useHash = true)Double parameter) {
        return this.createSavingsAccount(parameter);
    }

    @Override
    public void inject(Axon axon) {
        this.axon = axon;
    }
}
