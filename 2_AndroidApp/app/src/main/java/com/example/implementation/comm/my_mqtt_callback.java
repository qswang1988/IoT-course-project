package com.example.implementation.comm;

import android.app.Activity;
import com.example.myapplication.R;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * @Author: Qiushi Wang
 *
 *
 * Description:
 * set mqtt call back functions, for receiving messages from simulation through broker
 * */
public class my_mqtt_callback implements MqttCallback {

    private String topic;
    private MqttMessage message;
    private Activity activity;

    public my_mqtt_callback(String topic, MqttMessage message, Activity activity) {
        this.topic = topic;
        this.message = message;
        this.activity = activity;
    }

    /**
     * This method is called when the connection to the server is lost.
     *
     * @param cause the reason behind the loss of connection.
     */
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection to broker lost!" + cause.getMessage());
    }

    /**
     * This method is called when a message arrives from the server.
     *
     * <p>
     * This method is invoked synchronously by the MQTT client. An
     * acknowledgment is not sent back to the server until this
     * method returns cleanly.</p>
     * <p>
     * If an implementation of this method throws an <code>Exception</code>, then the
     * client will be shut down.  When the client is next re-connected, any QoS
     * 1 or 2 messages will be redelivered by the server.</p>
     * <p>
     * Any additional messages which arrive while an
     * implementation of this method is running, will build up in memory, and
     * will then back up on the network.</p>
     * <p>
     * If an application needs to persist data, then it
     * should ensure the data is persisted prior to returning from this method, as
     * after returning from this method, the message is considered to have been
     * delivered, and will not be reproducible.</p>
     * <p>
     * It is possible to send a new message within an implementation of this callback
     * (for example, a response to this message), but the implementation must not
     * disconnect the client, as it will be impossible to send an acknowledgment for
     * the message being processed, and a deadlock will occur.</p>
     *
     * @param topic   name of the topic on the message was published to
     * @param message the actual message.
     * @throws Exception if a terminal error has occurred, and the client should be
     *                   shut down.
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        topic = this.topic;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        if(topic.equals(activity.getResources().getString(R.string.topic_recv))){
             try {
                JSONObject jsonObject = new JSONObject(new String(message.getPayload()));
                //System.out.println(time+" , RECEIVE A UPDATE JSON-OBJECT\n"+jsonObject.toString());
                System.out.println(time+" , RECEIVE A UPDATE JSON-OBJECT\n");
                //jsonObject.toJSONArray();
                String room = jsonObject.getString("room");

                AirConditioner ac = new AirConditioner();
                ac.setId(jsonObject.getInt("id"));
                String key = jsonObject.getString("room");
                ac.setRoom(key);
                ac.setOn_off(jsonObject.getInt("on_off"));
                ac.setAc_temperature(jsonObject.getDouble("ac_temperature"));
                ac.setMode(jsonObject.getInt("mode"));
                ac.setFan_speed(jsonObject.getString("fan_speed"));
                ac.setRoom_temperature(jsonObject.getDouble("room_temperature"));
                ac.setHumidity(jsonObject.getDouble("humidity"));
                synchronized (data.acs) {
                    data.acs.put(key, ac);
                    System.out.println("ac size after putting: " + data.acs.size());
                }
            }catch (JSONException jex){
                jex.printStackTrace();
            }
        }else{
            System.out.println(time+ " , message received can not be recognized");
        }
    }

    /**
     * Called when delivery for a message has been completed, and all
     * acknowledgments have been received. For QoS 0 messages it is
     * called once the message has been handed to the network for
     * delivery. For QoS 1 it is called when PUBACK is received and
     * for QoS 2 when PUBCOMP is received. The token will be the same
     * token as that returned when the message was published.
     *
     * @param token the delivery token associated with the message.
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public MqttMessage getMessage() {
        return message;
    }

    public void setMessage(MqttMessage message) {
        this.message = message;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
