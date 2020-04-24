package lorikeet.coding;

public enum InternetMediaType {
    APPLICATION_JSON        ("application/json"),
    TEXT_PLAIN              ("text/plain");

    private final String tree;

    InternetMediaType(String tree) {
        this.tree = tree;
    }

    public String tree() {
        return this.tree;
    }
}
