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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import amplify.us.amplify.R;
import amplify.us.amplify.entities.SongEntity;
import amplify.us.amplify.entities.UserEntity;

public class DetailSongActivity extends AppCompatActivity {

    String url_major = "http://brain.3utilities.com/AmplifyWeb/rest";
    SongEntity songEntity;
    int Similar = 0;
    ImageView Song1;
    ImageView Song2;
    ImageView Song3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_song);

        ImageView img = findViewById(R.id.back_detail_song);
        img.setOnClickListener((View v) -> finish());

        getIncomingIntent();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String similar1 = url_major+"/songs/1";
        String similar2 = url_major+"/songs/2";
        String similar3 = url_major+"/songs/3";
        queue.add(volleySimilarSongs(similar1));
        queue.add(volleySimilarSongs(similar2));
        queue.add(volleySimilarSongs(similar3));
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

    public JsonObjectRequest volleySimilarSongs(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        songEntity = new SongEntity(response);
                        setSimilarSongs();
                        //response.getString("image");
                        //updateInfoAmplifyJSON(rootView,response);
                        //user = new UserEntity(response);
                        //setUser();

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

    public void setSimilarSongs(){
        if(Similar == 0){
            Song1 = findViewById(R.id.similar);
            Picasso.get()
                    .load(songEntity.getUrl_image())
                    .centerCrop()
                    .fit()
                    .into(Song1);
            Similar += 1;
        }else if(Similar == 1){
            Song2 = findViewById(R.id.similar1);
            Picasso.get()
                    .load(songEntity.getUrl_image())
                    .centerCrop()
                    .fit()
                    .into(Song2);
            Similar += 1;
        }else{
            Song3 = findViewById(R.id.similar2);
            Picasso.get()
                    .load(songEntity.getUrl_image())
                    .centerCrop()
                    .fit()
                    .into(Song3);
            Similar = 0;
        }
    }
}
