package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Tag;

public class IssueDebitCard implements Edict1<Boolean, String> {

    private Plug plug;

    private boolean issueDebitCard(String paymentCompany) {
        return true;
    }

    @Override
    public Boolean invoke(@Tag("paymentCompany") String paymentCompany) {
        return null;
    }

    @Override
    public void inject(Plug action) {
        this.plug = plug;
    }

}
