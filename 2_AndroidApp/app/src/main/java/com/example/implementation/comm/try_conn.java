package com.example.implementation.comm;

import android.app.Activity;
import com.example.myapplication.R;
import java.io.IOException;
import java.net.Socket;
/*
 * @Author: Qiushi Wang
 * Description:
 * Simply test the connection to broker before initializing everything
 * */
public class try_conn {
    public static boolean conn_broker(Activity activity){
        String ip = activity.getResources().getString(R.string.broker_host);
        int port = Integer.parseInt(activity.getResources().getString(R.string.broker_port));
        try {
            Socket s = new Socket(ip,port);
        } catch (java.net.ConnectException e){
            System.out.println("can not access to broker..");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
