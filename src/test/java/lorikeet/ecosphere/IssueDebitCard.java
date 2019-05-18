package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Dbg;

public class IssueDebitCard implements Action1<Boolean, String> {

    private Axon axon;

    private boolean issueDebitCard(String paymentCompany) {
        return true;
    }

    @Override
    public Boolean invoke(@Dbg("paymentCompany") String paymentCompany) {
        return true;
    }

    @Override
    public void inject(Axon action) {
        this.axon = axon;
    }

}
