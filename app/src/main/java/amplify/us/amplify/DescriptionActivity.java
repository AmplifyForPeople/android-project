package amplify.us.amplify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import amplify.us.amplify.bottom_menu.MainActivity;

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
                startActivity(new Intent(DescriptionActivity.this, MainActivity.class));
                finish();
            });
    }

}
