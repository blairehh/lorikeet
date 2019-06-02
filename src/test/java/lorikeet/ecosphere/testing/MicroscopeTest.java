package lorikeet.ecosphere.testing;

import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.IssueDebitCard;
import lorikeet.ecosphere.meta.ParameterMeta;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MicroscopeTest {

    private final Microscope microscope = new Microscope();

   @Test
   public void testIssueDebitCard() {
       CellStructure structure = microscope.inspect(IssueDebitCard.class);
       assertThat(structure.getForms()).hasSize(1);

       CellForm form = structure.getForms().first().orPanic();
       assertThat(form.getForm()).isEqualTo(Action1.class);
       assertThat(form.getParameters()).hasSize(1);
       assertThat(form.getParameters()).contains(new ParameterMeta(0, "paymentCompany", false, false, String.class));
   }

}