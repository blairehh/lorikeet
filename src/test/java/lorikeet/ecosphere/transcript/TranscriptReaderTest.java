package lorikeet.ecosphere.transcript;

import org.junit.Test;

import static lorikeet.ecosphere.transcript.TranscriptReader.extractNextIdentifier;
import static lorikeet.ecosphere.transcript.TranscriptReader.jumpWhitesapce;
import static org.assertj.core.api.Assertions.assertThat;


public class TranscriptReaderTest {

    @Test
    public void testExtractNextIdentifier() {
        assertThat(extractNextIdentifier("lorikeet.Seq", 0)).isEqualTo(new Extract<>("lorikeet.Seq", 12));
        assertThat(extractNextIdentifier("lorikeet.Seq <", 0)).isEqualTo(new Extract<>("lorikeet.Seq", 12));
    }

    @Test
    public void testJumpWhitesapce() {
        assertThat(jumpWhitesapce("  a", 0)).isEqualTo(2);
        assertThat(jumpWhitesapce("  a", 2)).isEqualTo(2);
    }
}