package lorikeet.error;

public class CouldNotFindQueryPlanExecutorForQueryPlan extends LorikeetException {
    private final Class<?> queryPlan;
    public CouldNotFindQueryPlanExecutorForQueryPlan(Class<?> queryPlan) {
        super(CouldNotFindQueryPlanExecutorForQueryPlan.class);
        this.queryPlan = queryPlan;
    }
}
