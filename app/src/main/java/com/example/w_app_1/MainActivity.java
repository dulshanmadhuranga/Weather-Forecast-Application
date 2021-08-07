package com.example.w_app_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String city;
    private String temperatureType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Weather App");
        loadCity();
        loadTemperatureType();

        FetchData fetchData = new FetchData();
        fetchData.execute();
    }

    private void loadCity() {
        String city = getIntent().getStringExtra("city");
        this.city =city;
//        System.out.println("qqqqqpppppp"+city);
    }

    private void loadTemperatureType() {
        String temp_type = getIntent().getStringExtra("temp_type");
        this.temperatureType = temp_type;
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you need to exit the app..?");
        builder.setTitle(R.string.alert_title);
        builder.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case R.id.About:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                Intent intent_about = new Intent(MainActivity.this,About.class);
                startActivity(intent_about);
                return true;
            case R.id.Settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent intent_Settings = new Intent(MainActivity.this,Settings.class);
                startActivity(intent_Settings);
                return true;
            case R.id.Refresh:
                finish();
                startActivity(getIntent());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class FetchData extends AsyncTask<String,Void,String>{
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //TextView txt_day = (TextView) findViewById(R.id.day);
            //TextView txt_temp = (TextView) findViewById(R.id.temp);
            //TextView txt_weather = (TextView) findViewById(R.id.weather);

            try {
                ListView listView = findViewById(R.id.ListView);
                final List<String> display_location = new ArrayList<>();

                final String[] day =new String[7];
                final String[] temp_val = new String[7];
                final String[] day_weather = new String[7];
                final Integer[] icon_list = new Integer[7];
                final String[] humidity = new String[7];
                final String[] weather_description = new String[7];
                final String[] show_city = new String[7];
                final String[] show_description = new String[7];
                final String[] relevantDate = new String[7];

                JSONObject weatherData = new JSONObject(forecastJsonStr);
                JSONArray jarray = weatherData.getJSONArray("daily");

                for(int i=0;i<7;i++) {
                    JSONObject object = jarray.getJSONObject(i);
                    @SuppressLint("SimpleDateFormat")SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE");
                    SimpleDateFormat simpleDateFormat1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    String unixTimeString = object.getString("dt");
                    long unixTimeInt = Integer.parseInt(unixTimeString);
                    Date dateFormat = new java.util.Date(unixTimeInt * 1000);
                    String weekday = simpleDateFormat.format(dateFormat );

                    simpleDateFormat1.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                    String dateRelevant = simpleDateFormat1.format(dateFormat);
                    relevantDate[i] = dateRelevant;

                    String day_humidity = object.getString("humidity");
                    humidity[i] = day_humidity;

                    if (i == 0){
                        day[i] = weekday+"(TODAY)";
                    }else{
                        day[i] = weekday;
                    }

                    JSONObject temp = jarray.getJSONObject(i);
                    JSONObject d_temp = temp.getJSONObject("temp");

                    Double Temp = d_temp.getDouble("day");

                    if (temperatureType == null || temperatureType.equals("Celsius")) {
                        Double temp_value = Temp - 273.15;
                        temp_val[i]=String.format("%.2f"+"\u00B0"+"C",temp_value);
                    } else {
                        Double tempV = (Temp-273.15)*(9.0f/5.0f) +32.0f;
                        temp_val[i] =String.format("%.2f"+"\u00B0"+"F", tempV);
                    }

                    JSONArray Dweather = object.getJSONArray("weather");
                    JSONObject DescriptionWeather = Dweather.getJSONObject(0);
                    String main = DescriptionWeather.getString("main");
                    String description = DescriptionWeather.getString("description");

                    day_weather[i]= main;
                    weather_description[i] = description;

                    if(main.equals("Thunderstorm")){
                        icon_list[i] = R.drawable.thunderstorm;
                    }else if(main.equals("Rain")){
                        icon_list[i] = R.drawable.rain;
                    }else if(main.equals("Drizzle")){
                        icon_list[i] = R.drawable.drizzle;
                    }else if(main.equals("Clouds")){
                        icon_list[i] = R.drawable.clouds;
                    }else if(main.equals("Snow")){
                        icon_list[i] = R.drawable.snow;
                    }else if(main.equals("Clear")){
                        icon_list[i] = R.drawable.clear;
                    }else{
                        icon_list[i] = R.drawable.atmosphere;
                    }

                }

                CustomListAdapter adapter = new CustomListAdapter(MainActivity.this, icon_list,day, day_weather,temp_val );
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String show_day = day[+position];
                        String show_temp = temp_val[+position];
                        Integer show_icon = icon_list[+position];
                        String show_humidity = humidity[+position];
                        String show_description = weather_description[+position];
                        String date_Relevant = relevantDate[+position];

                        Toast.makeText(getApplicationContext(),show_day,Toast.LENGTH_SHORT);

                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);

                        if (city != null)
                            intent.putExtra("city",city);
                        else
                            intent.putExtra("city","Colombo");

                        intent.putExtra("date",show_day);
                        intent.putExtra("temperature",show_temp);
                        intent.putExtra("w_icon",show_icon);
                        intent.putExtra("description",show_description);
                        intent.putExtra("humidity",show_humidity);
                        intent.putExtra("date", date_Relevant);
                        startActivity(intent);

                    }
                });


            }catch(JSONException e){
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                List<Double> cordinates = null;

                String location = "Colombo";
                if (city != null) {
                    location = city;
                }

                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addresses= geocoder.getFromLocationName(location, 5);

                cordinates = new ArrayList<>(addresses.size()); //save the coordinates if they are available
                for(Address a : addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        cordinates.add(a.getLatitude());
                        cordinates.add(a.getLongitude());
                    }
                }

                final String BASE_URL = "https://api.openweathermap.org/data/2.5/onecall?lat="+cordinates.get(0)+"&lon="+cordinates.get(1)+"&exclude=minutely,hourly,alerts,current&appid=fce84fb4a3a0ae7294fc9a3347763796";
                URL url = new URL(BASE_URL);

                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer =new StringBuffer();

                if (inputStream == null){return null;}
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line1;

                while ((line1 = reader.readLine()) != null ){buffer.append(line1 + "\n");}
                if (buffer.length() == 0){return null;}
                forecastJsonStr = buffer.toString();

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT).show();
                return null;
            }
            finally {
                if(urlConnection != null){urlConnection.disconnect();}
                if (reader != null){
                    try {
                        reader.close();
                    }catch (final Exception e){
                        Log.e("Hi","ERROR Closing stream", e);
                        Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            return null;
        }
    }
}
