package lorikeet.http;

import lorikeet.lobe.ResourceInsignia;

public class HttpServerInsignia implements ResourceInsignia {
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.getClass().equals(this.getClass());
    }

    @Override
    public int hashCode() {
        return HttpServerInsignia.class.hashCode();
    }
}
