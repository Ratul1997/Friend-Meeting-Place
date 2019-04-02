package com.example.rat.meetup;

import android.content.Context;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RenderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<User> items = new ArrayList<User>();
    Context context;
    String CurrentUserId;
    public RenderListAdapter(Context context,List<User> items,String CurrentUserId) {
        this.context = context;
        this.items = items;
        this.CurrentUserId=CurrentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.render_items, parent, false);


        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vHolder = (ViewHolder)holder;
        vHolder.test.setText(items.get(position).getFName()+" "+items.get(position).getLName());

        vHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);
                Intent intent = new Intent(context,MesseginArea.class);
                intent.putExtra("FName",items.get(position).getFName());
                intent.putExtra("LName",items.get(position).getLName());
                intent.putExtra("Id",items.get(position).getUsr_Id());
                intent.putExtra("CurrentUserId",CurrentUserId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView test;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            init();
        }
        public void init(){
            test = (TextView)itemView.findViewById(R.id.test);
            parentLayout = (LinearLayout)itemView.findViewById(R.id.parentLayout);
        }

    }

}
