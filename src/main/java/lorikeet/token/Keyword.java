package lorikeet.token;

import java.util.Optional;

public enum Keyword {

    PACKAGE       ("package"),
    USE           ("use"),
    EXPR          ("expr"),
    STRUCT        ("struct"),
    ALGEBRAIC     ("algebraic"),
    MODULE        ("module"),
    FUNC          ("func"),
    OF            ("of"),
    AS            ("as"),
    LET           ("let");


    private String value;

    private Keyword(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Optional<Keyword> fromString(String kw) {
        for (Keyword keyword : Keyword.values()) {
            if (keyword.toString().equals(kw)) {
                return Optional.of(keyword);
            }
        }
        return Optional.empty();
    }
}
