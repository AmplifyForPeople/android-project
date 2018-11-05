package amplify.us.amplify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        this.save();
        this.cancel();
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
