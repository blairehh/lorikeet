package lorikeet.ecosphere.testing;

public class InteractionTestCase<TestCaseReturn, ActionReturn> extends TestCase<TestCaseReturn> {

    InteractionTestCase(TestCase<TestCaseReturn> testCase) {
        super(testCase);
    }

    public TestCase<TestCaseReturn> thenReturn(ActionReturn value) {
        super.axon.addStub(super.interaction.withReturnValue(value));
        return new TestCase<>(this);
    }

    public TestCase<TestCaseReturn> thenThrow(RuntimeException exception) {
        super.axon.addStub(super.interaction.withExceptionToThrow(exception));
        return new TestCase<>(this);
    }
}
