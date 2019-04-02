package com.example.rat.meetup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<GroupList>grp = new ArrayList<GroupList>();
    Context context;
    String CurrentUserId;

    public GroupAdapter(List<GroupList>grp, Context context,String CurrentUserId) {
        this.grp = grp;
        this.context = context;
        this.CurrentUserId = CurrentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.render_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final GroupAdapter.ViewHolder vHolder = (GroupAdapter.ViewHolder)holder;
        vHolder.test.setText(grp.get(position).getGropname().toString());
        vHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GroupMassageAreaActivity.class);
                intent.putExtra("Name",grp.get(position).getGropname().toString());
                intent.putExtra("Id",grp.get(position).getGropid().toString());
                intent.putExtra("CurrentUserId",CurrentUserId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        System.out.println(grp.size());
        return grp.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        TextView test;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }
        public  void  init(){
            test = (TextView)itemView.findViewById(R.id.test);
            parentLayout = (LinearLayout)itemView.findViewById(R.id.parentLayout);
        }

    }
}
