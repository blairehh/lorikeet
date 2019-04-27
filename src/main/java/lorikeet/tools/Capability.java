package lorikeet.tools;

import lorikeet.Fun;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class Capability<Ability, Context> implements Rankable {
    private final Ability ability;
    private final Fun<Context, Boolean> contextPredicate;
    private final int rank;

    public Capability(Ability ability, Fun<Context, Boolean> contextPredicate, int rank) {
        this.ability = ability;
        this.contextPredicate = contextPredicate;
        this.rank = rank;
    }

    public final Ability getAbility() {
        return this.ability;
    }

    public final Fun<Context, Boolean> getContextPredicate() {
        return this.contextPredicate;
    }

    @Override
    public final int getRank() {
        return this.rank;
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Capability<?,?> that = (Capability<?,?>) o;

        return Objects.equals(this.getContextPredicate(), that.getContextPredicate())
            && Objects.equals(this.getAbility(), that.getAbility())
            && Objects.equals(this.getRank(), that.getRank());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.getAbility(), this.getContextPredicate(), this.getRank());
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
            .append("ability", this.getAbility())
            .append("rank", this.getRank())
            .toString();
    }
}
