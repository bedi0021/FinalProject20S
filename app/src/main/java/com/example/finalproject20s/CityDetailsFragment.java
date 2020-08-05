package com.example.finalproject20s;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityDetailsFragment extends Fragment {

    public CityDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * SQLiteDatabase instance
     */
    private SQLiteDatabase db;

    /**
     * ID of the city
     */
    private long id = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_city_details, container, false);

       Bundle i =getArguments();

        id = i.getLong("ID", -1);
        String cityNameStr = i.getString("CITY_NAME");
        String countryNameStr = i.getString("COUNTRY_NAME");
        String regionStr = i.getString("REGION");
        String currencyStr = i.getString("CURRENCY");
        String longitudeStr = i.getString("LONGITUDE");
        String latitudeStr = i.getString("LATITUDE");

        TextView cityName = v.findViewById(R.id.cityName);
        TextView countryName = v.findViewById(R.id.countryName);
        TextView region = v.findViewById(R.id.region);
        TextView currency = v.findViewById(R.id.currency);
        TextView longitude = v.findViewById(R.id.longitude);
        TextView latitude = v.findViewById(R.id.latitude);

        cityName.setText("City Name: " + cityNameStr);
        countryName.setText("Country Name:" +countryNameStr);
        region.setText("Region:" +regionStr);
        currency.setText("Currency:" +currencyStr);
        longitude.setText("Longitude:" +longitudeStr);
        latitude.setText("Latitude:" +latitudeStr);

        Button googleMaps = v.findViewById(R.id.googleMaps);
        googleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:" + latitudeStr + "," + longitudeStr);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        MyOpener dbOpener = new MyOpener(getActivity());
        db = dbOpener.getWritableDatabase();

        Button addToFav = v.findViewById(R.id.addToFav);
        if(id != -1) {
            addToFav.setText(R.string.remove_from_favorite);
        }

        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id != -1) {
                    db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "=?", new String[]{Long.toString(id)});
                    id = -1;
                    addToFav.setText(R.string.add_to_favorite);
                    Toast.makeText(getActivity(), R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(MyOpener.COL_CITY_NAME, cityNameStr);
                    newRowValues.put(MyOpener.COL_COUNTRY, countryNameStr);
                    newRowValues.put(MyOpener.COL_REGION, regionStr);
                    newRowValues.put(MyOpener.COL_CURRENCY, currencyStr);
                    newRowValues.put(MyOpener.COL_LONGITUDE, longitudeStr);
                    newRowValues.put(MyOpener.COL_LATITUDE, latitudeStr);

                    id = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

                    addToFav.setText(R.string.remove_from_favorite);
                    Toast.makeText(getActivity(), R.string.add_to_favorite, Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

}
