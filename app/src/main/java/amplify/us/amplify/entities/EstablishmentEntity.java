package amplify.us.amplify.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class EstablishmentEntity {
    private int id;
    private String name;
    private String url_image;
    private String email;
    private String info;
    private String distance;
    private String genres;

    public EstablishmentEntity(int id, String name, String ur_image, String email, String info, String distance, String genres) {
        this.id = id;
        this.name = name;
        this.url_image = ur_image;
        this.email = email;
        this.info = info;
        this.distance = distance;
        this.genres = genres;
    }

    //PER EMMAGATZAMAR JSON
    public EstablishmentEntity(JSONObject data) {
        try {
            this.name = data.getString("name");
            this.id = data.getInt("id");
            this.url_image = data.getString("image");
            this.email = data.getString("email");
            this.info = data.getString("info");
            this.distance = data.getString("distance");
            this.genres = data.getString("genres");
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

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String ur_image) {
        this.url_image = ur_image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}