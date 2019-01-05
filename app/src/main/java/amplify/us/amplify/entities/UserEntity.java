package amplify.us.amplify.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class UserEntity {
    private  int    id;
    private  String name;
    private  String age;
    private  String sex;
    private  String location;
    private  String favGenres;

    public UserEntity(int id,String name, String age, String sex, String location, String favGenres) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.favGenres = favGenres;
    }

    //PER EMMAGATZAMAR JSON
    public UserEntity(JSONObject data) {
        try {
            this.id = data.getInt("id");
            this.name = data.getString("name");
            this.age = data.getString("artist");
            this.sex = data.getString("album");
            this.location = data.getString("image");
            this.favGenres = data.getString("genres");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFavGenres() {
        return favGenres;
    }

    public void setFavGenres(String favGenres) {
        this.favGenres = favGenres;
    }
}
