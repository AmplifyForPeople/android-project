package amplify.us.amplify.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import amplify.us.amplify.R;

public class DetailSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_song);

        ImageView img = findViewById(R.id.back_detail_song);
        img.setOnClickListener((View v) -> finish());
    }
}
