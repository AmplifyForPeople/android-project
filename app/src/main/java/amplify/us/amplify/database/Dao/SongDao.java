package amplify.us.amplify.database.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import amplify.us.amplify.database.DataBaseHelper;
import amplify.us.amplify.database.model.Song;
import amplify.us.amplify.entities.SongEntity;

public class SongDao {
    private DataBaseHelper dataBaseHelper;

    public SongDao(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public long insert(String name, String album, String artist) {

        ContentValues values = new ContentValues();
        values.put(Song.Columns.NAME, name);
        values.put(Song.Columns.ARTIST, artist);
        values.put(Song.Columns.ALBUM, album);

        long id = dataBaseHelper.getDB().insert(Song.TABLE_NAME, null, values);

        dataBaseHelper.closeDB();

        return id;
    }

    public long insert(SongEntity songEntity) {
        return insert(songEntity.getName(),songEntity.getArtist(),songEntity.getAlbum());
    }


    public List<SongEntity> getAllSong(String word) {

        List<SongEntity> list = new ArrayList<>();

        String columns[] = new String[]{Song.Columns.NAME,Song.Columns.ARTIST,Song.Columns.ALBUM};
        String values[] = new String[]{ "'" + word + "%'"};

      StringBuilder where = new StringBuilder();
        where.append(Song.Columns.NAME + " LIKE ? ");
        String orderBy = Song.Columns.NAME + " ASC";
//        Cursor cursor = dataBaseHelper.getDB().query(Tag.TABLE_NAME, columns, where.toString(), values, null, null, orderBy);
        Cursor cursor = dataBaseHelper.getDB().query(Song.TABLE_NAME, columns, where.toString(), values, null, null, orderBy);

        if (cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SongEntity song = build(cursor);
                list.add(song);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return list;
    }

    private SongEntity build(Cursor cursor) {
        SongEntity entity = new SongEntity("","","","");

        entity.setName(cursor.getString(cursor.getColumnIndex(Song.Columns.NAME)));
        entity.setArtist(cursor.getString(cursor.getColumnIndex(Song.Columns.ARTIST)));
        entity.setAlbum(cursor.getString(cursor.getColumnIndex(Song.Columns.ALBUM)));
        entity.setUrl_image(cursor.getString(cursor.getColumnIndex(Song.Columns.IMAGE_URL)));

        return entity;
    }

    public void truncate() {
        dataBaseHelper.getDB().execSQL(Song.Scripts.DELETE);
        dataBaseHelper.closeDB();
    }
}
