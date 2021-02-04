package com.example.implementation.comm;

import android.app.Activity;
import android.content.Intent;
import com.example.myapplication.R;
import com.example.myapplication.initActivity;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/*
* @Author: Qiushi Wang
* Description:
*
* mqtt connection
*
* Note: We were inspired by sample code of eclipse paho during the researching phase
* https://www.eclipse.org/paho/index.php?page=clients/java/index.php
* */

public class mqtt_connection {

    public static MqttClient mqttClient;

    public static MqttClient mqtt_init_allinone(Activity activity){
        MqttClient mqttClient = null;
        String broker = activity.getString(R.string.broker);
        String clientId = activity.getResources().getString(R.string.mqtt_client_id);
        String topic_recv = activity.getString(R.string.topic_recv);
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(broker, clientId, persistence);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        try {
            mqttClient.connect(connOpts);
            mqttClient.subscribe(topic_recv,2);
            my_mqtt_callback callback = new my_mqtt_callback(topic_recv,new MqttMessage(),activity);
            mqttClient.setCallback(callback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        System.out.println("mqtt init done");
        return mqttClient;
    }

    public static void check_connection(Activity activity){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while(true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("checking mqtt connection ");
                    synchronized (mqtt_connection.mqttClient) {
                        try {
                            if(mqtt_connection.mqttClient.isConnected()){ // connected

                            } else { // not connected
                                System.out.print(" : connection broken up, trying reconnecting ");
                                // set mic enable false
                                //if(activity.findViewById(R.id.imageButton).isEnabled()){
                                    //activity.findViewById(R.id.imageButton).setEnabled(false);
                                //}
                                if(count<3){
                                    count++;
                                    continue;
                                }

                                if(try_conn.conn_broker(activity)){
                                    // reconnecting
                                    MqttConnectOptions connOpts = new MqttConnectOptions();
                                    connOpts.setCleanSession(false);
                                    mqttClient.connect(connOpts);
                                    count = 0;
                                }else{
                                    Intent intent = new Intent(activity, initActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    activity.startActivity(intent);
                                    break;
                                }
                            }
                        } catch (MqttSecurityException e) {
                            e.printStackTrace();
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t.start();
    }

    public static boolean mqtt_publish(Activity activity,MqttClient mqttClient,String topic,String msg){

        if(!mqttClient.isConnected())
            return false;

        MqttMessage message = new MqttMessage();
        int qos = Integer.parseInt(activity.getString(R.string.qos));
        message.setQos(qos);
        message.setPayload(msg.getBytes());
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Message published");
        System.out.println(msg);
        return true;
    }
}
