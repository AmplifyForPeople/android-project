package amplify.us.amplify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

public class WelcomeActivity extends AppCompatActivity {

    private final int TRANSITION_TIME = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(TRANSITION_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    showNextScreen();
                }
            }

        };
        timer.start();

    }

    private void showNextScreen() {

        // Class activity = MainActivity.class;
        Class activity = DescriptionActivity.class;

        startActivity(new Intent(WelcomeActivity.this, activity));
        finish();
    }
}
