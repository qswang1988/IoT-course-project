package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.implementation.voice.Recording_N_Recognition;

/*
 * @author  Shu Zhang
 *
 * Description:
 * Main Fragment where we put the microphone button
 */

public class MainFragment extends Fragment {
    // mic
    public static ImageButton mic_ImageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mic_ImageButton = view.findViewById(R.id.imageButton);
        // Start Recording and Recognizing
        mic_ImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("pressed");
                mic_ImageButton.setImageResource(R.drawable.pressed_mic);
                // can not press button now
                mic_ImageButton.setEnabled(false);
                Recording_N_Recognition.speechRecognizer.startListening(Recording_N_Recognition.speechRecognizerIntent);
            }
        });
        //return inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

}
