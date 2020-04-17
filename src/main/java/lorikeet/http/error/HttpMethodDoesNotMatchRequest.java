package lorikeet.http.error;

public class HttpMethodDoesNotMatchRequest extends RuntimeException {

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(HttpMethodDoesNotMatchRequest.class);
    }

    @Override
    public int hashCode() {
        return HttpMethodDoesNotMatchRequest.class.hashCode();
    }
}
