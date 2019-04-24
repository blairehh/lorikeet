package lorikeet.container;

public class IssueDebitCard implements Edict1<Boolean, String> {

    private ActionContainer action;

    private boolean issueDebitCard(String paymentCompany) {
        return true;
    }

    @Override
    public Boolean invoke(String paymentCompany) {
        return null;
    }

    @Override
    public void inject(ActionContainer action) {
        this.action = action;
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("paymentCompany");
    }
}
