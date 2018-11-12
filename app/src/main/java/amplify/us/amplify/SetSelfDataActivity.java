package amplify.us.amplify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SetSelfDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_self_data);
        this.set_spinner_data(R.id.set_self_data_sex, R.array.set_self_data_sexs);
        this.set_spinner_data(R.id.set_self_data_location, R.array.set_self_data_locations);
        this.set_button_next();
    }

    private void set_button_next(){
        Button mEmailSignInButton = (Button) findViewById(R.id.set_self_data_next_button);
        mEmailSignInButton.setOnClickListener((View view) -> {
                Intent intent = new Intent(SetSelfDataActivity.this, SetGenresActivity.class);
                startActivity(intent);
                finish();
        });
    }
    private void set_spinner_data(int element_id, int array_id){
        Spinner spinner = (Spinner) findViewById(element_id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array_id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
