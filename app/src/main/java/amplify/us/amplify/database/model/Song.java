package amplify.us.amplify.database.model;

public class Song {
    public static final String TABLE_NAME = "songs";

    public static final class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ARTIST = "artist";
        public static final String ALBUM = "album";
        public static final String IMAGE_URL = "url";
    }

    public static final class Scripts {
        public static final String CREATE =
                "create table " + TABLE_NAME + "("
                        + Columns.ID + "int not null,"
                        + Columns.NAME + " text not null, "
                        + Columns.ARTIST + " text not null, "
                        + Columns.ALBUM + " text not null "
                        + Columns.IMAGE_URL + "text not null"
                        + ");";

        public static final String DESTROY = "drop table if exists " + TABLE_NAME;
        public static final String DELETE = "delete from " + TABLE_NAME;

    }
}
