package lorikeet.parse;

import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.Use;

import java.util.HashMap;
import java.util.Optional;


public class TypeTable {
    private final HashMap<String, Entry> table;

    public TypeTable() {
        this.table = new HashMap<String, Entry>();
        this.init();
    }

    private void init() {
        this.table.put("Str", new Entry(new Type(new Package("lorikeet", "core"), "Str"), false));
        this.table.put("Int", new Entry(new Type(new Package("lorikeet", "core"), "Int"), false));
        this.table.put("Dec", new Entry(new Type(new Package("lorikeet", "core"), "Dec"), false));
        this.table.put("Bol", new Entry(new Type(new Package("lorikeet", "core"), "Bol"), false));

        this.table.put("Seq", new Entry(new Type(new Package("lorikeet", "core"), "Seq"), false));
        this.table.put("Lst", new Entry(new Type(new Package("lorikeet", "core"), "Lst"), false));
        this.table.put("Set", new Entry(new Type(new Package("lorikeet", "core"), "Set"), false));
        this.table.put("Map", new Entry(new Type(new Package("lorikeet", "core"), "Map"), false));
        this.table.put("Dic", new Entry(new Type(new Package("lorikeet", "core"), "Dic"), false));
        this.table.put("Que", new Entry(new Type(new Package("lorikeet", "core"), "Que"), false));
        this.table.put("Stk", new Entry(new Type(new Package("lorikeet", "core"), "Stk"), false));

        this.table.put("Opt", new Entry(new Type(new Package("lorikeet", "core"), "Opt"), false));
        this.table.put("Err", new Entry(new Type(new Package("lorikeet", "core"), "Err"), false));
    }

    public Optional<Entry> get(String name) {
        return Optional.ofNullable(this.table.get(name));
    }

    public Optional<Entry> get(Type type) {
        return Optional.ofNullable(this.table.get(type.getName()));
    }

    public void add(Use use) {
        this.table.put(use.getAlias(), new Entry(new Type(use.getPackage(), use.getName()), false));
    }

    public void add(Type type) {
        this.table.put(type.getName(), new Entry(type, true));
    }

    public static class Entry {
        private final Type type;
        private final boolean exportable;

        public Entry(Type type, boolean exportable) {
            this.type = type;
            this.exportable = exportable;
        }

        public Type getType() {
            return this.type;
        }

        public boolean isExportable() {
            return this.exportable;
        }
    }
}
