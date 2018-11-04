package amplify.us.amplify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.set_button_next();
    }

    private void set_button_next(){
        Button mEmailSignInButton = (Button) findViewById(R.id.register_next_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, SetSelfDataActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
