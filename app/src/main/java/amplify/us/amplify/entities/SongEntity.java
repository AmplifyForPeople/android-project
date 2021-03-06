package amplify.us.amplify.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class SongEntity {
    private  int    id;
    private  String name;
    private  String artist;
    private  String album;
    private  String url_image;
    private  int votes;

    public SongEntity(int id, String name, String artist, String album, String url_image,int votes) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.url_image = url_image;
        this.votes = votes;
    }

    //PER EMMAGATZAMAR JSON
    public SongEntity(JSONObject data) {
        try {
            this.id = data.getInt("id");
            this.name = data.getString("name");
            this.artist = data.getString("author");
            this.album = data.getString("album");
            this.url_image = data.getString("image");
            this.votes = data.getInt("votes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getUrl_image() {
        return url_image;
    }

    public int getVotes() { return votes; }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setUrl_image(String url_image) { this.url_image = url_image;}

    public void setId(int id) { this.id = id;}

    public void setVotes(int votes) { this.votes = votes; }
}
