package amplify.us.amplify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        this.setSpinnerData(R.id.set_self_data_sex, R.array.set_self_data_sexs);
        this.setSpinnerData(R.id.set_self_data_location, R.array.set_self_data_locations);
        this.save();
        this.cancel();
    }

    private void setSpinnerData(int element_id, int array_id){
        Spinner spinner = (Spinner) findViewById(element_id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array_id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void save(){
        Button safeBut = (Button) findViewById((R.id.save_but));
        safeBut.setOnClickListener((View v) -> finish());
    }

    private void cancel(){
        Button cancelBut = (Button) findViewById((R.id.cancel_but));
        cancelBut.setOnClickListener((View v) -> finish());
    }
}
