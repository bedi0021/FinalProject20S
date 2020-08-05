package com.example.finalproject20s;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject20s.MatchModel;
import com.example.finalproject20s.DatabaseOpener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * this class returns the view of favaourite matches choosed
 */
public class FavouriteAdapter extends RecyclerView.Adapter<com.example.finalproject20s.FavouriteAdapter.MyViewHolder> {

    private ArrayList<MatchModel> mArrResources;
    Context context;

    public FavouriteAdapter(ArrayList<MatchModel> arrResources, Context context) {
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
                MatchModel matchObj = mArrResources.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle(matchObj.getTitle())
                        .setMessage("Title : " + matchObj.getTitle() + "\nDescription : " + matchObj.getUrl())
                        .setPositiveButton("Open In Browser", (click, b) -> {
                            Intent intent=new Intent((Intent.ACTION_VIEW));
                            intent.setData(Uri.parse(matchObj.getUrl()));
                            view.getContext().startActivity(intent);
                        })
                        .setNegativeButton("Delete", (click, b) -> {
                            deleteMatch(matchObj); //remove the contact from database
                            mArrResources.remove(position); //remove the match from match list
                            notifyDataSetChanged(); //there is one less item so update the list
                        })
                        .setNeutralButton("dismiss", (click, b) -> { })
                        .create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrResources.size();
    }

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

    /**
     * method to delete matches from list of favourites
     * @param matchModel
     */
    protected void deleteMatch(MatchModel matchModel) {
        DatabaseOpener dbOpener = new DatabaseOpener(context);
        SQLiteDatabase db = dbOpener.getWritableDatabase();
        db.delete(DatabaseOpener.TABLE_NAME, DatabaseOpener.COL_ID + "= ?", new String[]{Long.toString(matchModel.getId())});
        db.close();
    }
}
