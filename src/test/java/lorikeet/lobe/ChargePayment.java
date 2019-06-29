package lorikeet.lobe;

import lorikeet.lobe.articletesting.meta.Dbg;

public class ChargePayment implements Action2<Boolean, String, Double> {
    @Override
    public Boolean invoke(@Dbg("currency") String parameter1, @Dbg("price") Double parameter2) {
        return true;
    }

    @Override
    public void connect(Tract tract) {

    }

}
