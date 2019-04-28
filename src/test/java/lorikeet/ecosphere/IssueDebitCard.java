package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Dbg;

public class IssueDebitCard implements Edict1<Boolean, String> {

    private Plug plug;

    private boolean issueDebitCard(String paymentCompany) {
        return true;
    }

    @Override
    public Boolean invoke(@Dbg("paymentCompany") String paymentCompany) {
        return true;
    }

    @Override
    public void inject(Plug action) {
        this.plug = plug;
    }

}
