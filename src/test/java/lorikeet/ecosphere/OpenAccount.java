package lorikeet.ecosphere;

import lorikeet.ecosphere.articletesting.meta.Dbg;

public class OpenAccount implements Action1<Boolean, String> {

    private Tract tract;

    private boolean openAccount(String email) {
        tract.yield(new IssueDebitCard(), "mastercard");
        try {
            tract.yield(new CreateSavingsDeposit(), 0.0);
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
    public void connect(Tract tract) {
        this.tract = tract;
    }

}
