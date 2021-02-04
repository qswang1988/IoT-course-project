
package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*
 * @author  Shu Zhang
 *
 * Description:
 * fragment for displaying the settings of the android application.
 *
 */
public class SettingFragment extends Fragment {
    public static TextView port;
    public static TextView addr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        addr = view.findViewById(R.id.mqtt_addr);
        addr.setText(this.getResources().getString(R.string.broker_host));
        port = view.findViewById(R.id.mqtt_port);
        port.setText(this.getResources().getString(R.string.broker_port));
        return view;
    }

}

