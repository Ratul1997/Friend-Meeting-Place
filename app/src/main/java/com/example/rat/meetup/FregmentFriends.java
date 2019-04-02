package com.example.rat.meetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("ValidFragment")
public class FregmentFriends extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    Button addButton;
    List<User> friends = new ArrayList<User>();
    RenderListAdapter adapter;
    String CurrentUserId="";
    String CurrentUserMail ="";
    String Url = "http://ratul.ourcuet.com/PhpFiles/FriendList.php";
    FloatingActionButton search_friends;

    ProgressBar progressBar;
    public FregmentFriends(String CurrentUserMail, String CurrentUserId) {
        this.CurrentUserId = CurrentUserId;
        this.CurrentUserMail = CurrentUserMail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.freids_fregment,container,false);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        search_friends = (FloatingActionButton)view.findViewById(R.id.search_friends);
        search_friends.setOnClickListener(this);

        init();
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
            recyclerView.setVisibility(View.GONE);
            adapter = new RenderListAdapter(getActivity(),friends,CurrentUserId);
            final LinearLayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);


        return view;
    }
    public void init(){

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("FriendList");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = (JSONObject)jsonArray.getJSONObject(i);

                        String usr1 = object.getString("User_id");
                        User usr = new User(object.getString("User_id"),object.getString("Fname"),object.getString("Lname"));
                        friends.add(usr);
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                System.out.println(CurrentUserMail);
                Map<String,String> map = new HashMap<String, String>();
                map.put("Email1",CurrentUserMail);
                
                return map;
            }
        };
        queue.add(request);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_friends){

             Intent intent = new Intent(getActivity(), SearchUserActivity.class);
             intent.putExtra("CurentUser",CurrentUserId);
            startActivity(intent);
        }
    }
}
