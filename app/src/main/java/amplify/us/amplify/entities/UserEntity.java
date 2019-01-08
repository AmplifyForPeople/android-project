package amplify.us.amplify.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserEntity {
    private  int    id;
    private  String name;
    private  String age;
    private  String email;
    private  String password;
    private  List<GenreEntity> genres = new ArrayList <>();
    private  List<SongEntity> favSongs = new ArrayList<>();


    public UserEntity(int id, String name, String age, String email, String password, List<GenreEntity> genres, List<SongEntity> favSongs) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.genres = genres;
        this.favSongs = favSongs;
    }

    //PER EMMAGATZAMAR JSON
    public UserEntity(JSONObject data) {
        try {
            this.id = data.getInt("id");
            this.name = data.getString("name");
            this.age = data.getString("author");
            this.email = data.getString("album");
            this.password = data.getString("password");
            if(data.getJSONArray("genres") != null && data.getJSONArray("genres").length()>0){
                for (int i=0;i<data.getJSONArray("genres").length();i++){
                    this.genres.add(new GenreEntity((JSONObject) data.getJSONArray("genres").get(i)));
                }
            }
            if(data.getJSONArray("songs") != null && data.getJSONArray("songs").length()>0){
                for (int i=0;i<data.getJSONArray("songs").length();i++){
                    this.favSongs.add(new SongEntity((JSONObject) data.getJSONArray("songs").get(i)));
                }
            }
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreEntity> genres) {
        this.genres = genres;
    }

    public List<SongEntity> getFavSongs() {
        return favSongs;
    }

    public void setFavSongs(List<SongEntity> favSongs) {
        this.favSongs = favSongs;
    }
}
