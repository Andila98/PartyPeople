package com.partypeople.user.adaptors.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.partypeople.user.R;
import com.partypeople.user.models.Explore;
import com.partypeople.user.models.Popular;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder>{

    private List<Explore> exploreList;

    public  class  MyViewHolder extends RecyclerView.ViewHolder{
       // public ImageView image;
        public TextView name, venue, date,ticketprice;

        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.event_name);
            venue =  view.findViewById(R.id.event_venue);
            date =  view.findViewById(R.id.event_date);
            ticketprice =  view.findViewById(R.id.event_ticket);

        }
    }

    public ExploreAdapter(List<Explore> exploreList) {
        this.exploreList = exploreList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trending,parent,false);

        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Explore trend = exploreList.get(position);
        holder.name.setText(trend.getName());
        holder.venue.setText(trend.getVenue());
        holder.date.setText(trend.getDate());
        holder.ticketprice.setText(trend.getTicketprice());

    }

    @Override
    public int getItemCount() {
        return exploreList.size();
    }
}
