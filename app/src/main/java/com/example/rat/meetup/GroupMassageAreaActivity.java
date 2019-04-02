package com.example.rat.meetup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GroupMassageAreaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name;
    EditText massageText;
    ImageButton sendButton;
    String sender,receiver;
    List<Group> mchat;
    RecyclerView recyclerView;
    ImageButton placePick;
    RenderGroupMassage renderMassageList;
    String FirstName,LastName,groupId;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_massage_area);
        init();
        readMassage();
    }
    private void init() {
        name = (TextView)findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("Name"));


        sender = getIntent().getStringExtra("CurrentUserId");
        groupId = getIntent().getStringExtra("Id");

        System.out.println(sender);


        massageText = (EditText)findViewById(R.id.massageText);

        back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(this);



        sendButton = (ImageButton)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        placePick = (ImageButton)findViewById(R.id.placePick);
        placePick.setOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendButton){
            sendMessage();
        }if(v.getId() == R.id.placePick){
            gotoPlace();
        }
        if(v.getId() == R.id.back){
            finish();
        }
    }

    private void gotoPlace() {
        Intent intent = new Intent(this,MeetUpPlaceGroup.class);


        intent.putExtra("sender",sender);
        intent.putExtra("key",groupId);
        startActivity(intent);

    }

    private void sendMessage(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups/"+groupId+"/massage");

        String id = myRef.push().getKey();

        Group fr = new Group(sender,massageText.getText().toString());
        myRef.child(id).setValue(fr);
        massageText.setText("");
    }

    private void readMassage(){
        mchat = new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("groups/"+groupId+"/massage");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                      Group frp = snapshot.getValue(Group.class);

                      mchat.add(frp);

                      renderMassageList = new RenderGroupMassage(mchat,GroupMassageAreaActivity.this,sender);
                      recyclerView.scrollToPosition(mchat.size() - 1);

                      recyclerView.setAdapter(renderMassageList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
