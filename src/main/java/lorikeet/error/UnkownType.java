package lorikeet.error;

import lorikeet.lang.SourceFile;
import lorikeet.lang.Type;
import lorikeet.util.Console;

import java.util.Objects;

public class UnkownType implements CompileError {
    private final Type type;
    private final SourceFile sourceFile;

    public UnkownType(Type type, SourceFile sourceFile) {
        this.type = type;
        this.sourceFile = sourceFile;
    }

    public Type getType() {
        return this.type;
    }

    public SourceFile getSourceFile() {
        return this.sourceFile;
    }

    @Override
    public void outputTo(Console console) {
        // console.print("Error in %s", this.getSourceFile().getOrigin());
        console.print(
            "Type '%s' was not found to be in the source code or discoverable in dependencies",
            this.getType()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        UnkownType that = (UnkownType)o;

        return Objects.equals(this.getType(), that.getType())
            && Objects.equals(this.getSourceFile(), that.getSourceFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.sourceFile);
    }
}
