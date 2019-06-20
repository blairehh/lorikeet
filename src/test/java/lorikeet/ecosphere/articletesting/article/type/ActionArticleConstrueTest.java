package lorikeet.ecosphere.articletesting.article.type;


import lorikeet.Dict;
import lorikeet.IO;
import lorikeet.ecosphere.articletesting.CellFormType;
import lorikeet.ecosphere.articletesting.article.Article;
import lorikeet.ecosphere.articletesting.article.ArticleReader;
import lorikeet.ecosphere.articletesting.data.BoolValue;
import lorikeet.ecosphere.articletesting.data.CellDefinition;
import lorikeet.ecosphere.articletesting.data.NumberValue;
import lorikeet.ecosphere.articletesting.data.StringValue;
import lorikeet.ecosphere.articletesting.reader.LineReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionArticleConstrueTest {
    private final ActionArticleConstrue construe = new ActionArticleConstrue();

    @Test
    public void testSample() {
        Article article = articleFrom("action-articles/issue-debit-card.article");

        ActionArticle actionArticle = construe.construe(article).orPanic();

        CellDefinition cell = new CellDefinition(
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

        CellDefinition cell = new CellDefinition(
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

        CellDefinition cell = new CellDefinition(
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

        CellDefinition cell = new CellDefinition(
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

        CellDefinition cell = new CellDefinition(
            "lorikeet.ecosphere.ChargePayment",
            Dict.of("0", new StringValue("AUD"), "1", new NumberValue(12.00)),
            null,
            new BoolValue(true)
        );

        assertThat(actionArticle.getFormType().orPanic()).isEqualTo(CellFormType.ACTION_2);
        assertThat(actionArticle.getCell()).isEqualTo(cell);
    }

    @Test
    public void testReadsNameAndDocumentation() {
        Article article = articleFrom("action-articles/charge-payment.article");

        ActionArticle actionArticle = construe.construe(article).orPanic();

        assertThat(actionArticle.getFilePath()).isEqualTo("action-articles/charge-payment.article");
        assertThat(actionArticle.getName().orPanic()).isEqualTo("Charge payment");
        assertThat(actionArticle.getDocumentation().orPanic()).isEqualTo("A payment should be made of 12 dollars");
    }

    static Article articleFrom(String file) {
        return new ArticleReader().read(file, new LineReader(IO.readResource(file).orPanic())).orPanic();
    }
}