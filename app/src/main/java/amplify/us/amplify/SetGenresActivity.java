package amplify.us.amplify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SetGenresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_genres);
        this.set_check_boxes(R.id.set_genres_genres, R.array.set_genres_genres);
        this.set_button_next();
    }

    private void set_button_next(){
        Button mEmailSignInButton = (Button) findViewById(R.id.set_genres_next_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetGenresActivity.this, MainActivity.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getBaseContext(),"Wellcome!", Toast.LENGTH_SHORT); //Todo remove
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            }
        });
    }

    private void set_check_boxes(int element_id, int array_id){
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

}
