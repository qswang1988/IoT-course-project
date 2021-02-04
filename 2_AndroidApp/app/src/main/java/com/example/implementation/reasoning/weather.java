package com.example.implementation.reasoning;

import android.app.Activity;
import com.example.myapplication.R;
import org.apache.commons.math3.util.Precision;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * @Author: Qiushi Wang
 * Description: get outdoor temperature of stockholm from public api
 *
 * */
public class weather {

    public static double local_temp = 8.0;

    public static void init_local_temp(Activity activity){
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                local_temp = get_local_temp(activity);
            }
            //System.out.println("local_outdoor_temperature: "+weather.local_temp);

        });
        t.start();
    }

    public static double get_local_temp(Activity activity){
        String city = activity.getResources().getString(R.string.home_region);
        String country = activity.getResources().getString(R.string.home_country);
        String key = activity.getResources().getString(R.string.weather_api_key);
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://community-open-weather-map.p.rapidapi.com/weather?q=" + city + "%2C" + country + "&lat=0&lon=0&callback=test&id=2172797&lang=null&units=%22metric%22%20or%20%22imperial%22")
                    .get()
                    .addHeader("x-rapidapi-key", key)
                    .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            String str = response.body().string();
            str = str.substring(5, str.length() - 1);
            //System.out.println(str);
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(str);
            double r = jsonObject.getJSONObject("main").getDouble("temp");
            r -= 273.15;
            System.out.println("getting outdoor temp: "+r);
            return Precision.round(r, 1);
        }catch(IOException|JSONException ex){
            ex.printStackTrace();
            return 8.0;
        }
    }


}
