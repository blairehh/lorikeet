package lorikeet.tools;

import lorikeet.Dict;
import lorikeet.Fun;
import lorikeet.Opt;
import lorikeet.Seq;

import java.util.Comparator;
import java.util.Objects;

public class CapabilityRepository<Identifier, Ability, Context> {
    private static final int DEFAULT_RANK = -2;
    private final Fun<Context, Boolean> DEFAULT_CONTEXT_PREDICATE = (context -> true);

    private final Dict<Identifier, Seq<Capability<Ability, Context>>> capabilities;

    public CapabilityRepository() {
        this.capabilities = Dict.empty();
    }

    public CapabilityRepository(Dict<Identifier, Seq<Capability<Ability, Context>>> capabilities) {
        this.capabilities = capabilities;
    }

    public CapabilityRepository<Identifier, Ability, Context> add(Identifier identifier, Ability ability, Fun<Context, Boolean> contextPredicate, int rank) {
        return new CapabilityRepository<>(Dict.pushpush(this.capabilities, identifier, new Capability<>(ability, contextPredicate, rank)));
    }

    public CapabilityRepository<Identifier, Ability, Context> add(Identifier identifier, Ability ability, Fun<Context, Boolean> contextPredicate) {
        return this.add(identifier, ability, contextPredicate, DEFAULT_RANK);
    }

    public CapabilityRepository<Identifier, Ability, Context> add(Identifier identifier, Ability ability, int rank) {
        return this.add(identifier, ability, DEFAULT_CONTEXT_PREDICATE, rank);
    }


    public CapabilityRepository<Identifier, Ability, Context> add(Identifier identifier, Ability ability) {
        return this.add(identifier, ability, DEFAULT_CONTEXT_PREDICATE, DEFAULT_RANK);
    }

    public Opt<Ability> find(Identifier identifier, Context context) {
        return this.capabilities.find(identifier)
            .orElse(Seq.empty())
            .stream()
            .filter(capability -> capability.getContextPredicate().apply(context))
            .sorted(Comparator.reverseOrder())
            .map(Capability::getAbility)
            .filter(item -> item != null)
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
