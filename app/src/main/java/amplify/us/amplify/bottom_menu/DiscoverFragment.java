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
import amplify.us.amplify.details.DetailSongActivity;
import amplify.us.amplify.entities.EstablishmentEntity;
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
        recyclerViewSetup(rootView);

        //Most voted song to -> song detail
        setupListenerMostVotedSong(rootView);

        // Inflate the layout for this fragment
        return rootView;

    }

    private void setupListenerMostVotedSong(View rootView) {
        CardView card_view = (CardView) rootView.findViewById(R.id.cardView); // creating a CardView and assigning a value.
        card_view.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),DetailSongActivity.class);
                v.getContext().startActivity(intent);
        });
    }

    private void recyclerViewSetup(View rootView) {
        RecyclerView rv_establishments_nearby = (RecyclerView) rootView.findViewById(R.id.rv_establishments_nearby);
        rv_establishments_nearby.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_establishments_nearby.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new EstablishmentAdapter(dataSet());
        rv_establishments_nearby.setAdapter(mAdapter);
    }

    //Adding dataSet into RecyclerView Establishments Nearby
    private ArrayList<EstablishmentEntity> dataSet(){
        EstablishmentService.getAllEstablishments();
        ArrayList<EstablishmentEntity> data = new ArrayList<>();
        data.add(new EstablishmentEntity("Establishment 1","Info Establishment 1"));
        data.add(new EstablishmentEntity("Establishment 2","Info Establishment 2"));
        data.add(new EstablishmentEntity("Establishment 3","Info Establishment 3"));
        data.add(new EstablishmentEntity("Establishment 4","Info Establishment 4"));
        data.add(new EstablishmentEntity("Establishment 5","Info Establishment 5"));
        return data;
    }

}
