package lorikeet.lobe;

public class ChardRegistrationFee extends Action implements Action1<Double, BankAccountType> {

    @Override
    public Double invoke(BankAccountType type) {
        double amount = 0;
        switch (type) {
            case SAVINGS: amount = 1; break;
            case CHEQUE: amount = 2; break;
            case CREDIT: amount = 3; break;
        }
        yield(new ChargePayment(), "USD", amount);
        return amount;
    }

}
