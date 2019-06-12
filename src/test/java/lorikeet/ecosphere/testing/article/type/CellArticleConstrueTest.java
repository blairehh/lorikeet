package lorikeet.ecosphere.testing.article.type;


import lorikeet.Dict;
import lorikeet.IO;
import lorikeet.ecosphere.testing.article.Article;
import lorikeet.ecosphere.testing.article.ArticleReader;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.reader.LineReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CellArticleConstrueTest {
    private final CellArticleConstrue construe = new CellArticleConstrue();

    @Test
    public void testSample() {
        Article article = articleFrom("cell-articles/issue-debit-card.article");

        CellArticle cellArticle = construe.construe(article).orPanic();

        CellValue cell = new CellValue(
            "lorikeet.ecosphere.IssueDebitCard",
            Dict.of("paymentCompany", new StringValue("mastercard")),
            null,
            new BoolValue(true)
        );

        assertThat(cellArticle.getCell()).isEqualTo(cell);
    }

    @Test
    public void testWithParameterWithNoName() {
        Article article = articleFrom("cell-articles/create-savings-deposit.article");

        CellArticle cellArticle = construe.construe(article).orPanic();

        CellValue cell = new CellValue(
            "lorikeet.ecosphere.CreateSavingsDeposit",
            Dict.of("0", new NumberValue(34.67)),
            "java.lang.RuntimeException",
            null
        );

        assertThat(cellArticle.getCell()).isEqualTo(cell);
    }

    static Article articleFrom(String file) {
        return new ArticleReader().read(new LineReader(IO.readResource(file).orPanic())).orPanic();
    }
}
