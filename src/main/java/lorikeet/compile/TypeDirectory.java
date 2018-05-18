package lorikeet.compile;

import lorikeet.lang.LorikeetType;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Type;
import lorikeet.error.TypeNameCollision;

import java.util.Optional;
import java.util.List;
import java.util.HashMap;

public class TypeDirectory {
    private final HashMap<Type, LorikeetType> table;

    public TypeDirectory() {
        this.table = new HashMap<Type, LorikeetType>();
    }

    public Optional<LorikeetType> get(Type type) {
        return Optional.ofNullable(this.table.get(type));
    }

    public Optional<TypeNameCollision> enter(SourceFile sourceFile) {
        final Optional<TypeNameCollision> a = this.enter(sourceFile.getStructs());
        if (a.isPresent()) {
            return a;
        }
        return this.enter(sourceFile.getModules());
    }

    public Optional<TypeNameCollision> enter(List<? extends LorikeetType> types) {
        for (LorikeetType lt : types) {
            final LorikeetType existing = this.table.get(lt.getType());
            if (existing != null) {
                return Optional.of(new TypeNameCollision(existing, lt));
            }
            this.table.put(lt.getType(), lt);
        }
        return Optional.empty();
    }
}
