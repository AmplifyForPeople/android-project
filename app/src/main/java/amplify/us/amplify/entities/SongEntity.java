package amplify.us.amplify.entities;

public class SongEntity {
    private final String name;
    private final String artist;
    private final String album;

    public SongEntity(String name, String artist, String album) {
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

}
