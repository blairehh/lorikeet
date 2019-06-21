package lorikeet.ecosphere;

import lorikeet.ecosphere.articletesting.meta.Dbg;

public class OpenAccount implements Action1<Boolean, String> {

    private Axon axon;

    private boolean openAccount(String email) {
        axon.yield(new IssueDebitCard(), "mastercard");
        try {
            axon.yield(new CreateSavingsDeposit(), 0.0);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean invoke(@Dbg("email") String parameter) {
        return this.openAccount(parameter);
    }

    @Override
    public void inject(Axon axon) {
        this.axon = axon;
    }

}
