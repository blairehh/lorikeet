package lorikeet.http.msg;

import com.google.gson.annotations.SerializedName;

public class SampleJson {
    @SerializedName("id") public final int id;
    @SerializedName("name") public final String name;
    @SerializedName("active") public final boolean active;

    public SampleJson(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }
}
