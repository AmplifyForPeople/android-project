package amplify.us.amplify.bottom_menu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amplify.us.amplify.R;
import amplify.us.amplify.database.Dao.SongDao;
import amplify.us.amplify.details.DetailSongActivity;
import amplify.us.amplify.entities.EstablishmentEntity;
import amplify.us.amplify.entities.GenreEntity;
import amplify.us.amplify.entities.SongEntity;
import amplify.us.amplify.entities.UserEntity;
import amplify.us.amplify.profile.FavouriteSongsActivity;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AmplifySiteFragment extends Fragment {

    TextView establishmentName;
    TextView nameSongAmplify;
    TextView artistSongAmplify;
    TextView albumSongAmplify;
    ImageView imgAmplify;
    ImageButton Song1;
    ImageButton Song2;
    ArrayList<SongEntity> dataSong;
    ImageButton addToFav;
    int Similar = 0;
    Boolean first = false;
    UserEntity userEntity;
    private DiscoverFragment discoverFragment;
    SongEntity songAmplify;
    private Handler handler;
    MyRunnable runnable;
    EstablishmentEntity establishmentEntity;
    SongEntity songAmplifySimilar;
    SongEntity songAmplifySimilar2;
    String url_major = "http://brain.3utilities.com/AmplifyWeb/rest/";
    String url_similars ="";
    private String m_Text="";
    RequestQueue queuePost;
    RequestQueue queue;
    RequestQueue queue2;
    JsonArrayRequest similars;
    private Boolean flag = false;




    public AmplifySiteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Similar = 0;
        discoverFragment = new DiscoverFragment();
        dataSong = new ArrayList<>();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_amplify_site, container, false);

        if(!flag){
            flag = true;
            String urlPost = url_major+"establishments/go_in/1/1";
            queuePost = Volley.newRequestQueue(getActivity().getApplicationContext());

            JsonObjectRequest post = volleyPostRequest(rootView,urlPost);
            queuePost.add(post);
        }

        // Pulse Animation (Amplify Active)
        PulsatorLayout pulse = rootView.findViewById(R.id.pulsator);
        pulse.start();

        /*if(flag){
            String urlPost = url_major+"establishments/go_in/1/1";
            queuePost = Volley.newRequestQueue(getActivity().getApplicationContext());

            JsonObjectRequest post = volleyPostRequest(rootView,urlPost);
            queuePost.add(post);

        }*/

        //Updating songs
        String url_update_song = url_major+"establishments/1";
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue2 = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest update = volleyRequest(rootView,url_update_song);
        queue.add(update);

        String url_user = url_major+"users/1";
        JsonObjectRequest requestUser = volleyRequest_rvUser(url_user,rootView);
        queue.add(requestUser);


        handler = new Handler();
        runnable = new MyRunnable(url_update_song,rootView,queue);
        handler.post(runnable);


        //////// BUTTONS & ACCESS TO OTHER ACTIVITIES /////

        // ADD TO FAV SONGS
        addToFav = (ImageButton) rootView.findViewById(R.id.addToFav);
        addToFav.setOnClickListener(v -> {
            Toast.makeText(getContext(),songAmplify.getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(rootView.getContext(),"Added Song",Toast.LENGTH_LONG).show();
            addToFav.setVisibility(View.GONE);
            String url = url_major+"songs/"+userEntity.getId()+"/"+songAmplify.getId();
            JsonObjectRequest requestAdd = volleyPostRequest(url);
            queue.add(requestAdd);

        });

        //Similar song to -> song detail (simple)
        ImageButton card_view = rootView.findViewById(R.id.similar_amp); // creating a CardView and assigning a value.

        card_view.setOnClickListener((View v) -> {
            Intent intent = new Intent(v.getContext(),DetailSongActivity.class);
            intent.putExtra("song",songAmplifySimilar.getName());
            intent.putExtra("album",songAmplifySimilar.getAlbum());
            intent.putExtra("artist",songAmplifySimilar.getArtist());
            intent.putExtra("id",songAmplifySimilar.getId());
            intent.putExtra("url",songAmplifySimilar.getUrl_image());
            //IMAGE -> intent.putExtra("info_title",data.get(position).getInfo());
            v.getContext().startActivity(intent);
        });

        //Similar song to -> song detail2 (simple)
        ImageButton card_view2 = rootView.findViewById(R.id.similar_amp2); // creating a CardView and assigning a value.


        card_view2.setOnClickListener((View v) -> {
            Intent intent = new Intent(v.getContext(),DetailSongActivity.class);
            intent.putExtra("song",songAmplifySimilar2.getName());
            intent.putExtra("album",songAmplifySimilar2.getAlbum());
            intent.putExtra("artist",songAmplifySimilar2.getArtist());
            intent.putExtra("id",songAmplifySimilar2.getId());
            intent.putExtra("url",songAmplifySimilar2.getUrl_image());
            v.getContext().startActivity(intent);
        });



        //Vots
















        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public JsonObjectRequest volleyPostRequest(View rootview, String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response Post", response.toString());
                establishmentEntity = new EstablishmentEntity(response);
                try {
                    JSONArray playList = response.getJSONArray("playlists");
                    for (int i=0;i<playList.length();i++){
                        if(playList.getJSONObject(i).getString("current").equals("true")){
                            songAmplify = new SongEntity((playList.getJSONObject(i).getJSONObject("song")));
                            setAmplify(rootview);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPONSEError", error.toString());
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

    public JsonObjectRequest volleyRequest(View rootView, String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response Post", response.toString());
                        establishmentEntity = new EstablishmentEntity(response);
                        try {
                            JSONArray playList = response.getJSONArray("playlists");
                            for (int i=0;i<playList.length();i++){
                                if(playList.getJSONObject(i).getString("current").equals("true")){
                                    songAmplify = new SongEntity((playList.getJSONObject(i).getJSONObject("song")));
                                    //Toast.makeText(getContext(),"UPDATING",Toast.LENGTH_SHORT).show();
                                    setAmplify(rootView);

                                    url_similars = url_major+"songs/"+songAmplify.getId()+"/similar";
                                    similars = volleyRequest_rvSongs(rootView,url_similars);
                                    queue2.add(similars);
                                    first = true;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    public JsonArrayRequest volleyRequest_rvSongs(View rootView ,String url){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SIMILAR", response.toString());
                        try{
                            dataSong = new ArrayList<>();
                            for(int i = 0;i<response.length();i++){
                                JSONObject result = response.getJSONObject(i);
                                dataSong.add(new SongEntity(result));
                            }
                            setSimilarSongs(dataSong,rootView);
                        }catch (JSONException arg){
                            Log.d("RIPARNAU", response.toString());
                            arg.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(rootView.getContext(),error.toString(),Toast.LENGTH_LONG).show();
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

    public JsonObjectRequest volleyRequest_rvUser(String url, View rootView) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());

                        //response.getString("image");
                        //updateInfoAmplifyJSON(rootView,response);
                        userEntity = new UserEntity(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(rootView.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
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



    class MyRunnable implements Runnable{
        String url;
        View rootView;
        RequestQueue queue;
        MyRunnable(String url, View rootView, RequestQueue queue){
            this.url = url;
            this.rootView = rootView;
            this.queue = queue;
        }
        @Override
        public void run() {
            JsonObjectRequest firstRequest = volleyRequest(rootView,url);
            queue.add(firstRequest);
            handler.postDelayed(this::run,10000);
        }

    }


    public void updateInfoAmplify(View rootView, SongEntity songEntity){
        //TextView nameEstablishment = rootView.findViewById(R.id.name_local);
        //ImageView imageSong = rootView.findViewById(R.id.cardSong);
        //TextView nameSong = rootView.findViewById(R.id.name_song_amp);
        //TextView nameArtist = rootView.findViewById(R.id.artist_song_amp);
        //TextView nameAlbum = rootView.findViewById(R.id.album_song_amp);
        //TextView listOfGenres = rootView.findViewById(R.id.genres_song_amp);



        /*nameSong.setText(songEntity.getName());
        nameAlbum.setText(songEntity.getAlbum());
        nameArtist.setText(songEntity.getArtist());*/

    }


    public void updateInfoAmplifyJSON(View rootView, JSONObject response){
        //TextView nameEstablishment = rootView.findViewById(R.id.name_local);
        //ImageView imageSong = rootView.findViewById(R.id.cardSong);
        //TextView nameSong = rootView.findViewById(R.id.name_song_amp);
        ////TextView nameAlbum = rootView.findViewById(R.id.album_song_amp);
        //TextView listOfGenres = rootView.findViewById(R.id.genres_song_amp);


        try {
            songAmplify =
                    new SongEntity(
                            response.getInt("id")
                            ,response.getString("name")
                            ,response.getString("author")
                            ,response.getString("album")
                            ,response.getString("image")
                            ,response.getInt("votes"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*nameSong.setText(songAmplify.getName());
        nameAlbum.setText(songAmplify.getAlbum());
        nameArtist.setText(songAmplify.getArtist());*/

    }


    public void addToFavSong(View v, SongEntity song){

        SongDao songDao = new SongDao(getContext());
        songDao.insert(song);
        Intent intent = new Intent(v.getContext(),FavouriteSongsActivity.class);
        /*intent.putExtra("nameSong", song.getName());
        intent.putExtra("nameArtist", song.getArtist());
        intent.putExtra("nameAlbum", song.getAlbum());*/

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        queue.cancelAll(this);
        super.onDestroyView();
    }



    private void setFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void setAmplify(View rootView){
        establishmentName = rootView.findViewById(R.id.name_local);
        nameSongAmplify = rootView.findViewById(R.id.name_song_amp1);
        artistSongAmplify = rootView.findViewById(R.id.artist_song_amp1);
        albumSongAmplify = rootView.findViewById(R.id.album_song_amp1);
        imgAmplify = rootView.findViewById(R.id.cardSong);

        establishmentName.setText(establishmentEntity.getName());
        nameSongAmplify.setText(songAmplify.getName());
        artistSongAmplify.setText(songAmplify.getArtist());
        albumSongAmplify.setText(songAmplify.getAlbum());
        Log.d("Rip",songAmplify.toString());
        //Log.d("rIP", songAmplify.getUrl_image());
        Picasso.get()
                .load(songAmplify.getUrl_image())
                .centerCrop()
                .fit()
                .into(imgAmplify);

        if(userEntity != null){
            setButton();
        }

    }

    public void setSimilarSongs(ArrayList<SongEntity> song, View rootView){
        for (SongEntity s: song
                ) {
            if(Similar == 0){
                Song1 = rootView.findViewById(R.id.similar_amp);
                Picasso.get()
                        .load(s.getUrl_image())
                        .centerCrop()
                        .fit()
                        .into(Song1);
                Similar += 1;
                songAmplifySimilar = s;
            }else if(Similar == 1){
                Song2 = rootView.findViewById(R.id.similar_amp2);
                Picasso.get()
                        .load(s.getUrl_image())
                        .centerCrop()
                        .fit()
                        .into(Song2);
                Similar += 1;
                songAmplifySimilar2 = s;
            }
        }
    }

    public void setButton(){
        for (SongEntity s: userEntity.getFavSongs()
                ) {
            if(s.getName().equals(songAmplify.getName())){
                addToFav.setVisibility(View.GONE);
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
