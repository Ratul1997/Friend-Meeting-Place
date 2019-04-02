package com.example.rat.meetup;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchUserActivity extends AppCompatActivity {

    String Url = "http://ratul.ourcuet.com/PhpFiles/Users.php";
    LinearLayout frame;
    String CurrentUserId;
    RecyclerView recyclerView;
    SearchFriendRenderList renderListAdapter;
    List<User>mchat =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_search_user);
        dimingbackground();

        init();
        requset();
    }

    private void init() {
        CurrentUserId = getIntent().getStringExtra("CurentUser");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //recyclerView.setVisibility(View.GONE);
        renderListAdapter = new SearchFriendRenderList(mchat,this,CurrentUserId);
        final LinearLayoutManager mLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(renderListAdapter);

    }

    private void requset() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =  new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Users");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = (JSONObject)jsonArray.getJSONObject(i);
                        User usr = new User(object.getString("user_id"),object.getString("fname"),object.getString("lname"));
                        mchat.add(usr);
                    }
                renderListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                System.out.println(CurrentUserId);
                Map<String,String> map = new HashMap<String, String>();
                map.put("User",CurrentUserId);

                return map;
            }
        };
        queue.add(request);

    }

    private void dimingbackground() {

        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.9), (int)(height*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.dimAmount = .7f;

        params.x = 0;
        params.y = -20;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(params);

    }
}
