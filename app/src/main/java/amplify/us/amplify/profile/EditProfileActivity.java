package amplify.us.amplify.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import amplify.us.amplify.R;
import amplify.us.amplify.entities.UserEntity;

public class EditProfileActivity extends AppCompatActivity {

    String url_major = "http://brain.3utilities.com/AmplifyWeb/rest";
    UserEntity user;
    EditText nameEdit;
    EditText ageEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEdit = findViewById(R.id.set_self_data_name);
        ageEdit = findViewById(R.id.set_self_data_age);

        this.setSpinnerData(R.id.set_self_data_sex, R.array.set_self_data_sexs);
        this.setSpinnerData(R.id.set_self_data_location, R.array.set_self_data_locations);
        this.save();
        this.cancel();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url_user = url_major+"/users/1";
        JsonObjectRequest requestUser = volleyRequest_rvUser(url_user);
        queue.add(requestUser);
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

    public JsonObjectRequest volleyRequest_rvUser(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());

                        //response.getString("image");
                        //updateInfoAmplifyJSON(rootView,response);
                        user = new UserEntity(response);
                        setUser();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE", error.toString());
            }
        })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        return jsonObjectRequest;
    }

    public void setUser(){
        ageEdit.setText(user.getAge());
        nameEdit.setText(user.getName());
        Toast.makeText(getApplicationContext(), user.getAge(), Toast.LENGTH_SHORT).show();
        ageEdit.setHint(user.getAge());
        nameEdit.setHint(user.getName());
        Toast.makeText(getApplicationContext(),user.getName(), Toast.LENGTH_SHORT).show();
    }
}
