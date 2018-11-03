package amplify.us.amplify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amplify.us.amplify.adapters.EstablishmentAdapter;
import amplify.us.amplify.entities.EstablishmentEntity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    private RecyclerView rv_establishments_nearby;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        //simulate Services info
        rv_establishments_nearby = (RecyclerView) rootView.findViewById(R.id.rv_establishments_nearby);
        rv_establishments_nearby.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rv_establishments_nearby.setLayoutManager(mLayoutManager);
        mAdapter = new EstablishmentAdapter(dataSet());
        rv_establishments_nearby.setAdapter(mAdapter);


        // Inflate the layout for this fragment
        return rootView;
    }

    //Adding dataSet
    private ArrayList<EstablishmentEntity> dataSet(){
        ArrayList<EstablishmentEntity> data = new ArrayList<>();
        data.add(new EstablishmentEntity("Establishment 1","Info Establishment 1"));
        data.add(new EstablishmentEntity("Establishment 2","Info Establishment 2"));
        data.add(new EstablishmentEntity("Establishment 3","Info Establishment 3"));
        data.add(new EstablishmentEntity("Establishment 4","Info Establishment 4"));
        data.add(new EstablishmentEntity("Establishment 5","Info Establishment 5"));
        return data;
    }

}
