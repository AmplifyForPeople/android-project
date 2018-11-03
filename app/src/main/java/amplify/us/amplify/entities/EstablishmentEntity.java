package amplify.us.amplify.entities;

public class EstablishmentEntity {
    private String name;
    private String email;
    private int phone;
    private String info;
    private  String location;
    private  String[] favourite_genres;
    private int image;

    public EstablishmentEntity(String name, String email, int phone, String info, String location, String[] favourite_genres, int image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.info = info;
        this.location = location;
        this.favourite_genres = favourite_genres;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getFavourite_genres() {
        return favourite_genres;
    }

    public void setFavourite_genres(String[] favourite_genres) {
        this.favourite_genres = favourite_genres;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
