package lorikeet;

import lorikeet.coding.JsonEncodable;
import lorikeet.lobe.UsesCoding;

public class User<R extends UsesCoding> implements JsonEncodable<R> {
    String name;

    public User(String name) {
        this.name = name;
    }
}
