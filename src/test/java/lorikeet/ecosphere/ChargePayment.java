package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Dbg;

public class ChargePayment implements Edict2<Boolean, String, Double> {
    @Override
    public Boolean invoke(@Dbg("currency") String parameter1, @Dbg("price") Double parameter2) {
        return true;
    }

    @Override
    public void inject(Plug plug) {

    }

}
