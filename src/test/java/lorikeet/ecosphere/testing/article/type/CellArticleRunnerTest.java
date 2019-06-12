package lorikeet.ecosphere.testing.article.type;

import lorikeet.Dict;
import lorikeet.ecosphere.testing.article.RunResult;
import lorikeet.ecosphere.testing.data.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CellArticleRunnerTest {

    private final CellArticleRunner runner = new CellArticleRunner();

    @Test
    public void testReturnsValue() {
        CellValue cell = new CellValue(
            "lorikeet.ecosphere.IssueDebitCard",
            Dict.of("paymentCompany", new StringValue("mastercard")),
            null,
            new BoolValue(true)
        );
        CellArticle article = new CellArticle(cell);

        RunResult result = runner.run(article).orPanic();

        assertThat(result.isReturnValueMatched()).isTrue();
        assertThat(result.isExceptionThrownMatched()).isTrue();
    }

    @Test
    public void testThrowsException() {
        CellValue cell = new CellValue(
                "lorikeet.ecosphere.CreateSavingsDeposit",
                Dict.of("0", new NumberValue(34.67)),
                "java.lang.RuntimeException",
                null
        );

        CellArticle article = new CellArticle(cell);

        RunResult result = runner.run(article).orPanic();

        assertThat(result.isReturnValueMatched()).isTrue();
        assertThat(result.isExceptionThrownMatched()).isTrue();
    }
}
