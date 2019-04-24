package lorikeet.container;

public class ChargePayment implements Edict2<Boolean, String, Double> {
    @Override
    public Boolean invoke(String parameter1, Double parameter2) {
        return true;
    }

    @Override
    public void inject(ActionContainer action) {

    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("currency", "price");
    }
}
