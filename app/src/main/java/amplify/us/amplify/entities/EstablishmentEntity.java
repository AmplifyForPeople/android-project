package amplify.us.amplify.entities;

public class EstablishmentEntity {
    private final String name;
    private final String info;


    public EstablishmentEntity(String name,String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

}