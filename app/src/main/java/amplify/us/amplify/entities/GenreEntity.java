package amplify.us.amplify.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class GenreEntity implements Serializable {
    public int id;
    public String name;

    public GenreEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public GenreEntity(JSONObject data) {
        try {
            this.name = data.getString("name");
            this.id = data.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
