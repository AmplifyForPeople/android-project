package amplify.us.amplify.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import amplify.us.amplify.R;

public class DetailSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_song);

        ImageView img = findViewById(R.id.back_detail_song);
        img.setOnClickListener((View v) -> finish());

        getIncomingIntent();
    }

    private  void getIncomingIntent(){
        if(getIntent().hasExtra("song") && getIntent().hasExtra(("artist")) && getIntent().hasExtra(("album"))){
            String title = getIntent().getStringExtra("song");
            String artist = getIntent().getStringExtra("artist");
            String album = getIntent().getStringExtra("album");
            setData(title,artist,album);
        }
    }
    private void setData(String title, String artist, String album){
        TextView title_album = findViewById(R.id.name_song);
        title_album.setText(title);
        TextView artist_name = findViewById(R.id.artist_name);
        artist_name.setText(artist);
        TextView album_name = findViewById(R.id.album_name);
        album_name.setText(album);
    }
}
