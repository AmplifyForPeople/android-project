package amplify.us.amplify.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentEntity {
    private int id;
    private String name;
    private String url_image;
    private String email;
    private String info;
    private Long posLat;
    private Long posLong;
    private List<GenreEntity> genres = new ArrayList<>();

    public EstablishmentEntity(int id, String name, String url_image, String email, String info, Long posLat, Long posLong,List<GenreEntity> genres) {
        this.id = id;
        this.name = name;
        this.url_image = url_image;
        this.info = info;
        this.posLat = posLat;
        this.posLong = posLong;
        this.genres = genres;
    }

    //PER EMMAGATZAMAR JSON
    public EstablishmentEntity(JSONObject data) {
        try {
            this.name = data.getString("name");
            this.id = data.getInt("id");
            this.url_image = data.getString("imatge");
            this.info = data.getString("info");
            this.posLat = data.getLong("position_lat");
            this.posLong = data.getLong("position_lng");
            if(data.getJSONArray("genres") != null && data.getJSONArray("genres").length()>0){
                for (int i=0;i<data.getJSONArray("genres").length();i++){
                    this.genres.add(new GenreEntity((JSONObject) data.getJSONArray("genres").get(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreEntity> genres) {
        this.genres = genres;
    }

    public Long getPosLat() { return posLat; }

    public void setPosLat(Long posLat) { this.posLat = posLat; }

    public Long getPosLong() { return posLong; }

    public void setPosLong(Long posLong) { this.posLong = posLong; }

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
}