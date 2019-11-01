package com.partypeople.user.adaptors.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.partypeople.user.R;
import com.partypeople.user.models.Trending;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MyViewHolder>{
    private Context mContext;
    private List<Trending> eventList;

    public  class  MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name, details, venue, date, timefrom, timeto, ticketprice, artist1, artist2, artist3;

        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.event_name);
            image =  view.findViewById(R.id.event_id);
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

    public TrendingAdapter(Context mContext, List<Trending> eventList) {
        this.eventList = eventList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trending,parent,false);

        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Trending trend = eventList.get(position);

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

        Glide.with(mContext).load(trend.getImage()).into(holder.image);

       /* holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.image);
            }
        });*/
    }

    /*private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_event, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            return false;
        }
    }*/

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
