package com.example.implementation.voice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Locale;
/*
 * @author  Qiushi Wang
 *
 * Description:
 *
 * Init SpeechRecognizer
 *
 * Note: We get inspired by the project below when we were in researching phase. That's how we know the SpeechRecongnizer class in Android
 * https://github.com/abhinav0612/SpeechToText/blob/master/app/src/main/java/com/example/texttospeech/MainActivity.java
 *
 * */
public class Recording_N_Recognition {

    public static SpeechRecognizer speechRecognizer;
    public static int recordAudioRequestCode = 1;
    public static Intent speechRecognizerIntent;

    public static boolean init(Context context,Activity activity){
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkRecordPermission(activity);
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK);
        return false;
    }

    private static void checkRecordPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.RECORD_AUDIO},recordAudioRequestCode);
        }
    }

    public static SpeechRecognizer getSpeechRecognizer() {
        return speechRecognizer;
    }

    public static void setSpeechRecognizer(SpeechRecognizer speechRecognizer) {
        Recording_N_Recognition.speechRecognizer = speechRecognizer;
    }

}
