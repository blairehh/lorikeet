package lorikeet.ecosphere.meta;


import lorikeet.Seq;
import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.SendWelcomeMessage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MetaFromTagAnnotationsTest {

    @Test
    public void testEveryParameterWithTag() {
        Seq<ParameterMeta> parameters = MetaFromTagAnnotations.meta(new CreateUser(), 3).getParameters();
        assertThat(parameters).contains(
            new ParameterMeta(0, "email", false, false),
            new ParameterMeta(1, "password", false, false),
            new ParameterMeta(2, "codes", false, false)
        );
    }

    @Test
    public void testEveryParameterWithOneMissing() {
        Seq<ParameterMeta> parameters = MetaFromTagAnnotations.meta(new SendWelcomeMessage(), 2).getParameters();
        assertThat(parameters).contains(
            new ParameterMeta(0),
            new ParameterMeta(1, "message", false, false)
        );
    }
}