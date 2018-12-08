package amplify.us.amplify.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import amplify.us.amplify.R;

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
            setData(title,info);
        }
    }
    private void setData(String title, String info){
        TextView title_establishment = findViewById(R.id.name_establishment);
        title_establishment.setText(title);
        TextView info_establishment = findViewById(R.id.description_establishment);
        info_establishment.setText(info);
    }
}
