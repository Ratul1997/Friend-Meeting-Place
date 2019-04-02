package com.example.rat.meetup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendRenderList extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    List<User> items = new ArrayList<User>();
    Context context;
    String CurrentUserId;

    public SearchFriendRenderList(List<User> items, Context context, String currentUserId) {
        this.items = items;
        this.context = context;
        this.CurrentUserId = currentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_renderlist, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vHolder = (ViewHolder)holder;

        vHolder.name.setText(items.get(position).getFName()+" "+items.get(position).getLName());
        vHolder.frame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name ;
        RelativeLayout frame;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            frame  = (RelativeLayout)itemView.findViewById(R.id.frame);
        }
    }
}
