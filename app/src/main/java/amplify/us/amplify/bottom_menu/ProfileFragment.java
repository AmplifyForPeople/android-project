package amplify.us.amplify.bottom_menu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import amplify.us.amplify.LoginActivity;
import amplify.us.amplify.R;
import amplify.us.amplify.profile.ChangePasswordActivity;
import amplify.us.amplify.profile.EditProfileActivity;
import amplify.us.amplify.profile.FavouriteSongsActivity;
import amplify.us.amplify.profile.ModifyGenresActivitiy;


/**
 * {@link Fragment} subclass for profile in the dashboard.
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
        setupListeners(rootView);
        return rootView;

    }

    private void setupListeners(View rootView) {
        //Profile -> My Fav list Songs
        setListenerFavList(rootView);

        //Profile -> Edit Profile
        setListenerEditProfile(rootView);

        //Profile -> Edit Password
        setListenerEditPassword(rootView);

        //Profile -> Modify Genres
        setListenerModifyGenres(rootView);

        //Profile -> Log Out
        setListenerLogOut(rootView);
    }

    private void setListenerLogOut(View rootView) {
        Button logUot = (Button) rootView.findViewById((R.id.button_logout));

        logUot.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),LoginActivity.class);
                v.getContext().startActivity(intent);
                getActivity().finish();
        });
    }

    private void setListenerModifyGenres(View rootView) {
        TextView modGen = (TextView) rootView.findViewById((R.id.modifyGen));

        modGen.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),ModifyGenresActivitiy.class);
                v.getContext().startActivity(intent);
        });
    }

    private void setListenerEditPassword(View rootView) {
        TextView editPass = (TextView) rootView.findViewById((R.id.changePssw));

        editPass.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),ChangePasswordActivity.class);
                v.getContext().startActivity(intent);
        });
    }

    private void setListenerEditProfile(View rootView) {
        TextView editProf = (TextView) rootView.findViewById((R.id.editProf));

        editProf.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),EditProfileActivity.class);
                v.getContext().startActivity(intent);
        });
    }

    private void setListenerFavList(View rootView) {
        FrameLayout favLayout = (FrameLayout) rootView.findViewById(R.id.frameLayout_fav);
        favLayout.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),FavouriteSongsActivity.class);
                v.getContext().startActivity(intent);
        });
    }


}
