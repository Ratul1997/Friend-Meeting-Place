package com.example.rat.meetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
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
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentChat extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    RenderListAdapter adapter;
    String CurrentUserId="";
    List<User>item = new ArrayList<>();
    ProgressBar progressBar;
    FloatingActionButton search_friends;
    Context context;

    public FragmentChat(String CurrentUserId,Context context) {
        this.CurrentUserId = CurrentUserId;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fregment,container,false);


        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        init();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);

        adapter = new RenderListAdapter(getActivity(),item,CurrentUserId);
        final LinearLayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Chat/"+CurrentUserId);
        progressBar.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chatting cht = snapshot.getValue(Chatting.class);
                    User usr = new User(cht.getUsId(),cht.getFname(),cht.getLname());
                    item.add(usr);
                }
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
