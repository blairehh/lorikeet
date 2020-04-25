package lorikeet;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name") private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
