package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Dbg;

public class CreateSavingsDeposit implements Edict1<Boolean, Double> {

    private Plug plug;

    private boolean createSavingsAccount(double balance) {
        throw new RuntimeException();
    }

    @Override
    public Boolean invoke(@Dbg(useHash = true)Double parameter) {
        return this.createSavingsAccount(parameter);
    }

    @Override
    public void inject(Plug plug) {
        this.plug = plug;
    }
}
