package lorikeet.lobe.articletesting.article.type;

import lorikeet.Dict;
import lorikeet.Seq;
import lorikeet.lobe.articletesting.article.RunResult;
import lorikeet.lobe.articletesting.data.AnyValue;
import lorikeet.lobe.articletesting.data.BoolValue;
import lorikeet.lobe.articletesting.data.CellDefinition;
import lorikeet.lobe.articletesting.data.ListValue;
import lorikeet.lobe.articletesting.data.NotSupportedValue;
import lorikeet.lobe.articletesting.data.NullValue;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.ObjectValue;
import lorikeet.lobe.articletesting.data.StringValue;
import lorikeet.lobe.articletesting.data.Value;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionArticleRunnerTest {

    private final ActionArticleRunner runner = new ActionArticleRunner();

    @Test
    public void testReturnsValue() {
        CellDefinition cell = new CellDefinition(
            "lorikeet.lobe.IssueDebitCard",
            Dict.of("paymentCompany", new StringValue("mastercard")),
            null,
            new BoolValue(true)
        );
        ActionArticle article = new ActionArticle(cell);

        RunResult result = runner.run(article).orPanic();

        assertThat(result.isReturnValueMatched()).isTrue();
        assertThat(result.isExceptionThrownMatched()).isTrue();
    }

    @Test
    public void testReturnsValueMatchesAgainstAnyValue() {
        CellDefinition cell = new CellDefinition(
            "lorikeet.lobe.IssueDebitCard",
            Dict.of("paymentCompany", new StringValue("mastercard")),
            null,
            new AnyValue()
        );
        ActionArticle article = new ActionArticle(cell);

        RunResult result = runner.run(article).orPanic();

        assertThat(result.isReturnValueMatched()).isTrue();
        assertThat(result.isExceptionThrownMatched()).isTrue();
    }

    @Test
    public void testParameterWithDbgWorksWithJustParameterNumber() {
        CellDefinition cell = new CellDefinition(
            "lorikeet.lobe.IssueDebitCard",
            Dict.of("0", new StringValue("mastercard")),
            null,
            new BoolValue(true)
        );
        ActionArticle article = new ActionArticle(cell);

        RunResult result = runner.run(article).orPanic();

        assertThat(result.isReturnValueMatched()).isTrue();
        assertThat(result.isExceptionThrownMatched()).isTrue();
    }

    @Test
    public void testThrowsException() {
        CellDefinition cell = new CellDefinition(
            "lorikeet.lobe.CreateSavingsDeposit",
            Dict.of("0", new NumberValue(34.67)),
            "java.lang.IllegalStateException",
            null
        );

        ActionArticle article = new ActionArticle(cell);

        RunResult result = runner.run(article).orPanic();

        assertThat(result.isReturnValueMatched()).isTrue();
        assertThat(result.isExceptionThrownMatched()).isTrue();
    }

    @Test
    public void testCellThatInvokesAnotherCell() {
        Dict<String, Value> arguments = Dict.empty();
        arguments = arguments
            .push("email", new StringValue("me@mail.com"))
            .push("password", new StringValue("secret"))
            .push("codes", new ListValue(Seq.of(new NumberValue(1), new NumberValue(2))));

        Dict<String, Value> returnData = Dict.empty();
        returnData = returnData
                .push("email", new StringValue("me@mail.com"))
                .push("password", new StringValue("secret"))
                .push("welcomeMessageSentAt", new NotSupportedValue())
                .push("account", new NullValue());
        ObjectValue returnValue = new ObjectValue("lorikeet.lobe.User", returnData);

        CellDefinition cell = new CellDefinition(
            "lorikeet.lobe.CreateUser",
            arguments,
            null,
            returnValue
        );

        ActionArticle article = new ActionArticle(cell);

        RunResult result = runner.run(article).orPanic();

        assertThat(result.isReturnValueMatched()).isTrue();
        assertThat(result.isExceptionThrownMatched()).isTrue();
    }
}
