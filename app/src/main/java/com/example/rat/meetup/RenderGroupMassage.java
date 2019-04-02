package com.example.rat.meetup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RenderGroupMassage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Group> mChat ;
    Context context;
    final int MSG_TYPE_RIGHT = 1;
    final int MSG_TYPE_LEFT = 2;

    String CurrentId;

    FirebaseDatabase database ;


    public RenderGroupMassage(List<Group> mChat, Context context, String currentId) {
        this.mChat = mChat;
        this.context = context;
        this.CurrentId = currentId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType ==MSG_TYPE_RIGHT){
            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.chating_right, parent, false);
            return new ViewHolde(view);
        }else{
            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem_left, parent, false);
            return new ViewHolde(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final RenderGroupMassage.ViewHolde vHolder = (RenderGroupMassage.ViewHolde)holder;
        vHolder.show_msg.setText(mChat.get(position).getMassage());

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder{


        TextView show_msg;
        //ImageView profile;
        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            init();
        }
        public  void  init(){
            show_msg = (TextView)itemView.findViewById(R.id.show_msg);
            //   profile = (ImageView)itemView.findViewById(R.id.profile);
        }
    }
    @Override
    public int getItemViewType(int position) {
        database = FirebaseDatabase.getInstance();
        //System.out.println(mChat.get(position).getSender());
        if(mChat.get(position).getSender().equals(CurrentId)){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
