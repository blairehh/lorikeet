package lorikeet.lobe;

import lorikeet.lobe.articletesting.meta.Dbg;

public class CreateSavingsDeposit implements Action1<Boolean, Double> {

    private Tract tract;

    private boolean createSavingsAccount(double balance) {
        throw new IllegalStateException();
    }

    @Override
    public Boolean invoke(@Dbg(useHash = true)Double parameter) {
        return this.createSavingsAccount(parameter);
    }

    @Override
    public void connect(Tract tract) {
        this.tract = tract;
    }
}
