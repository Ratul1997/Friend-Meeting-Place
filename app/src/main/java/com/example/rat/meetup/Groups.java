package com.example.rat.meetup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@SuppressLint("ValidFragment")
public class Groups extends Fragment {

    List<GroupList>grp = new ArrayList<GroupList>();
    RecyclerView recyclerView;

    GroupAdapter adapter;
    String CurrentUserId;

    public Groups(String CurrentUserId) {
        this.CurrentUserId =CurrentUserId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_fragment,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        adapter = new GroupAdapter(grp,getActivity(),CurrentUserId);
        final LinearLayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        init();

        return view;
    }

    private void init() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("groups/");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                grp.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    //String i =snapshot.getKey().toString().toString();
                    String ii = snapshot.getKey();

                    GroupList gr = new GroupList(ii,"FirstGroup");
                    grp.add(gr);
                    //System.out.println(gr);
                }
                System.out.println(grp);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getKey(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmmss");
        return  format.format(date);
    }
}
