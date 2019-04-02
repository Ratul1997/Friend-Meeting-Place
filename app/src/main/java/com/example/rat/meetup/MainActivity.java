package com.example.rat.meetup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", 0);

        if(sharedPreferences.getBoolean("isLogIn",false)){

            Intent intent = new Intent(this,Profile_page.class);
            String name = sharedPreferences.getString("UserName","").toString();
            String id = sharedPreferences.getString("UserId","").toString();
            String emial = sharedPreferences.getString("UserEmail","").toString();

            intent.putExtra("name",name);
            intent.putExtra("email",emial);
            intent.putExtra("userId",id);

            startActivity(intent);
            finish();
        }else{

            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
