package amplify.us.amplify.bottom_menu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import amplify.us.amplify.R;
import amplify.us.amplify.details.DetailSongActivity;
import amplify.us.amplify.entities.SongEntity;
import amplify.us.amplify.profile.FavouriteSongsActivity;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AmplifySiteFragment extends Fragment {


    public AmplifySiteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize the Data
        SongEntity songAmplify = new SongEntity("SongAmplify","amplify","amplify Album");
        //TODO: Afegir la canço a la Representacio de la layout

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_amplify_site, container, false);


        // Pulsator
        PulsatorLayout pulsator = (PulsatorLayout) rootView.findViewById(R.id.pulsator);
        pulsator.start();

        // Add to fav song
        Button addToFav = (Button) rootView.findViewById(R.id.addToFav);
        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavSong(v,songAmplify);
            }
        });


        //Similar song to -> song detail (simple)
        CardView card_view = rootView.findViewById(R.id.similar_amp); // creating a CardView and assigning a value.

        card_view.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),DetailSongActivity.class);
                v.getContext().startActivity(intent);
        });

        return rootView;
    }

    public void addToFavSong(View v, SongEntity song){
        Intent intent = new Intent(v.getContext(),FavouriteSongsActivity.class);
        intent.putExtra("nameSong", song.getName());
        intent.putExtra("nameArtist", song.getArtist());
        intent.putExtra("nameAlbum", song.getAlbum());
        startActivity(intent);
    }

}
