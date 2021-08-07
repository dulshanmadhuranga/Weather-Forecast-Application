package com.example.w_app_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.w_app_1.ui.CityFragment;
import com.example.w_app_1.ui.TemperatureFragment;

public class Settings extends AppCompatActivity {

    Button City,Temperature_unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");

        City = (Button)findViewById(R.id.city);
        Temperature_unit = (Button)findViewById(R.id.temperature_unit);

        City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "City", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new CityFragment())
                        .commit();
            }
        });

        Temperature_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Temperature unit", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new TemperatureFragment())
                        .commit();
            }
        });


    }
}
