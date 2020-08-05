package com.example.finalproject20s;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * This application helps you to find lyrics for the popular songs around he world
 * Along with doing a google search for the desired song
 *
 * @author Harmanpreet Bedi
 * @version 1.0
 * @since 2020-05-08
 *
 */

/**
 * This class loads an adapter in order to get the view that has used to display the songs ina list view
 *
 * @since 1.0
 */
public class MyListAdapter extends BaseAdapter {

    private Context cra;
    List<Song> songList= null;

    public MyListAdapter(@NonNull Context context, @NonNull ArrayList<Song> objects) {
        this.cra =context;
        this.songList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View newView=convertView;
        String songName=(getItem(position).getSongname());
        String artistName=(getItem(position).getArtist());
        LayoutInflater inflater=LayoutInflater.from(cra);
        newView=inflater.inflate(R.layout.activity_song_in_list,parent,false);
        TextView sN=newView.findViewById(R.id.songinlist);
        TextView aN=newView.findViewById(R.id.artistinlist);
        ImageView img=newView.findViewById(R.id.imageView);
        sN.setText(songName);
        aN.setText(artistName);
        img.setImageResource(R.drawable.musicalnotes);

        return newView;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public Song getItem(int i) {
        return songList.get(i);
    }
}
