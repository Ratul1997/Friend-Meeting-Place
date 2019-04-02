package com.example.rat.meetup;

import android.content.Context;
import android.location.Address;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MeetUpPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Address>item;
    Context context;

    public MeetUpPlaceAdapter(List<Address>item, Context context) {
        this.item = item;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vHolder = (ViewHolder)holder;

        vHolder.address.setText(item.get(position).getAddressLine(position));
        vHolder.city.setText(item.get(position).getLocality());
        vHolder.state.setText(item.get(position).getAdminArea());
        vHolder.country.setText(item.get(position).getCountryCode());
        vHolder.known_name.setText(item.get(position).getFeatureName());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView address,city,state,country,known_name;
        LinearLayout view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = (TextView)itemView.findViewById(R.id.address);
            city = (TextView)itemView.findViewById(R.id.city);
            state = (TextView)itemView.findViewById(R.id.state);
            country = (TextView)itemView.findViewById(R.id.country);
            known_name = (TextView)itemView.findViewById(R.id.known_name);

            view = (LinearLayout)itemView.findViewById(R.id.view);
        }

    }
}
