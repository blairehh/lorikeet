package lorikeet.ecosphere.testing.article.type;


import lorikeet.Dict;
import lorikeet.IO;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.article.Article;
import lorikeet.ecosphere.testing.article.ArticleReader;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.reader.LineReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionArticleConstrueTest {
    private final ActionArticleConstrue construe = new ActionArticleConstrue();

    @Test
    public void testSample() {
        Article article = articleFrom("action-articles/issue-debit-card.article");

        ActionArticle actionArticle = construe.construe(article).orPanic();

        CellValue cell = new CellValue(
            "lorikeet.ecosphere.IssueDebitCard",
            Dict.of("0", new StringValue("mastercard")),
            null,
            new BoolValue(true)
        );

        assertThat(actionArticle.getFormType().isPresent()).isFalse();
        assertThat(actionArticle.getCell()).isEqualTo(cell);
    }

    @Test
    public void testWithToReturnStanza() {
        Article article = articleFrom("action-articles/issue-debit-card-with-to-return-stanza.article");

        ActionArticle actionArticle = construe.construe(article).orPanic();

        CellValue cell = new CellValue(
            "lorikeet.ecosphere.IssueDebitCard",
            Dict.of("0", new StringValue("mastercard")),
            null,
            new BoolValue(true)
        );

        assertThat(actionArticle.getFormType().isPresent()).isFalse();
        assertThat(actionArticle.getCell()).isEqualTo(cell);
    }

    @Test
    public void testWithParameterWithNoName() {
        Article article = articleFrom("action-articles/create-savings-deposit.article");

        ActionArticle actionArticle = construe.construe(article).orPanic();

        CellValue cell = new CellValue(
            "lorikeet.ecosphere.CreateSavingsDeposit",
            Dict.of("0", new NumberValue(34.67)),
            "java.lang.RuntimeException",
            null
        );

        assertThat(actionArticle.getFormType().isPresent()).isFalse();
        assertThat(actionArticle.getCell()).isEqualTo(cell);
    }

    @Test
    public void testWithToThrowStanza() {
        Article article = articleFrom("action-articles/create-savings-deposit-with-to-throw-stanza.article");

        ActionArticle actionArticle = construe.construe(article).orPanic();

        CellValue cell = new CellValue(
            "lorikeet.ecosphere.CreateSavingsDeposit",
            Dict.of("0", new NumberValue(34.67)),
            "java.lang.RuntimeException",
            null
        );

        assertThat(actionArticle.getFormType().isPresent()).isFalse();
        assertThat(actionArticle.getCell()).isEqualTo(cell);
    }



    @Test
    public void testWithFormSpecified() {
        Article article = articleFrom("action-articles/charge-payment.article");

        ActionArticle actionArticle = construe.construe(article).orPanic();

        CellValue cell = new CellValue(
            "lorikeet.ecosphere.ChargePayment",
            Dict.of("0", new StringValue("AUD"), "1", new NumberValue(12.00)),
            null,
            new BoolValue(true)
        );

        assertThat(actionArticle.getFormType().orPanic()).isEqualTo(CellFormType.ACTION_2);
        assertThat(actionArticle.getCell()).isEqualTo(cell);
    }

    static Article articleFrom(String file) {
        return new ArticleReader().read(new LineReader(IO.readResource(file).orPanic())).orPanic();
    }
}