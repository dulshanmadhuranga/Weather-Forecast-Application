package com.example.w_app_1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final Integer[] iconid;
    private final String[] day;
    private final String[] weather;
    private final String[] temp;

    public CustomListAdapter(Activity context, Integer[] iconid, String[] day, String[] weather,String[] temp){
        super(context, R.layout.my_list,day);

        this.context = context;
        this.iconid = iconid;
        this.day = day;
        this.weather = weather;
        this.temp = temp;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater= context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.my_list,null,true);
        ImageView imageView =(ImageView) rowView.findViewById(R.id.iconid);
        TextView txt_day = (TextView) rowView.findViewById(R.id.day);
        TextView txt_weather = (TextView) rowView.findViewById(R.id.weather);
        TextView txt_temp = (TextView) rowView.findViewById(R.id.temp);

        imageView.setImageResource(iconid[position]);
        txt_day.setText(day[position]);
        txt_weather.setText(weather[position]);
        txt_temp.setText(temp[position]);
        return rowView;
    }

}
