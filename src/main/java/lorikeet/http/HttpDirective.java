package lorikeet.http;

public interface HttpDirective {
    boolean reject();
    HttpReply perform();
}
