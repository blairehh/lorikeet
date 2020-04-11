package lorikeet.http;

public interface HttpDirective {
    boolean reject();
    void perform();
}
