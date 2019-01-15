package amplify.us.amplify.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import amplify.us.amplify.database.model.Song;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_song);
        dataSong = new ArrayList<>();

        ImageView img = findViewById(R.id.back_detail_song);
        img.setOnClickListener((View v) -> finish());

        getIncomingIntent();

        id_song = getIntent().getIntExtra("id",0);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String similars = url_major+"/songs/"+id_song+"/similar";
        queue.add(volleyRequest_rvSongs(similars));
    }

    private  void getIncomingIntent(){
        if(getIntent().hasExtra("song") && getIntent().hasExtra(("artist")) && getIntent().hasExtra(("album"))){
            String title = getIntent().getStringExtra("song");
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
}
