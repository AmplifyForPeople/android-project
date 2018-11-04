package amplify.us.amplify;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //Profile -> My Fav list Songs
        FrameLayout favLayout = (FrameLayout) rootView.findViewById(R.id.frameLayout_fav);
        favLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),FavouriteSongListActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        //Profile -> Edit Profile

        TextView editProf = (TextView) rootView.findViewById((R.id.editProf));

        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditProfileActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        //Profile -> Edit Password

        TextView editPass = (TextView) rootView.findViewById((R.id.changePssw));

        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChangePasswordActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        return rootView;

    }


}
