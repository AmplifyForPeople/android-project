package amplify.us.amplify.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import amplify.us.amplify.R;
import amplify.us.amplify.entities.GenreEntity;

public class DetailEstablishmentActivity extends AppCompatActivity {

    private  static final String TAG = "DetailEstablishment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_establishment);
        Log.d(TAG,"On create: started");

        ImageView img = (ImageView) findViewById(R.id.back_detail_establishment);
        img.setOnClickListener((View v) ->finish());

        getIncomingIntent();

    }

    private  void getIncomingIntent(){
        Log.d(TAG,"getIncoming");
        if(getIntent().hasExtra("establishment_title") && getIntent().hasExtra(("info_title"))){
            String title = getIntent().getStringExtra("establishment_title");
            String info = getIntent().getStringExtra("info_title");
            String image = getIntent().getStringExtra("image");
            ArrayList<GenreEntity> genres = (ArrayList<GenreEntity>)getIntent().getSerializableExtra("genres");
            setData(title,info,image,genres);

            Toast.makeText(getApplicationContext(),genres.toString(),Toast.LENGTH_SHORT).show();
            Log.d("RIPARNAU", genres.toString());
        }
    }
    private void setData(String title, String info,String image,List<GenreEntity> genres){
        TextView title_establishment = findViewById(R.id.name_establishment);
        title_establishment.setText(title);
        TextView info_establishment = findViewById(R.id.description_establishment);
        info_establishment.setText(info);
        ImageView imageEstablishment = findViewById(R.id.img_establishment);
        Picasso.get()
                .load(image)
                .centerCrop()
                .fit()
                .into(imageEstablishment);
        TextView genre_string = findViewById(R.id.genre1);
        String result = " ";
        if(genres!=null && genres.size()>0){
            for(GenreEntity g : genres) {
                result += (g.name + " ");
            }
        }
        genre_string.setText(result);
    }
}
