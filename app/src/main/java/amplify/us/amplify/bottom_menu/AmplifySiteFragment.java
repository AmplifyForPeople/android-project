package amplify.us.amplify.bottom_menu;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import amplify.us.amplify.R;
import amplify.us.amplify.database.Dao.SongDao;
import amplify.us.amplify.details.DetailSongActivity;
import amplify.us.amplify.entities.SongEntity;
import amplify.us.amplify.profile.FavouriteSongsActivity;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AmplifySiteFragment extends Fragment {

    SongEntity songAmplify;
    private Handler handler;
    MyRunnable runnable;
    SongEntity songAmplifySimilar;
    SongEntity songAmplifySimilar2;
    String url_major = "http://172.16.110.175:8080";
    private TextView nameSong;
    private TextView nameArtist;
    private TextView nameAlbum;


    public AmplifySiteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_amplify_site, container, false);

        nameSong = rootView.findViewById(R.id.name_song_amp);
        nameArtist = rootView.findViewById(R.id.artist_song_amp);
        nameAlbum = rootView.findViewById(R.id.album_song_amp);



        // Pulse Animation (Amplify Active)
        PulsatorLayout pulse = rootView.findViewById(R.id.pulsator);
        pulse.start();

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = url_major+"/music/songs/4";
        String url_similar = url_major+"/music/songs/similar/4";
        String url_similar2 = url_major+"/music/songs/similar/4";


        //AMPLIFY SITE SONG
        JsonObjectRequest firstRequest = volleyRequest(rootView,url);
        queue.add(firstRequest);

        handler = new Handler();
        runnable = new MyRunnable(url,rootView,queue);
        handler.post(runnable);

        //AMPLIFY SIMILAR SONGS
        JsonObjectRequest similarRequest = volleyRequestSimilar(rootView,url_similar);
        JsonObjectRequest similarRequest2 = volleyRequestSimilar2(rootView,url_similar2);
        queue.add(similarRequest);
        queue.add(similarRequest2);



        //////// BUTTONS & ACCESS TO OTHER ACTIVITIES /////

        // Add to fav song
        Button addToFav = (Button) rootView.findViewById(R.id.addToFav);
        addToFav.setOnClickListener(v -> {
            addToFavSong(v,songAmplify);
            Toast.makeText(getContext(),songAmplify.getName(), Toast.LENGTH_SHORT).show();
            handler.removeCallbacks(runnable);
        });

        //Similar song to -> song detail (simple)
        CardView card_view = rootView.findViewById(R.id.similar_amp); // creating a CardView and assigning a value.

        card_view.setOnClickListener((View v) -> {
            Intent intent = new Intent(v.getContext(),DetailSongActivity.class);
            intent.putExtra("song",songAmplifySimilar.getName());
            intent.putExtra("album",songAmplifySimilar.getAlbum());
            intent.putExtra("artist",songAmplifySimilar.getArtist());
            //IMAGE -> intent.putExtra("info_title",data.get(position).getInfo());
            v.getContext().startActivity(intent);
        });

        //Similar song to -> song detail2 (simple)
        CardView card_view2 = rootView.findViewById(R.id.similar_amp2); // creating a CardView and assigning a value.

        card_view2.setOnClickListener((View v) -> {
            Intent intent = new Intent(v.getContext(),DetailSongActivity.class);
            intent.putExtra("song",songAmplifySimilar2.getName());
            intent.putExtra("album",songAmplifySimilar2.getAlbum());
            intent.putExtra("artist",songAmplifySimilar2.getArtist());
            v.getContext().startActivity(intent);
        });



        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public JsonObjectRequest volleyRequest(View rootView, String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());

                        //response.getString("image");
                        updateInfoAmplifyJSON(rootView,response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE", error.toString());
            }
        });
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


    public JsonObjectRequest volleyRequestSimilar(View rootView,String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try{
                            songAmplifySimilar =
                                    new SongEntity(response.getString("name")
                                            ,response.getString("author")
                                            ,response.getString("album"));
                            Log.d("RESPONSE", response.toString());
                            //response.getString("image");
                            //updateInfoAmplify(rootView,songAmplify);
                            updateInfoAmplifyJSON(rootView,response);


                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE", error.toString());
            }
        });
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

    public JsonObjectRequest volleyRequestSimilar2(View rootView,String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try{
                            songAmplifySimilar2 =
                                    new SongEntity(response.getString("name")
                                            ,response.getString("author")
                                            ,response.getString("album"));
                            //response.getString("image");
                            updateInfoAmplify(rootView,songAmplify);


                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE", error.toString());
            }
        });
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
            handler.postDelayed(this::run,30000);
        }

    }


    public void updateInfoAmplify(View rootView, SongEntity songEntity){
        //TextView nameEstablishment = rootView.findViewById(R.id.name_local);
        //ImageView imageSong = rootView.findViewById(R.id.cardSong);
        TextView nameSong = rootView.findViewById(R.id.name_song_amp);
        TextView nameArtist = rootView.findViewById(R.id.artist_song_amp);
        TextView nameAlbum = rootView.findViewById(R.id.album_song_amp);
        //TextView listOfGenres = rootView.findViewById(R.id.genres_song_amp);



        nameSong.setText(songEntity.getName());
        nameAlbum.setText(songEntity.getAlbum());
        nameArtist.setText(songEntity.getArtist());

    }


    public void updateInfoAmplifyJSON(View rootView, JSONObject response){
        //TextView nameEstablishment = rootView.findViewById(R.id.name_local);
        //ImageView imageSong = rootView.findViewById(R.id.cardSong);
        TextView nameSong = rootView.findViewById(R.id.name_song_amp);
        TextView nameArtist = rootView.findViewById(R.id.artist_song_amp);
        TextView nameAlbum = rootView.findViewById(R.id.album_song_amp);
        //TextView listOfGenres = rootView.findViewById(R.id.genres_song_amp);


        try {
            songAmplify =
                    new SongEntity(response.getString("name")
                            ,response.getString("author")
                            ,response.getString("album"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nameSong.setText(songAmplify.getName());
        nameAlbum.setText(songAmplify.getAlbum());
        nameArtist.setText(songAmplify.getArtist());

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

}
