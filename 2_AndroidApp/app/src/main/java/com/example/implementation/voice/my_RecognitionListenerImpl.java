package com.example.implementation.voice;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import com.example.implementation.comm.AirConditioner;
import com.example.implementation.comm.mqtt_connection;
import com.example.implementation.comm.vibrate;
import com.example.implementation.reasoning.reasoner;
import com.example.myapplication.MainFragment;
import com.example.myapplication.R;
import java.util.ArrayList;

/*
 * @Author: Qiushi Wang
 * Description: implement the interface RecognitionListener.
 *
 *  call method reasoner.do_reasoning() if user's speech is captured.
 * */
public class my_RecognitionListenerImpl implements RecognitionListener {

//    private ImageButton mic_ImageButton;
    private Activity activity;

    public my_RecognitionListenerImpl(Activity activity) {
//        this.mic_ImageButton = activity.findViewById(R.id.imageButton);
        this.activity = activity;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {}
    @Override
    public void onBeginningOfSpeech() {
        //System.out.println("begin speech");
    }
    @Override
    public void onRmsChanged(float rmsdB) {
    }
    @Override
    public void onBufferReceived(byte[] buffer) {}
    @Override
    public void onEndOfSpeech() {
        //System.out.println("ending speech");
        // in case of saying nothing, so the even end here. not going to onResult

        //MainActivity.getMainFragment().getMic_ImageButton().setEnabled(true);
        //MainActivity.getMainFragment().getMic_ImageButton().setImageResource(R.drawable.microphone);

        MainFragment.mic_ImageButton.setEnabled(true);
        MainFragment.mic_ImageButton.setImageResource(R.drawable.microphone);

    }
    @Override
    public void onError(int error) {}
    @Override
    public void onResults(Bundle results) {
        System.out.println("voice recognition on result");

        ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        // processing, reasoning
        // processing language
        String text = data.get(0);
        text = text.toLowerCase();
        System.out.println("Recognized:\n"+text);

        // check local data.acs, if it's empty, then sent request_info
        if(com.example.implementation.comm.data.acs.isEmpty()){
            mqtt_connection.mqtt_publish(activity,mqtt_connection.mqttClient,activity.getResources().getString(R.string.topic_request),"request_info");
            System.out.println("sent S/request_info: my_RecognitionListenerImpl");
            return;
        }

        // reasoning & generating messages
        ArrayList<AirConditioner> commands = reasoner.do_reasoning(activity,text);
        for(AirConditioner a:commands){
            String msg = a.toString();
            // publishing messages
            mqtt_connection.mqtt_publish(activity,mqtt_connection.mqttClient,activity.getResources().getString(R.string.topic_command),msg);

        }
        vibrate.vib(activity);
        // enable components
        MainFragment.mic_ImageButton.setEnabled(true);
        MainFragment.mic_ImageButton.setImageResource(R.drawable.microphone);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {}
    @Override
    public void onEvent(int eventType, Bundle params) {}

    //
//    public ImageButton getMic_ImageButton() {
//        return mic_ImageButton;
//    }
//
//    public void setMic_ImageButton(ImageButton mic_ImageButton) {
//        this.mic_ImageButton = mic_ImageButton;
//    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
