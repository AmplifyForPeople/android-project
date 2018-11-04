package amplify.us.amplify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        setButtonToStartMainActivity();

    }

    private void setButtonToStartMainActivity() {
        Button btn = (Button) findViewById(R.id.description_button);

        btn.setOnClickListener((View v) -> {
                startActivity(new Intent(DescriptionActivity.this, LoginActivity.class));
                finish();
            });
    }

}
