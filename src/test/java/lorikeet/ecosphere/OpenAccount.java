package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Tag;

public class OpenAccount implements Edict1<Boolean, String> {

    private Plug plug;

    private boolean openAccount(String email) {
        plug.yield(new CreateSavingsDeposit(), 0.0);
        plug.yield(new IssueDebitCard(), "mastercard");
        return true;
    }

    @Override
    public Boolean invoke(@Tag("email") String parameter) {
        return this.openAccount(parameter);
    }

    @Override
    public void inject(Plug plug) {
        this.plug = plug;
    }

}
