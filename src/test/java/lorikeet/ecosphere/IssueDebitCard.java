package lorikeet.ecosphere;

public class IssueDebitCard implements Edict1<Boolean, String> {

    private Plug plug;

    private boolean issueDebitCard(String paymentCompany) {
        return true;
    }

    @Override
    public Boolean invoke(String paymentCompany) {
        return null;
    }

    @Override
    public void inject(Plug action) {
        this.plug = plug;
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("paymentCompany");
    }
}
