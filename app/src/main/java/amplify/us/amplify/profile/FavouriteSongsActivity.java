package amplify.us.amplify.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import amplify.us.amplify.R;
import amplify.us.amplify.adapters.FavouriteSongsAdapter;
import amplify.us.amplify.entities.SongEntity;

public class FavouriteSongsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView lvFavSongs;
    private List<SongEntity> mSongList;
    private SearchView mSearchView;
    FavouriteSongsAdapter adapter;
    //SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_song_list);

        mSongList = new ArrayList<>();

        setupSearchView();

        //return activity
        ImageView img = (ImageView) findViewById(R.id.back_favlist_song);
        img.setOnClickListener((View v) -> finish());

        //Preview list songs
        lvFavSongs = (ListView) findViewById(R.id.listViewSongsFav);

        //Add sample data for list
        //We can get data form DB, webService here
        mSongList.add(new SongEntity("Song 1", "artist 1", "album 1"));
        mSongList.add(new SongEntity("Song 2", "artist 2", "album 2"));
        mSongList.add(new SongEntity("Song 3", "artist 3", "album 3"));
        mSongList.add(new SongEntity("Song 4", "artist 4", "album 4"));
        mSongList.add(new SongEntity("Song 5", "artist 5", "album 5"));
        mSongList.add(new SongEntity("Song 6", "artist 6", "album 6"));
        mSongList.add(new SongEntity("Song 7", "artist 7", "album 7"));
        mSongList.add(new SongEntity("Song 8", "artist 8", "album 8"));
        mSongList.add(new SongEntity("Song 9", "artist 9", "album 9"));
        mSongList.add(new SongEntity("Song 10", "artist 10", "album 10"));


        //Update Data
        if (getIntent().hasExtra("nameSong") && getIntent().hasExtra(("nameArtist"))
                && getIntent().hasExtra(("nameAlbum"))) {
            SongEntity favSong = new SongEntity(getIntent().getStringExtra("nameSong")
                    , getIntent().getStringExtra("nameArtist"), getIntent().getStringExtra("nameAlbum"));

            mSongList.add(favSong);
            //saveList();
        }
        //Init Adapter
        adapter = new FavouriteSongsAdapter(this,mSongList);
        lvFavSongs.setAdapter(adapter);

        //Init ContextMenu
        registerForContextMenu(lvFavSongs);

        /*lvFavSongs.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Toast.makeText(getApplicationContext(),"Clicked:"+view.getTag(), Toast.LENGTH_SHORT).show();
        });*/
    }

    /*private void saveList(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mSongList);
        prefsEditor.putString("ListSong",json);
        prefsEditor.commit();
    }*/

    /*public void onResume(){
        super.onResume();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ListSong", "");
        Toast.makeText(getApplicationContext(),"Retorno "+json,Toast.LENGTH_LONG).show();
        Type type = new TypeToken<ArrayList<SongEntity>>(){}.getType();
        mSongList = gson.fromJson(json,type);

        //UPDATING
        adapter = new FavouriteSongsAdapter(this,mSongList);
        lvFavSongs.setAdapter(adapter);

        //Init ContextMenu
        registerForContextMenu(lvFavSongs);

        /*String json = sharedPreferences.getString("ListSong", "");
        Type type = new TypeToken<ArrayList<SongEntity>>(){}.getType();
        mSongList = gson.fromJson(json,type);*/
    //}


    private void setupSearchView()
    {
        mSearchView=(SearchView) findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search your favourite song");
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        if(v.getId()==R.id.listViewSongsFav){
            getMenuInflater().inflate(R.menu.options_fav_songs,menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){


            //Delete Item
            case R.id.option_delete:

                // Remove Song from My Favourite Songs

                mSongList.remove(mSongList.get(info.position));
                adapter = new FavouriteSongsAdapter(this,mSongList);
                lvFavSongs.setAdapter(adapter);

                // Toast -> NameSong deleted
                //Toast.makeText(this,mSongList.get(info.position).getName()+" Deleted",Toast.LENGTH_SHORT).show();

                //Copy Name of song
            case R.id.option_copy:
                if(item.getTitle().equals("Copy")){
                    ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

                    String nameSong = mSongList.get(info.position).getName();
                    ClipData clip = ClipData.newPlainText("text", nameSong);

                    clipboard.setPrimaryClip(clip);

                    // Toast -> NameSong Copied
                    Toast.makeText(this,mSongList.get(info.position).getName()+" Copied",Toast.LENGTH_SHORT).show();
                }




            default:
                return super.onContextItemSelected(item);
        }
    }


}
