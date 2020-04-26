package lorikeet.http;

import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

public interface HttpRouteProvider<R extends UsesLogging & UsesCoding> {
    HttpRouter<R> router();
}
