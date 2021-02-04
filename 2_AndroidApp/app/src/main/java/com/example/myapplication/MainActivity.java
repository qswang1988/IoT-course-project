package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.implementation.comm.vibrate;
import com.example.implementation.reasoning.weather;
import com.example.implementation.voice.Recording_N_Recognition;
import com.example.implementation.comm.mqtt_connection;
import com.example.implementation.voice.my_RecognitionListenerImpl;
import com.example.implementation.comm.data;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/*
 * @author Qiushi Wang
 * @author Shu Zhang
 *
 * Description: the core activity. initialize fragments, initialize services.
 *
 */

public class MainActivity extends AppCompatActivity {

    public static MainFragment mainFragment;
    public static SettingFragment settingFragment;
    public static MyHomeFragment myHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_main);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();


        /*
        *                   *
        *   INITIALIZING    *
        *                   *
        * */
        // mqtt init
        mqtt_connection.mqttClient = mqtt_connection.mqtt_init_allinone(this);
        // init local temperature
        //weather.local_temp = 6.0; test
        weather.init_local_temp(this);
        // init check mqtt connection thread
        mqtt_connection.check_connection(this);
        //System.out.println("mqtt connection checker set");
        //System.out.println("Main Activity ongoing");
        // init speech recognizer
        Recording_N_Recognition.init(this,this);
        // check vibration permission
        vibrate.checkVibrationPermission(this);
        // set recognizer listener
        Recording_N_Recognition.speechRecognizer.setRecognitionListener(new my_RecognitionListenerImpl(this));
        // init room information. send a request to home devices, update home device info
        if(data.acs.isEmpty()){
            Map<String,String> map = new HashMap<>();
            map.put("request_info","request_info");
            JSONObject jsonObject = new JSONObject(map);
            mqtt_connection.mqtt_publish(this,mqtt_connection.mqttClient,this.getResources().getString(R.string.topic_request),jsonObject.toString());
            System.out.println("sent S/request info: MainActivity");
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            myHomeFragment = new MyHomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myHomeFragment).commit();
                            break;

                        case R.id.nav_main:
                            //if(mainFragment==null)
                            mainFragment = new MainFragment();
                             //setMainFragment(mainF);
                            //mainF.onCreate(new Bundle());
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
                            break;
                        case R.id.nav_setting:
                            if(settingFragment == null)
                                settingFragment = new SettingFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingFragment).commit();
                            break;
                    }

                    return true;
                }
            };

}