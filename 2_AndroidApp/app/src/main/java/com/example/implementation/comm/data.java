package com.example.implementation.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author  Qiushi Wang
 *
 * Description:
 * local storage for current status of all air conditioners.
 *
 *
 * */

public class data {
    // the acs here is for storing the status of all air conditioners.
    // it is initialized by main activity. after sending request_info to broker
    // the application will receive updates of all air conditioners from the Node-Red simulation
    // then the acs get initialized.
    // elements in acs get updated if any update receive from the Node-Red simulation
    // use room name as key, use AirConditioner object as value
    public static Map<String,AirConditioner> acs = new HashMap<>();

    // generate a HashMap for displaying devices in MyHomeFragment
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        for(String s: acs.keySet()){
            AirConditioner ac = acs.get(s);
            List<String> element = new ArrayList<String>();

            element.add("Status: "+(ac.getOn_off()==1?"on":"off"));
            element.add("Mode: "+(ac.getMode() == 1?"cooling":"warming"));
            element.add("Room Temp: "+String.valueOf(ac.getRoom_temperature()) + "℃");
            element.add("Ac Temp: "+String.valueOf(ac.getAc_temperature()) + "℃");
            element.add("Humidity: "+String.valueOf(ac.getHumidity()) + "%");
            element.add("Fan Speed: "+ac.getFan_speed());

            expandableListDetail.put(ac.getRoom(),element);
        }

        return expandableListDetail;
    }
}
