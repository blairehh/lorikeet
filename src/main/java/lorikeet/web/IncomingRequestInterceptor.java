package lorikeet.web;

public interface IncomingRequestInterceptor {
    public void intercept(IncomingRequest request, OutgoingResponse response);

    default public IncomingRequestFilter getFilter() {
        return new IncomingRequestFilter()
            .filter("/**", HttpMethod.values());
    }
}
