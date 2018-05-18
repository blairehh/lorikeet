package lorikeet.lang;

import lorikeet.lang.SourceFile;
import lorikeet.lang.Package;
import lorikeet.lang.Struct;

import java.util.List;
import java.util.Objects;

public class SourceFile {

    private final Package pkg;
    private final List<Struct> structs;
    private final List<Module> modules;

    public SourceFile(Package pkg, List<Struct> structs, List<Module> modules) {
        this.pkg = pkg;
        this.structs = structs;
        this.modules = modules;
    }

    public Package getPackage() {
        return this.pkg;
    }

    public List<Struct> getStructs() {
        return this.structs;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        SourceFile that = (SourceFile)o;

        return Objects.equals(this.getPackage(), that.getPackage())
            && Objects.equals(this.getStructs(), that.getStructs())
            && Objects.equals(this.getModules(), that.getModules());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pkg, this.structs, this.modules);
    }

}
