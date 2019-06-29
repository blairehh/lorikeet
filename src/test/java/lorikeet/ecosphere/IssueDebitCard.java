package lorikeet.ecosphere;

import lorikeet.ecosphere.articletesting.meta.Dbg;

public class IssueDebitCard implements Action1<Boolean, String> {

    private Tract tract;

    private boolean issueDebitCard(String paymentCompany) {
        return true;
    }

    @Override
    public Boolean invoke(@Dbg("paymentCompany") String paymentCompany) {
        return true;
    }

    @Override
    public void connect(Tract tract) {
        this.tract = tract;
    }
}
