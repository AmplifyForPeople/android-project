package amplify.us.amplify.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import amplify.us.amplify.database.model.Song;

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "favSongsAmplify" + "_db";

    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Song.Scripts.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("DataBaseHelper", "oldVersion: " + oldVersion + " newVersion: " + newVersion);
        /*Prefs.getInstance(context).setKeyTag(""); //clean the token tags to download again the new tags
        sqLiteDatabase.execSQL(Tag.Scripts.DESTROY);*/

        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase getDB() {
        if(sqLiteDatabase == null || !sqLiteDatabase.isOpen())
            sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase;
    }

    public void closeDB() {
        this.close();
        if(sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }
}
