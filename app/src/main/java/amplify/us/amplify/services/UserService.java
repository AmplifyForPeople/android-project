package amplify.us.amplify.services;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserService {

    private static final String BASE_URL = "http://localhost:8080/AmplifyWeb/rest/";

    public void getAllUsers(){

    }

    public void getUser(String id){

    }

    public static void main(String[] args) {
        UserService s= new UserService();
        s.getAllUsers();
    }

}
