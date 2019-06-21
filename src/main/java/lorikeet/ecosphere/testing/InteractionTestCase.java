package lorikeet.ecosphere.testing;

public class InteractionTestCase<TestCaseReturn, ActionReturn> extends TestCase<TestCaseReturn> {

    InteractionTestCase(TestCase<TestCaseReturn> testCase) {
        super(testCase);
    }

    public TestCase<TestCaseReturn> thenReturn(ActionReturn value) {
        super.axon.addStub(interaction.withReturnValue(value));
        return new TestCase<>(this);
    }
}
