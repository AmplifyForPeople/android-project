package amplify.us.amplify.bottom_menu;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amplify.us.amplify.R;
import amplify.us.amplify.adapters.EstablishmentAdapter;
import amplify.us.amplify.adapters.VotedSongsAdapter;
import amplify.us.amplify.details.DetailSongActivity;
import amplify.us.amplify.entities.EstablishmentEntity;
import amplify.us.amplify.entities.SongEntity;
import amplify.us.amplify.services.EstablishmentService;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    String url_major = "http://brain.3utilities.com/AmplifyWeb/rest";

    ArrayList<EstablishmentEntity> data;
    ArrayList<SongEntity> dataSong;
    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url_establishment = url_major+"/establishments";
        String url_song = url_major+"/music/songs/all";

        data = new ArrayList<>();
        dataSong = new ArrayList<>();

        JsonArrayRequest requestEstablishment = volleyRequest_rvEstablishment(rootView,url_establishment);
        JsonArrayRequest requestSongs = volleyRequest_rvSongs(rootView,url_song);
        queue.add(requestEstablishment);
        queue.add(requestSongs);




        //simulate Services info
        //RecyclerView establishments nearby
        recyclerViewSetupEstablishment(rootView);
        //RecyclerView most voted songs
        recyclerViewSetupVotedSongs(rootView);


        // Inflate the layout for this fragment
        return rootView;

    }


    public JsonArrayRequest volleyRequest_rvEstablishment(View rootView, String url){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("RESPONSE", response.toString());
                        try{
                            for(int i = 0;i<response.length();i++){
                                JSONObject result = response.getJSONObject(i);

                                data.add(new EstablishmentEntity(result));

                            }
                            recyclerViewSetupEstablishment(rootView);
                        }catch (JSONException arg){
                            Log.d("RIPARNAU", response.toString());

                            arg.printStackTrace();
                        }

                        //response.getString("image");

                        //updateInfoAmplifyJSON(rootView,response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
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

    public JsonArrayRequest volleyRequest_rvSongs(View rootView, String url){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("RESPONSE", response.toString());
                        try{
                            for(int i = 0;i<response.length();i++){
                                JSONObject result = response.getJSONObject(i);
                                int id = result.getInt("id");
                                String name = result.getString("name");
                                String album = result.getString("album");
                                String author = result.getString("author");
                                String url_image = result.getString("image");
                                dataSong.add(new SongEntity(id,name,author,album,url_image));
                            }
                            recyclerViewSetupVotedSongs(rootView);
                        }catch (JSONException arg){
                            Log.d("RIPARNAU", response.toString());

                            arg.printStackTrace();
                        }

                        //response.getString("image");

                        //updateInfoAmplifyJSON(rootView,response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE", error.toString());
            }
        });
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




    private void recyclerViewSetupEstablishment(View rootView) {
        //ESTABLISHMENT NEARBY
        RecyclerView rv_establishments_nearby = (RecyclerView) rootView.findViewById(R.id.rv_establishments_nearby);
        rv_establishments_nearby.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_establishments_nearby.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new EstablishmentAdapter(data);
        rv_establishments_nearby.setAdapter(mAdapter);
    }

    private void recyclerViewSetupVotedSongs(View rootView) {
        //ESTABLISHMENT NEARBY
        RecyclerView rv_most_voted_songs = (RecyclerView) rootView.findViewById(R.id.rv_most_voted_songs);
        rv_most_voted_songs.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_most_voted_songs.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new VotedSongsAdapter(dataSong);
        rv_most_voted_songs.setAdapter(mAdapter);
    }

    //Adding dataSet into RecyclerView Establishments Nearby
    /*private ArrayList<EstablishmentEntity> dataSetEstablishment(){
        EstablishmentService.getAllEstablishments();
        ArrayList<EstablishmentEntity> data = new ArrayList<>();
        data.add(new EstablishmentEntity(1,"Establishment 1","a", "mail@mail.com","Info Establishment 1"," ", new String[]{"genre1","genre2"}));
        data.add(new EstablishmentEntity(2,"Establishment 2","a", "mail@mail.com","Info Establishment 2"," ", new String[]{"genre1","genre2"}));
        data.add(new EstablishmentEntity(3,"Establishment 3","a", "mail@mail.com","Info Establishment 3"," ", new String[]{"genre1","genre2"}));
        data.add(new EstablishmentEntity(4,"Establishment 4","a", "mail@mail.com","Info Establishment 4"," ", new String[]{"genre1","genre2"}));
        return data;
    }*/

    //Adding dataSet into RecyclerView Establishments Nearby
    /*private ArrayList<SongEntity> dataSetSong(){
        //EstablishmentService.getMostVoted();
        ArrayList<SongEntity> data = new ArrayList<>();
        data.add(new SongEntity("song 1","artist1","album1"));
        data.add(new SongEntity("song 2","artist2","album2"));
        data.add(new SongEntity("song 3","artist3","album3"));
        data.add(new SongEntity("song 4","artist4","album4"));
        data.add(new SongEntity("song 5","artist5","album5"));
        return data;
    }*/

    private void displayImageFromUrl(String url){
        try {
            URI uri = new URI(url);
            URL link = uri.toURL();
            Bitmap bmp = BitmapFactory.decodeStream(link.openConnection().getInputStream());
            //imageView.setImageBitmap(bmp);

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

}
