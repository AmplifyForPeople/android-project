package amplify.us.amplify.bottom_menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import amplify.us.amplify.R;

public class MainActivity extends AppCompatActivity {

    private DiscoverFragment discoverFragment;
    private AmplifySiteFragment amplifySiteFragment;
    private ProfileFragment profileFragment;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        discoverFragment = new DiscoverFragment();
        amplifySiteFragment = new AmplifySiteFragment();
        profileFragment = new ProfileFragment();

        //Default MainFragment :: DiscoverFragment
        setFragment(discoverFragment);
    }

    // To refactor
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent in;
            switch (item.getItemId()) {
                case R.id.navigation_discover:
                    setFragment(discoverFragment);
                    return true;
                case R.id.navigation_amplifySite:
                    setFragment(amplifySiteFragment);
                    return true;
                case R.id.navigation_profile:
                    setFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    private void setFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
