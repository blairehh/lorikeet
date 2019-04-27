package lorikeet.tools;

import lorikeet.Err;

public interface CapabilityRegistry<Identifier, Ability, Context> {
    Err<Ability> find(Identifier identifier, Context context);
}
