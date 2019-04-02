package com.example.rat.meetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText password,login;
    Button loginButton,signUpButton;
    ProgressBar progressBar;
    String Url = "http://ratul.ourcuet.com/PhpFiles/loginandsignup.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        password = (EditText)findViewById(R.id.password);
        login = (EditText)findViewById(R.id.login);

        signUpButton = (Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.loginButton){
            System.out.println("asas");
            if(password.getText().toString().equals("") || login.getText().toString().equals("")){
                Toast.makeText(this, "Password or Email Can not be Empty", Toast.LENGTH_SHORT).show();
            }else{
               progressBar.setIndeterminate(true);
               checkData();
            }
        }
        if(v.getId() == R.id.signUpButton){
            signUp();
        }
    }

    private void signUp(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);

    }

    private void checkData() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                System.out.println(response);
                try {
                    JSONObject object = new JSONObject(response);
                    String userid = object.getString("user_id").toString();
                    if(userid.equals("-1")){
                        Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else{
                        String fname = object.getString("fname").toString();
                        String lname = object.getString("lname").toString();
                        String email = object.getString("email").toString();

                        Intent intent = new Intent(Login.this,Profile_page.class);
                        intent.putExtra("name",fname+" "+lname);
                        intent.putExtra("email",email);
                        intent.putExtra("userId",userid);

                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("email",login.getText().toString());
                map.put("passowrd",password.getText().toString());
                return map;
            }
        };
        queue.add(request);
   }
}
