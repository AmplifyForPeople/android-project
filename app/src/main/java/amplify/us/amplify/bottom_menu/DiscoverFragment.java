package amplify.us.amplify.bottom_menu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        //simulate Services info
        //RecyclerView establishments nearby
        recyclerViewSetupEstablishment(rootView);
        //RecyclerView most voted songs
        recyclerViewSetupVotedSongs(rootView);


        // Inflate the layout for this fragment
        return rootView;

    }


    private void recyclerViewSetupEstablishment(View rootView) {
        //ESTABLISHMENT NEARBY
        RecyclerView rv_establishments_nearby = (RecyclerView) rootView.findViewById(R.id.rv_establishments_nearby);
        rv_establishments_nearby.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_establishments_nearby.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new EstablishmentAdapter(dataSetEstablishment());
        rv_establishments_nearby.setAdapter(mAdapter);
    }

    private void recyclerViewSetupVotedSongs(View rootView) {
        //ESTABLISHMENT NEARBY
        RecyclerView rv_most_voted_songs = (RecyclerView) rootView.findViewById(R.id.rv_most_voted_songs);
        rv_most_voted_songs.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_most_voted_songs.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new VotedSongsAdapter(dataSetSong());
        rv_most_voted_songs.setAdapter(mAdapter);
    }

    //Adding dataSet into RecyclerView Establishments Nearby
    private ArrayList<EstablishmentEntity> dataSetEstablishment(){
        EstablishmentService.getAllEstablishments();
        ArrayList<EstablishmentEntity> data = new ArrayList<>();
        data.add(new EstablishmentEntity("Establishment 1","Info Establishment 1"));
        data.add(new EstablishmentEntity("Establishment 2","Info Establishment 2"));
        data.add(new EstablishmentEntity("Establishment 3","Info Establishment 3"));
        data.add(new EstablishmentEntity("Establishment 4","Info Establishment 4"));
        data.add(new EstablishmentEntity("Establishment 5","Info Establishment 5"));
        return data;
    }

    //Adding dataSet into RecyclerView Establishments Nearby
    private ArrayList<SongEntity> dataSetSong(){
        //EstablishmentService.getMostVoted();
        ArrayList<SongEntity> data = new ArrayList<>();
        data.add(new SongEntity("song 1","artist1","album1"));
        data.add(new SongEntity("song 2","artist2","album2"));
        data.add(new SongEntity("song 3","artist3","album3"));
        data.add(new SongEntity("song 4","artist4","album4"));
        data.add(new SongEntity("song 5","artist5","album5"));
        return data;
    }

}
