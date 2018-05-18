package lorikeet.error;

import lorikeet.lang.LorikeetType;
import lorikeet.util.Console;

import java.util.Objects;

public class TypeNameCollision implements CompileError {

    private final LorikeetType existing;
    private final LorikeetType redeclared;

    public TypeNameCollision(LorikeetType existing, LorikeetType redeclared) {
        this.existing = existing;
        this.redeclared = redeclared;
    }

    public LorikeetType getExisting() {
        return this.existing;
    }

    public LorikeetType getRedeclared() {
        return this.redeclared;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in project!");
        console.print("There has been two types found with the same package and type name");
        console.print(" in %s", this.getExisting().getOrigin());
        console.print(" and %s", this.getRedeclared().getOrigin());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        TypeNameCollision that = (TypeNameCollision)o;

        return Objects.equals(this.getExisting(), that.getExisting())
            && Objects.equals(this.getRedeclared(), that.getRedeclared());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.existing, this.redeclared);
    }
}
