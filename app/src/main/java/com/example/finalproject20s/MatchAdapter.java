package com.example.finalproject20s;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject20s.MatchModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * this class returns the view of the page that shows the list of matches
 */

public class MatchAdapter extends RecyclerView.Adapter<com.example.finalproject20s.MatchAdapter.MyViewHolder> {

    private ArrayList<MatchModel> mArrResources;
    Context context;

    public MatchAdapter(ArrayList<MatchModel> arrResources, Context context) {
        this.mArrResources = arrResources;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_match,parent,false);
        MyViewHolder vh = new MyViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.match.setText(mArrResources.get(position).getTitle());
        holder.date.setText("Date: "+mArrResources.get(position).getDate().substring(0,10));
        Picasso.with(context).load(mArrResources.get(position).getThumbnail()).into(holder.thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MatchDetailActivity.class);
                MatchModel matchObj = mArrResources.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("matchObj",matchObj);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrResources.size();
    }

    /**
     * it is the inner class in matchAdapter Activity
     * extends the ViewHolder from RecyclerView
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView match, date;
        ImageView thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);
            match = (TextView) itemView.findViewById(R.id.match);
            date = (TextView) itemView.findViewById(R.id.date);
            thumbnail=itemView.findViewById(R.id.profile_image2);
        }
    }
}
