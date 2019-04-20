package lorikeet.web;

public interface IncomingRequestInterceptor extends Comparable<IncomingRequestInterceptor> {
    void intercept(IncomingRequest request, OutgoingResponse response);

    default IncomingRequestFilter getFilter() {
        return new IncomingRequestFilter()
            .filter("/**", HttpMethod.values());
    }

    default int getRank() {
        return -1;
    }

    @Override
    default int compareTo(IncomingRequestInterceptor filter) {
        if (this.getRank() == -1 && filter.getRank() == -1) {
            return 0;
        }
        if (this.getRank() == -1) {
            return -1;
        }
        if (filter.getRank() == -1) {
            return 1;
        }
        return filter.getRank() - this.getRank();
    }

}
