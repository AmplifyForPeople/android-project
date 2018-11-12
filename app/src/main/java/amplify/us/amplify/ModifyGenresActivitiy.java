package amplify.us.amplify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

public class ModifyGenresActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_genres);

        setCheckBoxes(R.id.set_genres_genres, R.array.set_genres_genres);
        setupListeners();
    }

    private void setCheckBoxes(int element_id, int array_id){
        LinearLayout rgp = (LinearLayout) findViewById(element_id);
        String[] genres = getResources().getStringArray(array_id);
        for (int i = 0; i < genres.length ; i++) {
            Switch cb = new Switch(this);
            cb.setId(i + 1000);
            cb.setText(genres[i]);
            cb.setTextSize(25);
            cb.setPadding(0,0,0,50);
            rgp.addView(cb);
        }
    }

    private void setupListeners() {
        setListenerSave();
        setListenerCancel();
    }


    private void setListenerSave(){
        Button safeBut = (Button) findViewById((R.id.save_but));
        safeBut.setOnClickListener((View v) -> finish());
    }

    private void setListenerCancel(){
        Button cancelBut = (Button) findViewById((R.id.cancel_but));
        cancelBut.setOnClickListener((View v) -> finish());
    }
}
