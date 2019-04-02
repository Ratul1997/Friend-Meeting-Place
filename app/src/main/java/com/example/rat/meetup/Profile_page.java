package com.example.rat.meetup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TextView;

public class Profile_page extends AppCompatActivity implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    TabLayout tablayout;
    ViewPager viewPager;
    String CurrentUserId,CurrentUserMail,CurrentUserName;
    TextView name;
    ImageButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);



        init();

        tablayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentChat(CurrentUserId,Profile_page.this),"Chat");
        adapter.AddFragment(new FregmentFriends(CurrentUserMail,CurrentUserId),"Friends");
        adapter.AddFragment(new Groups(CurrentUserId),"Groups");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }


    private void init() {
        name = (TextView)findViewById(R.id.name);
        menu = (ImageButton)findViewById(R.id.menu);
        menu.setOnClickListener(this);

        CurrentUserId = getIntent().getStringExtra("userId");
        CurrentUserMail = getIntent().getStringExtra("email");
        CurrentUserName = getIntent().getStringExtra("name");

        name.setText(CurrentUserName);

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("UserName",CurrentUserName);
        editor.putBoolean("isLogIn",true);
        editor.putString("UserId",CurrentUserId);
        editor.putString("UserEmail",CurrentUserMail);

        editor.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.menu){
            PopupMenu popupMenu = new PopupMenu(this,v);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                Intent intent = new Intent(this,Login.class);
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("UserName","");
                editor.putBoolean("isLogIn",false);
                editor.putString("UserId","");
                editor.putString("UserEmail","");

                editor.commit();

                startActivity(intent);
                finish();
                return true;
            default:
                return false;
        }
    }
}
