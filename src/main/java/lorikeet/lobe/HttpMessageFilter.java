package lorikeet.lobe;

public class HttpMessageFilter implements HttpSignalFilter {
    @Override
    public boolean matches(HttpSignal signal) {
        return true;
    }
}
