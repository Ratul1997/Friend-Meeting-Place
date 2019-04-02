package com.example.rat.meetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText fname,lname,login,paswrd,cPasswrd;
    Button loginButton,back;
    String Url = "http://ratul.ourcuet.com/PhpFiles/InsertUserData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        fname = (EditText)findViewById(R.id.fname);
        lname = (EditText)findViewById(R.id.lname);
        login = (EditText)findViewById(R.id.login);
        paswrd = (EditText)findViewById(R.id.paswrd);
        cPasswrd = (EditText)findViewById(R.id.cPasswrd);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton){
            createAccount();
        }
        if(v.getId() == R.id.back){
            finish();
        }
    }

    private void createAccount() {
        final String fName = fname.getText().toString();
        final String Lname = lname.getText().toString();
        final String Login = login.getText().toString();
        final String ps = paswrd.getText().toString();
        String cps = cPasswrd.getText().toString();

        if(fName.equals("") || Lname.equals("") || Login.equals("") || ps.equals("") || cps.equals("")){
            Toast.makeText(this, "Can not be empty any field ", Toast.LENGTH_SHORT).show();
        }
        else{
            if(cps.equals(ps)){
                RequestQueue queue = Volley.newRequestQueue(this);

                StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map = new HashMap<>();
                        map.put("Id",getId());
                        map.put("FName",fName);
                        map.put("LName",Lname);
                        map.put("Email",Login);
                        map.put("Passowrd",ps);
                        return map;
                    }
                };
                queue.add(request);
            }else{
                Toast.makeText(this, "Password Doesnot Match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getId(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmmss");
        return  format.format(date);
    }
}
