package amplify.us.amplify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class SetGenresActivity extends AppCompatActivity {

    private int GRAVITY_XOFFSET = 0;
    private int GRAVITY_YOFFSET = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_genres);
        this.setCheckBoxes(R.id.set_genres_genres, R.array.set_genres_genres);
        this.setButtonNext();
    }

    private void setButtonNext(){
        Button mEmailSignInButton = (Button) findViewById(R.id.set_genres_next_button);
        mEmailSignInButton.setOnClickListener((View view) -> {
            Intent intent = new Intent(SetGenresActivity.this, MainActivity.class);
            startActivity(intent);
            Toast toast = Toast.makeText(getBaseContext(),"Wellcome!", Toast.LENGTH_SHORT); //Todo remove
            toast.setGravity(Gravity.CENTER, GRAVITY_XOFFSET, GRAVITY_YOFFSET);
            toast.show();
            finish();
        });
    }

    private void setCheckBoxes(int element_id, int genres_id){
        LinearLayout rgp = (LinearLayout) findViewById(element_id);
        String[] genres = getResources().getStringArray(genres_id);
        for (int i = 0; i < genres.length ; i++) {
            Switch cb = new Switch(this);
            cb.setId(i + 1000);
            cb.setText(genres[i]);
            cb.setTextSize(25);
            cb.setPadding(0,0,0,50);
            rgp.addView(cb);
        }
    }

}
