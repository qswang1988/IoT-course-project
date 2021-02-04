package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.implementation.comm.try_conn;

/*
 * @author  Qiushi Wang
 *
 * Description:
 *
 * check network and connection to broker before loading mainActivity.
 */
public class initActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);

        Activity act = this;
        Thread conn = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                boolean c = false;
                while(!c) {
                    //c = mqtt_connection.check_network(act);
                    c = try_conn.conn_broker(act);
                    try {
                        Thread.sleep(1000);
                        System.out.print(".");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(initActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        );
        conn.start();
        /*
        try {
            conn.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(initActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
         */
    }
}
