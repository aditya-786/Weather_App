package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.neovisionaries.i18n.LanguageCode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public RelativeLayout RL1;
    public EditText search_bar;
    public TextView time,day,temp;
    public TextView HumidityT2, SunriseTV,  WindSpeedT2, WindDirT2, PressureT2, SunsetTV;
    public static final Random myRandomNumber= new Random();

    String API_Key = "56cc619fb5a4478408a79923fcafe165";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assigningIDS();
    }

    public void gettingWeather(View view){

        changeBackgroundImage();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();

        WeaInter weaInter = retrofit.create(WeaInter.class);
        Call<Example> exampleCall = weaInter.getWeather(search_bar.getText().toString().trim(), API_Key);

        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                if (response.code() == 404){
                    Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
                }else if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }

                Example myData = response.body();
                Main main = myData.getMain();
                int tempe = (int)(main.getTemp()-273.15);
                StringBuilder fetchedTemperature = new StringBuilder(tempe+" °C");
                temp.setText(fetchedTemperature);

                int press = (int)(main.getPressure());
                StringBuilder pressure = new StringBuilder(press+" Pa");
                PressureT2.setText(pressure);

                int humid = (int)(main.getHumidity());
                StringBuilder humidity = new StringBuilder(humid+" g.kg-1");
                HumidityT2.setText(humidity);

                Wind wind = myData.getWind();
                int windSpeed = (int)(wind.getSpeed()-0);
                StringBuilder wSpeed = new StringBuilder(windSpeed+" km/h");
                WindSpeedT2.setText(wSpeed);

                double windDirection = (wind.getDeg());

                StringBuilder dir = new StringBuilder();
                if (windDirection>=348.75 && windDirection<33.75) dir.append("North");
                else if (windDirection>=33.75 && windDirection<78.75) dir.append("North East");
                else if (windDirection>=78.75 && windDirection<123.75) dir.append("East");
                else if (windDirection>=123.75 && windDirection<168.75) dir.append("South East");
                else if (windDirection>=168.75 && windDirection<213.75) dir.append("South");
                else if (windDirection>=213.75 && windDirection<258.75) dir.append("South West");
                else if (windDirection>=258.75 && windDirection<303.75) dir.append("West");
                else if (windDirection>=303.75 && windDirection<348.75) dir.append("North West");

                WindDirT2.setText(dir);

                Sys sys = myData.getSys();
                int timeInMilliSeconds = sys.getSunrise();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH).withZone(ZoneId.systemDefault());
                StringBuilder sunriseTime = new StringBuilder(formatter.format(Instant.ofEpochSecond(timeInMilliSeconds)));
                SunriseTV.setText(sunriseTime);


                timeInMilliSeconds = sys.getSunset();
                formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH).withZone(ZoneId.systemDefault());
                sunriseTime = new StringBuilder(formatter.format(Instant.ofEpochSecond(timeInMilliSeconds)));
                SunsetTV.setText(sunriseTime);

//                Calendar calendar = Calendar.getInstance();
//                TimeZone tz = TimeZone.getDefault();
//                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                java.util.Date currentTimeZone=new java.util.Date((long)sys.getSunrise()*1000);
                //SunriseTV.setText(currentTimeZone.toString());

                StringBuilder stringBuilder = new StringBuilder(currentTimeZone.toString().split(" ")[0]);
                StringBuilder currentDay = new StringBuilder();

                if (stringBuilder.substring(0,3).equals("Mon")) currentDay.append("Monday");
                else if (stringBuilder.substring(0,3).equals("Tue")) currentDay.append("Tuesday");
                else if (stringBuilder.substring(0,3).equals("Wed")) currentDay.append("Wednesday");
                else if (stringBuilder.substring(0,3).equals("Thu")) currentDay.append("Thursday");
                else if (stringBuilder.substring(0,3).equals("Fri")) currentDay.append("Friday");
                else if (stringBuilder.substring(0,3).equals("Sat")) currentDay.append("Saturday");
                else if (stringBuilder.substring(0,3).equals("Sun")) currentDay.append("Sunday");

                day.setText(currentDay);

                stringBuilder.setLength(0);

                stringBuilder.append(currentTimeZone.toString().split(" ")[1]).append(" ").append(currentTimeZone.toString().split(" ")[2]);
                time.setText(stringBuilder);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    public static String formatTime(Instant time) {
//        return formatter.format(time);
//    }

    public void assigningIDS(){
        search_bar=findViewById(R.id.search_bar);
        time=findViewById(R.id.time);
        day=findViewById(R.id.day);
        temp=findViewById(R.id.temp);
        HumidityT2=findViewById(R.id.HumidityT2);
        SunriseTV=findViewById(R.id.DewPointT2);
        WindSpeedT2=findViewById(R.id.WindSpeedT2);
        WindDirT2=findViewById(R.id.WindDirT2);
        PressureT2=findViewById(R.id.PressureT2);
        SunsetTV=findViewById(R.id.WindChillT2);

        RL1 = findViewById(R.id.RL1);
    }

    public void changeBackgroundImage(){
        int[] images = new int[]{R.drawable.one,
                R.drawable.two,
                R.drawable.three,
                R.drawable.four,
                R.drawable.five,
                R.drawable.six,
                R.drawable.seven,
                R.drawable.eight,
                R.drawable.nine,
                R.drawable.ten,
                R.drawable.eleven,
                R.drawable.twelve,
                R.drawable.thirtheen,
                R.drawable.fouteen,
                R.drawable.fifteen,
                R.drawable.sixteen,
                R.drawable.seventeen,
                R.drawable.eighteen,
                R.drawable.nineteen,
                R.drawable.twenty};
        int index = myRandomNumber.nextInt(images.length);
        RL1.setBackgroundResource(images[index]);
    }

}