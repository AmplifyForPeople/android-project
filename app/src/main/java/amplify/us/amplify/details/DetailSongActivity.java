package amplify.us.amplify.details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amplify.us.amplify.R;
import amplify.us.amplify.adapters.FavouriteSongsAdapter;
import amplify.us.amplify.database.model.Song;
import amplify.us.amplify.entities.EstablishmentEntity;
import amplify.us.amplify.entities.SongEntity;
import amplify.us.amplify.entities.UserEntity;

public class DetailSongActivity extends AppCompatActivity {

    String url_major = "http://brain.3utilities.com/AmplifyWeb/rest";
    SongEntity songEntity;
    int Similar = 0;
    int id_song = 0;
    ImageView Song1;
    ImageView Song2;
    ArrayList<SongEntity> dataSong;
    UserEntity user;
    String title;
    Button button;

    public static final String USERS = "MyUser";
    int user_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_song);
        dataSong = new ArrayList<>();

        //LOAD USER
        SharedPreferences prefs = getSharedPreferences(USERS, MODE_PRIVATE);
        user_id = prefs.getInt("user",0);

        ImageView img = findViewById(R.id.back_detail_song);
        img.setOnClickListener((View v) -> finish());

        getIncomingIntent();

        id_song = getIntent().getIntExtra("id",0);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String similars = url_major+"/songs/"+id_song+"/similar";
        queue.add(volleyRequest_rvSongs(similars));

        String url_user = url_major+"/users/"+user_id;
        JsonObjectRequest requestUser = volleyRequest_rvUser(url_user);
        queue.add(requestUser);

        button = findViewById(R.id.addFavourite);

        button.setOnClickListener((View v) -> {
            Toast.makeText(getApplicationContext(),"Added Song",Toast.LENGTH_LONG).show();
            button.setVisibility(View.GONE);
            String url = url_major+"/songs/"+user.getId()+"/"+id_song;
            JsonObjectRequest requestAdd = volleyPostRequest(url);
            queue.add(requestAdd);
        });

    }

    private  void getIncomingIntent(){
        if(getIntent().hasExtra("song") && getIntent().hasExtra(("artist")) && getIntent().hasExtra(("album"))){
            title = getIntent().getStringExtra("song");
            String artist = getIntent().getStringExtra("artist");
            String album = getIntent().getStringExtra("album");
            String image_url = getIntent().getStringExtra("url");
            setData(title,artist,album,image_url);
        }
    }
    private void setData(String title, String artist, String album, String url){
        TextView title_album = findViewById(R.id.name_song);
        title_album.setText(title);
        TextView artist_name = findViewById(R.id.artist_name);
        artist_name.setText(artist);
        TextView album_name = findViewById(R.id.album_name);
        album_name.setText(album);
        ImageView imageView = findViewById(R.id.image_view_song);
        Picasso.get().load(url)
                .centerCrop()
                .fit()
                .into(imageView);
    }


    public JsonArrayRequest volleyRequest_rvSongs(String url){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SIMILAR", response.toString());
                        try{
                            for(int i = 0;i<response.length();i++){
                                JSONObject result = response.getJSONObject(i);
                                dataSong.add(new SongEntity(result));
                            }
                            setSimilarSongs(dataSong);
                        }catch (JSONException arg){
                            Log.d("RIPARNAU", response.toString());
                            arg.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
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
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
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

        return jsonArrayRequest;
    }

    public JsonObjectRequest volleyRequest_rvUser(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());

                        //response.getString("image");
                        //updateInfoAmplifyJSON(rootView,response);
                        user = new UserEntity(response);
                        setButton();
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

    public void setSimilarSongs(ArrayList<SongEntity> song){
        for (SongEntity s: song
             ) {
            if(Similar == 0){
                Song1 = findViewById(R.id.similar);
                Picasso.get()
                        .load(s.getUrl_image())
                        .centerCrop()
                        .fit()
                        .into(Song1);
                Similar += 1;
            }else if(Similar == 1){
                Song2 = findViewById(R.id.similar1);
                Picasso.get()
                        .load(s.getUrl_image())
                        .centerCrop()
                        .fit()
                        .into(Song2);
                Similar += 1;
            }
        }
    }

    public void setButton(){
        for (SongEntity s: user.getFavSongs()
                ) {
            if(s.getName().equals(title)){
                button.setVisibility(View.GONE);

            }
        }
    }

    public JsonObjectRequest volleyPostRequest(String url){
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
