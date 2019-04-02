package com.example.rat.meetup;

import android.content.Intent;
import android.media.Image;
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
import java.util.HashMap;
import java.util.List;

public class MesseginArea extends AppCompatActivity implements View.OnClickListener{

    TextView name;
    EditText massageText;
    ImageButton sendButton;
    String sender,receiver;
    List<SendingMassage>mchat;
    RecyclerView recyclerView;
    ImageButton placePick;
    RenderMassageList renderMassageList;
    String FirstName,LastName;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messegin_area);

        init();
        readMassage();
    }

    private void init() {
        FirstName =getIntent().getStringExtra("FName") ;
        LastName =getIntent().getStringExtra("LName") ;
        name = (TextView)findViewById(R.id.name);
        name.setText(FirstName+" "+LastName);


        sender = getIntent().getStringExtra("CurrentUserId");
        receiver = getIntent().getStringExtra("Id");

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
        }if(v.getId() == R.id.back){
            finish();
        }
    }

    private void gotoPlace() {
        Intent intent = new Intent(this,MeetUpPlace.class);

        BigInteger send,rec;
        send = new BigInteger(sender);
        rec = new BigInteger(receiver);


        int res = send.compareTo(rec);

        String key="";
        if(res == -1){
            key = sender+receiver;
        }
        if(res == 1){
            key = receiver+sender;
        }
        intent.putExtra("sender",sender);
        intent.putExtra("receiver",receiver);
        intent.putExtra("key",key);
        startActivity(intent);

    }

    private void sendMessage(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("details/"+ new CompareTwoString(sender,receiver).compare()+"/massages");

        System.out.println(new CompareTwoString(sender,receiver).compare());
        String id = myRef.push().getKey();
        SendingMassage msg = new SendingMassage(sender,receiver,massageText.getText().toString());

        DatabaseReference myRef2 = database.getReference("Chat/"+sender);

        myRef2.child(receiver).setValue(new Chatting(receiver,FirstName,LastName));

        myRef.child(id).setValue(msg);
        massageText.setText("");
    }

    private void readMassage(){
        mchat = new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("details/"+ new CompareTwoString(sender,receiver).compare()+"/massages");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    SendingMassage massage = snapshot.getValue(SendingMassage.class);

                    if((massage.getSender().equals(sender) && massage.getReceiver().equals(receiver)) ||
                            massage.getSender().equals(receiver) && massage.getReceiver().equals(sender)){
                        mchat.add(massage);
                    }

                    renderMassageList = new RenderMassageList(mchat,MesseginArea.this,sender);
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
