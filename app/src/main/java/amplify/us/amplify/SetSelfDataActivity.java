package amplify.us.amplify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SetSelfDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_self_data);
        this.set_spinner_data(R.id.set_self_data_sex, R.array.set_self_data_sexs);
        this.set_spinner_data(R.id.set_self_data_location, R.array.set_self_data_locations);

    }

    private void set_spinner_data(int element_id, int array_id){
        Spinner spinner = (Spinner) findViewById(element_id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array_id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
