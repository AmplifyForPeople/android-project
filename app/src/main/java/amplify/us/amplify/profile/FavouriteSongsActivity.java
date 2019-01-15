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
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import amplify.us.amplify.R;
import amplify.us.amplify.adapters.FavouriteSongsAdapter;
import amplify.us.amplify.database.Dao.SongDao;
import amplify.us.amplify.entities.SongEntity;
import amplify.us.amplify.entities.UserEntity;

public class FavouriteSongsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView lvFavSongs;
    private List<SongEntity> mSongList;
    private SearchView mSearchView;
    RequestQueue queue;

    FavouriteSongsAdapter adapter;
    SongDao songDao;
    //SharedPreferences sharedPreferences;

    String url_major = "http://brain.3utilities.com/AmplifyWeb/rest";
    UserEntity user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_song_list);

        songDao = new SongDao(this);
        mSongList = new ArrayList<>();

        setupSearchView();

        //return activity
        ImageView img = (ImageView) findViewById(R.id.back_favlist_song);
        img.setOnClickListener((View v) -> finish());

        //Preview list songs
        lvFavSongs = (ListView) findViewById(R.id.listViewSongsFav);

        //Add sample data for list
        //We can get data form DB, webService here
        //mSongList.add(new SongEntity(1,"Angels", "Robbie Williams", "Robbie Wiliams album","https://upload.wikimedia.org/wikipedia/en/thumb/9/98/Angels_cover.png/220px-Angels_cover.png",0));


        //Update Data
        /*if (getIntent().hasExtra("nameSong") && getIntent().hasExtra(("nameArtist"))
                && getIntent().hasExtra(("nameAlbum"))) {
            SongEntity favSong = new SongEntity(1,getIntent().getStringExtra("nameSong")
                    , getIntent().getStringExtra("nameArtist"), getIntent().getStringExtra("nameAlbum"),"url");

            mSongList.add(favSong);
            //songDao.insert(favSong);
            //saveList();
        }*/

        //Init Adapter
        //mSongList = songDao.getAllSong("");


        /*lvFavSongs.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Toast.makeText(getApplicationContext(),"Clicked:"+view.getTag(), Toast.LENGTH_SHORT).show();
        });*/

        queue = Volley.newRequestQueue(getApplicationContext());
        String url_user = url_major+"/users/1";
        JsonObjectRequest requestUser = volleyRequest_rvUser(url_user);
        queue.add(requestUser);



        //Init ContextMenu
        registerForContextMenu(lvFavSongs);

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


    public JsonObjectRequest volleyRequest_rvUser(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());

                        //response.getString("image");
                        //updateInfoAmplifyJSON(rootView,response);
                        user = new UserEntity(response);
                        setListFavourites();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE", error.toString());
            }
        })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        return jsonObjectRequest;
    }


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
                if(item.getTitle().equals("Delete")){
                    // Remove Song from My Favourite Songs

                    String url = url_major+"/songs/"+user.getId()+"/"+mSongList.get(info.position).getId()+"/delete";
                    JsonObjectRequest requestAdd = volleyPostRequestDelete(url);
                    queue.add(requestAdd);

                    mSongList.remove(mSongList.get(info.position));
                    adapter = new FavouriteSongsAdapter(this,mSongList);
                    lvFavSongs.setAdapter(adapter);

                    //Toast.makeText(this,mSongList.get(info.position).getName()+" Deleted",Toast.LENGTH_SHORT).show();

                    break;
                }



                // Toast -> NameSong deleted

                //Copy Name of song
                break;
            case R.id.option_copy:
                if(item.getTitle().equals("Copy")){
                    ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

                    String nameSong = mSongList.get(info.position).getName();
                    ClipData clip = ClipData.newPlainText("text", nameSong);

                    clipboard.setPrimaryClip(clip);

                    // Toast -> NameSong Copied
                    Toast.makeText(this,mSongList.get(info.position).getName()+" Copied",Toast.LENGTH_SHORT).show();
                    break;
                }
                break;




            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    public void setListFavourites(){
        for (SongEntity s: user.getFavSongs()
             ) {
            mSongList.add(s);
        }
        adapter = new FavouriteSongsAdapter(this,mSongList);
        lvFavSongs.setAdapter(adapter);
    }

    public JsonObjectRequest volleyPostRequestDelete(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response", error.toString());
            }
        }
        ){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Accept", "application/json");
                headers.put("Content-type","application/json");
                return headers;
            }
        };
        return jsonObjectRequest;
    }


}
