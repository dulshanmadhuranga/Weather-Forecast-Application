package com.example.w_app_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main2Activity extends AppCompatActivity {
    TextView textViewDate;
    TextView country_region;
    ImageView weather_icon;
    TextView temp;
    TextView description;
    TextView humidity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("Weather Details");

        textViewDate = (TextView)findViewById(R.id.date);
        country_region = (TextView)findViewById(R.id.country);
        weather_icon = (ImageView)findViewById(R.id.w_icon);
        temp = (TextView)findViewById(R.id.temperature);
        description = (TextView)findViewById(R.id.description);
        humidity = (TextView)findViewById(R.id.humidity);

        Bundle bundle = getIntent().getExtras();

        Integer final_icon = bundle.getInt("w_icon");
        String final_temp = getIntent().getStringExtra("temperature");
        String final_humidity = getIntent().getStringExtra("humidity");
        String final_weather_des = getIntent().getStringExtra("description");
        String city = getIntent().getStringExtra("city");
        String date = getIntent().getStringExtra("date");

        country_region.setText(city);
        weather_icon.setImageResource(final_icon);
        temp.setText("Temperature: "+final_temp);
        description.setText(final_weather_des);
        humidity.setText("Humidity: " + final_humidity + "%");
        textViewDate.setText(date);

    }
}
