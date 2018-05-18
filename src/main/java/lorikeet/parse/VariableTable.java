package lorikeet.parse;

import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Let;
import lorikeet.lang.Type;
import lorikeet.lang.Value.Variable;

import java.util.HashMap;
import java.util.Optional;

public class VariableTable {
    private final HashMap<String, Variable> table;

    // Only for testing
    public VariableTable() {
        this.table = new HashMap<String, Variable>();
    }

    public VariableTable(VariableTable vt) {
        this.table = new HashMap<String, Variable>();
        this.table.putAll(vt.table);
    }

    public VariableTable(Function func) {
        this.table = new HashMap<String, Variable>();
        for (Attribute attr : func.getAttributes()) {
            this.table.put(attr.getName(), new Variable(true, attr.getName(), attr.getType()));
        }
    }

    public Optional<Variable> get(String name) {
        return Optional.ofNullable(this.table.get(name));
    }

    public void add(Let let) {
        this.table.put(let.getName(), new Variable(false, let.getName(), let.getType()));
    }

}
