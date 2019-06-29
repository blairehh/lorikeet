package lorikeet.lobe.articletesting;

import lorikeet.lobe.Action1;
import lorikeet.lobe.IssueDebitCard;
import lorikeet.lobe.CreateSavingsDeposit;
import lorikeet.lobe.articletesting.meta.ParameterMeta;
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

   @Test
   public void testCreateSavingsDesosit() {
       CellStructure structure = microscope.inspect(CreateSavingsDeposit.class);
       assertThat(structure.getForms()).hasSize(1);

       CellForm form = structure.getForms().first().orPanic();
       assertThat(form.getForm()).isEqualTo(Action1.class);
       assertThat(form.getParameters()).hasSize(1);
       assertThat(form.getParameters()).contains(new ParameterMeta(0, null, true, false, Double.class));
   }

   @Test
   public void testTypeNameToClassName() {
        assertThat(Microscope.typeNameToClassName("java.lang.String")).isEqualTo("java.lang.String");
        assertThat(Microscope.typeNameToClassName("lorikeet.Seq<java.lang.String>")).isEqualTo("lorikeet.Seq");
   }

}
