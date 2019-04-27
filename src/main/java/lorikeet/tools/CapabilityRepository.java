package lorikeet.tools;

import lorikeet.Fun;
import lorikeet.Opt;
import lorikeet.Seq;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CapabilityRepository<Identifier, Ability, Context> {
    private static final int DEFAULT_RANK = -2;
    private final Fun<Context, Boolean> DEFAULT_CONTEXT_PREDICATE = (context -> true);

    private final List<Capability<Identifier, Ability, Context>> capabilities;

    public CapabilityRepository() {
        this.capabilities = new ArrayList<>();
    }

    public CapabilityRepository(List<Capability<Identifier, Ability, Context>> capabilities) {
        this.capabilities = capabilities;
    }

    public CapabilityRepository<Identifier, Ability, Context> add(Fun<Identifier, Boolean> getMatchPredicate,
                                                                  Ability ability, Fun<Context, Boolean> contextPredicate,
                                                                  int rank) {
        this.capabilities.add(new Capability<>(getMatchPredicate, ability, contextPredicate, rank));
        return this;
    }

    public CapabilityRepository<Identifier, Ability, Context> add(Fun<Identifier, Boolean> getMatchPredicate,
                                                                  Ability ability, Fun<Context, Boolean> contextPredicate) {
        return this.add(getMatchPredicate, ability, contextPredicate, DEFAULT_RANK);
    }

    public CapabilityRepository<Identifier, Ability, Context> add(Fun<Identifier, Boolean> getMatchPredicate,
                                                                  Ability ability, int rank) {
        return this.add(getMatchPredicate, ability, DEFAULT_CONTEXT_PREDICATE, rank);
    }


    public CapabilityRepository<Identifier, Ability, Context> add(Fun<Identifier, Boolean> getMatchPredicate, Ability ability) {
        return this.add(getMatchPredicate, ability, DEFAULT_CONTEXT_PREDICATE, DEFAULT_RANK);
    }

    public Opt<Ability> find(Identifier identifier, Context context) {
        return this.capabilities
            .stream()
            .filter(capability -> capability.getMatchPredicate().apply(identifier))
            .filter(capability -> capability.getContextPredicate().apply(context))
            .map(Capability::getAbility)
            .collect(Seq.collector())
            .first();
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        CapabilityRepository<?, ?, ?> that = (CapabilityRepository<?, ?, ?>) o;

        return Objects.equals(this.capabilities, that.capabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.capabilities);
    }
}
