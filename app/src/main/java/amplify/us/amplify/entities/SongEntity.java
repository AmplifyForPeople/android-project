package amplify.us.amplify.entities;

public class SongEntity {
    private  int    id;
    private  String name;
    private  String artist;
    private  String album;
    private  String url_image;

    public SongEntity(int id,String name, String artist, String album, String url_image) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.url_image = url_image;
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
}
