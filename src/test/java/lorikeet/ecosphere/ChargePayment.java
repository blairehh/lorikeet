package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Tag;

public class ChargePayment implements Edict2<Boolean, String, Double> {
    @Override
    public Boolean invoke(@Tag("currency") String parameter1, @Tag("price") Double parameter2) {
        return true;
    }

    @Override
    public void inject(Plug plug) {

    }

}
