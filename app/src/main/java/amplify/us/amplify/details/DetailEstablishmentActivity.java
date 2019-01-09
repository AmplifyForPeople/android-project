package amplify.us.amplify.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import amplify.us.amplify.R;
import amplify.us.amplify.entities.GenreEntity;
import amplify.us.amplify.entities.SongEntity;

public class DetailEstablishmentActivity extends AppCompatActivity {

    private  static final String TAG = "DetailEstablishment";

    String url_major = "http://brain.3utilities.com/AmplifyWeb/rest";
    SongEntity songEntity;
    int Voted = 0;
    ImageView Song1;
    ImageView Song2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_establishment);
        Log.d(TAG,"On create: started");

        ImageView img = (ImageView) findViewById(R.id.back_detail_establishment);
        img.setOnClickListener((View v) ->finish());

        getIncomingIntent();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String voted1 = url_major+"/songs/1";
        String voted2 = url_major+"/songs/2";
        queue.add(volleyVotedSongs(voted1));
        queue.add(volleyVotedSongs(voted2));

    }

    private  void getIncomingIntent(){
        Log.d(TAG,"getIncoming");
        if(getIntent().hasExtra("establishment_title") && getIntent().hasExtra(("info_title"))){
            String title = getIntent().getStringExtra("establishment_title");
            String info = getIntent().getStringExtra("info_title");
            String image = getIntent().getStringExtra("image");
            ArrayList<GenreEntity> genres = (ArrayList<GenreEntity>)getIntent().getSerializableExtra("genres");
            setData(title,info,image,genres);
        }
    }
    private void setData(String title, String info,String image,List<GenreEntity> genres){
        TextView title_establishment = findViewById(R.id.name_establishment);
        title_establishment.setText(title);
        TextView info_establishment = findViewById(R.id.description_establishment);
        info_establishment.setText(info);
        ImageView imageEstablishment = findViewById(R.id.img_establishment);
        Picasso.get()
                .load(image)
                .centerCrop()
                .fit()
                .into(imageEstablishment);
        TextView genre_string = findViewById(R.id.genre1);
        String result = " ";
        if(genres!=null && genres.size()>0){
            for(GenreEntity g : genres) {
                result += (g.name + " ");
            }
        }
        genre_string.setText(result);
    }

    public JsonObjectRequest volleyVotedSongs(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        songEntity = new SongEntity(response);
                        setVotedSongs();
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

    public void setVotedSongs(){
        if(Voted == 0){
            Song1 = findViewById(R.id.votedEstablishment1);
            Picasso.get()
                    .load(songEntity.getUrl_image())
                    .centerCrop()
                    .fit()
                    .into(Song1);
            Voted += 1;
        }else if (Voted == 1){
            Song2 = findViewById(R.id.votedEstablishment2);
            Picasso.get()
                    .load(songEntity.getUrl_image())
                    .centerCrop()
                    .fit()
                    .into(Song2);
            Voted = 0;
        }
    }
}
