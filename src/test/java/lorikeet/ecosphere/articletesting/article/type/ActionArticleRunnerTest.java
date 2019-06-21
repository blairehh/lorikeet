package lorikeet.ecosphere.articletesting.article.type;

import lorikeet.Dict;
import lorikeet.Seq;
import lorikeet.ecosphere.articletesting.article.RunResult;
import lorikeet.ecosphere.articletesting.data.AnyValue;
import lorikeet.ecosphere.articletesting.data.BoolValue;
import lorikeet.ecosphere.articletesting.data.CellDefinition;
import lorikeet.ecosphere.articletesting.data.ListValue;
import lorikeet.ecosphere.articletesting.data.NotSupportedValue;
import lorikeet.ecosphere.articletesting.data.NullValue;
import lorikeet.ecosphere.articletesting.data.NumberValue;
import lorikeet.ecosphere.articletesting.data.ObjectValue;
import lorikeet.ecosphere.articletesting.data.StringValue;
import lorikeet.ecosphere.articletesting.data.Value;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionArticleRunnerTest {

    private final ActionArticleRunner runner = new ActionArticleRunner();

    @Test
    public void testReturnsValue() {
        CellDefinition cell = new CellDefinition(
            "lorikeet.ecosphere.IssueDebitCard",
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
            "lorikeet.ecosphere.IssueDebitCard",
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
            "lorikeet.ecosphere.IssueDebitCard",
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
            "lorikeet.ecosphere.CreateSavingsDeposit",
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
        ObjectValue returnValue = new ObjectValue("lorikeet.ecosphere.User", returnData);

        CellDefinition cell = new CellDefinition(
            "lorikeet.ecosphere.CreateUser",
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
