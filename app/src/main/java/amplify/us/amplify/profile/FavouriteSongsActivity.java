package amplify.us.amplify.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import amplify.us.amplify.R;
import amplify.us.amplify.adapters.FavouriteSongsAdapter;
import amplify.us.amplify.entities.SongEntity;

public class FavouriteSongsActivity extends AppCompatActivity {

    private ListView lvFavSongs;
    private List<SongEntity> mSongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_song_list);
        mSongList = new ArrayList<>();

        //return activity
        ImageView img = (ImageView) findViewById(R.id.back_favlist_song);
        img.setOnClickListener((View v) -> finish());

        //Preview list songs
        lvFavSongs = (ListView) findViewById(R.id.listViewSongsFav);

        //Add sample data for list
        //We can get data form DB, webService here
        mSongList.add(new SongEntity("Song 1","artist 1","album 1"));
        mSongList.add(new SongEntity("Song 2","artist 2","album 2"));
        mSongList.add(new SongEntity("Song 3","artist 3","album 3"));
        mSongList.add(new SongEntity("Song 4","artist 4","album 4"));
        mSongList.add(new SongEntity("Song 5","artist 5","album 5"));
        mSongList.add(new SongEntity("Song 6","artist 6","album 6"));
        mSongList.add(new SongEntity("Song 7","artist 7","album 7"));
        mSongList.add(new SongEntity("Song 8","artist 8","album 8"));
        mSongList.add(new SongEntity("Song 9","artist 9","album 9"));
        mSongList.add(new SongEntity("Song 10","artist 10","album 10"));

        //Init Adapter
        lvFavSongs.setAdapter(new FavouriteSongsAdapter(getApplicationContext(), mSongList));

        lvFavSongs.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Toast.makeText(getApplicationContext(),"Clicked:"+view.getTag(), Toast.LENGTH_SHORT).show();
        });

    }
}