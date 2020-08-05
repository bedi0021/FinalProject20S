package com.example.finalproject20s;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
 * This class is creating a fragment in order to store a fragment which pops out from the activity
 * to show the Song's name, artist name and its lyrics.
 */
public class DetailFragment extends Fragment {
    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        View result = inflater.inflate(R.layout.activity_fragment, container, false);

        TextView fID = (TextView) result.findViewById(R.id.fragId);
        TextView fSong = (TextView) result.findViewById(R.id.fragSong);
        TextView fArtist = (TextView) result.findViewById(R.id.fragArtist);
        TextView fLyrics = (TextView) result.findViewById(R.id.fragLyrics);
        //Getting the data from saved strings to store it to our fragment
        fID.setText(dataFromActivity.getString(FavouriteList.SHOW_ID));
        fArtist.setText(dataFromActivity.getString(FavouriteList.SHOW_ARTIST));
        fSong.setText(dataFromActivity.getString(FavouriteList.SHOW_SONG));
        fLyrics.setText(dataFromActivity.getString(FavouriteList.SHOW_LYRICS));


        id = dataFromActivity.getLong(FavouriteList.SHOW_ID);

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity) context;
    }
}