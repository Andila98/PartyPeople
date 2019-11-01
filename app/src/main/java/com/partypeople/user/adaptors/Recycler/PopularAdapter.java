package com.partypeople.user.adaptors.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.partypeople.user.R;
import com.partypeople.user.models.Popular;
import com.partypeople.user.models.Trending;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder>{

    private List<Popular> eventList;

    public  class  MyViewHolder extends RecyclerView.ViewHolder{
       // public ImageView image;
        public TextView name, details, venue, date, timefrom, timeto, ticketprice, artist1, artist2, artist3;

        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.event_name);
           // image =  view.findViewById(R.id.event_id);
            details =  view.findViewById(R.id.event_details);
            venue =  view.findViewById(R.id.event_venue);
            date =  view.findViewById(R.id.event_date);
            timefrom =  view.findViewById(R.id.event_time1);
            timeto =  view.findViewById(R.id.event_time2);
            ticketprice =  view.findViewById(R.id.event_ticket);
            artist1 =  view.findViewById(R.id.event_artist1);
            artist2 =  view.findViewById(R.id.event_artist2);
            artist3 =  view.findViewById(R.id.event_artist3);

        }
    }

    public PopularAdapter(List<Popular> moviesList) {
        this.eventList = moviesList;
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
        Popular trend = eventList.get(position);
//        holder.image.setImageURI(trend.getImage());
        holder.name.setText(trend.getName());
        holder.venue.setText(trend.getVenue());
        holder.details.setText(trend.getDetails());
        holder.date.setText(trend.getDate());
        holder.ticketprice.setText(trend.getTicketprice());
        holder.timeto.setText(trend.getTimeto());
        holder.timefrom.setText(trend.getTimefrom());
        holder.artist1.setText(trend.getArtist1());
        holder.artist2.setText(trend.getArtist2());
        holder.artist3.setText(trend.getArtist3());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
