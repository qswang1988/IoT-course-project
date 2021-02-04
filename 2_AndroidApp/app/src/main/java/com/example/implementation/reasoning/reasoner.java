package com.example.implementation.reasoning;

import android.app.Activity;
import com.example.implementation.comm.AirConditioner;
import com.example.implementation.comm.data;
import org.apache.commons.math3.util.Precision;
import java.util.ArrayList;

/*
 * @Author: Qiushi Wang
 * Description: reasoner service
 * */

public class reasoner {

    static String[] actionPhrase = {"turn","turning","switch","switching","set","setting"};

    // reasoning in this function
    public static ArrayList<AirConditioner> do_reasoning(Activity activity,String input){
        String tagged = CommandAnalyzer.tagged(input);

        ArrayList<String> rooms = check_rooms(tagged);

        ArrayList<AirConditioner> commands = new ArrayList<>();

        boolean onoff = check_on_off(input);
        //System.out.println(tagged);
        System.out.println("onoff: "+onoff);
        // turn on only living room
        // if turn off, but not specifying rooms, then turn ac off for all rooms
        // if turn on, but not specifying rooms, then turn only living room
        if(rooms==null||rooms.isEmpty()){
            if(onoff) {
                //rooms.add("living room");
            }
            else{
                for(String s:data.acs.keySet()){
                    rooms.add(s);
                }
            }
        }
        // if the recognized room name can not be matched with a real room, delete it.
        for(String s:rooms){
            if(!data.acs.containsKey(s)){
                rooms.remove(s);
            }
        }

        for(String s:rooms){
            AirConditioner ac = data.acs.get(s);
            if(onoff){ // on
                ac.setOn_off(1);
                double env_temp = weather.local_temp;
                ac.setAc_temperature(get_setting_temp(env_temp,s));
                ac.setFan_speed(get_fan_speed(env_temp,s));
                ac.setMode(get_mode(env_temp,s));
                commands.add(ac);
            }else{ // off
                ac.setOn_off(0);
                commands.add(ac);
            }
        }
        return commands;
    }

    // check whether the user want to turn on or turn off air conditioners
    // @params tagged text of user's speech
    public static boolean check_on_off(String tagged){
        System.out.println("checking on_off");
        //System.out.println(tagged);
        String [] strary = tagged.split(" ");
        boolean verb = false;
        boolean onoff = true;

        for(String v:strary){
            for(String s:actionPhrase){
                if(v.matches(s)){
                    verb = true;
                    break;
                }
            }
        }

        int j = 0;
        for(String v:strary){
            if(v.matches("off.*")||v.matches("of.*")) {
                if(strary [j-1].matches(".*room.*"))
                    return false;
                onoff = false;
                break;
            }else if(v.matches("on.*")) {
                if(strary [j-1].matches(".*room.*"))
                    return true;
                onoff = true;
                break;
            }
            j++;
        }
        System.out.println("verb: "+verb+" on_off: "+onoff);
        if(verb && (onoff==false))
            return false;
        else
            return true;
    }

    // check which rooms are involved
    // @param tagged text of user's speech
    public static ArrayList<String> check_rooms(String tagged){

        // temp, mode, fan_speed, room, on/off
        System.out.println(tagged);
        //System.out.println("checked rooms");
        String [] strTaggedArray = tagged.split(" ");
        ArrayList<String> roomList = new ArrayList<>();
        int j = 0;
        for(;j<strTaggedArray.length;j++){

            if(strTaggedArray[j].matches(".*bedrooms.*")) {
                for(String s:data.acs.keySet()){
                    if(s.matches(".*bedroom.*")) {
                        roomList.add(s);
                    }
                }
                break;
            }else if(strTaggedArray[j].matches(".*bedroom.*")){
                //if(j!=0 &&((strTaggedArray[j-1].matches(".*master.*")|| (strTaggedArray[j-1].matches(".*guest.*")||strTaggedArray[j-1].matches(".*_JJ.*"))))){
                if(j!=0 && (strTaggedArray[j-1].matches(".*_JJ.*")||strTaggedArray[j-1].matches(".*_NN.*"))){
                    String t = strTaggedArray [j-1].split("_")[0]+" "+strTaggedArray [j].split("_")[0];
                    roomList.add(t);
                }else{
                    for(String s:data.acs.keySet()){
                        if(s.matches(".*bedroom.*")){
                            roomList.add(s);
                            break;
                        }
                    }
                }
            }else if(strTaggedArray[j].matches(".*rooms.*")) {
                for(String s:data.acs.keySet()){
                    roomList.add(s);
                }
                break;
            }else if(strTaggedArray[j].matches(".*room.*")){
                if(j!=0 && (strTaggedArray[j-1].matches(".*_NN.*")||strTaggedArray[j-1].matches(".*_VBG.*"))){
                    String t = strTaggedArray [j-1].split("_")[0]+" "+strTaggedArray [j].split("_")[0];
                    roomList.add(t);
                }
            }else if(strTaggedArray[j].matches("all")||strTaggedArray[j].matches("every.*")){
                for(String s:data.acs.keySet()){
                    roomList.add(s);
                }
                break;
            }
        }

        for(String s:roomList){
            System.out.println("##################");
            System.out.println("room: "+s);
            //System.out.println("##################");
        }

        return roomList;
    }

    // give an appropriate fan speed setting
    // fan speed fast,medium,slow
    public static String get_fan_speed(Double env_temp,String room){
        //double env_temp = weather.get_local_temp(activity);
        double room_temp = data.acs.get(room).getRoom_temperature();

        if(room_temp>=30){
            return "fast";
        }else if(room_temp>=24){
            return "medium";
        }else if(room_temp<10){
            return "fast";
        }else if(room_temp<12){
            return "medium";
        }else if(room_temp<16){
            return "slow";
        }else{
            return "slow";
        }
    }

    // give an appropriate mode setting
    // 1 = cooling, 2 = warming
    public static int get_mode(double env_temp,String room){
        //double env_temp = weather.get_local_temp();
        System.out.println("env_temp: "+env_temp);
        if(env_temp>=25){
            return 1;
        }else if(env_temp<12){
            return 2;
        }else{
            double room_temp = data.acs.get(room).getRoom_temperature();
            //
            if(room_temp >= env_temp){
                return 2;
            }else{
                return 1;
            }
        }
    }

    // give an appropriate temperature setting
    public static double get_setting_temp(double env_temp,String room){
        double room_temp = data.acs.get(room).getRoom_temperature();
        if(env_temp>=30){
            return 18.0;
        }else if(env_temp<=10){
            return 14.0;
        }else{ // winter probably
            //DecimalFormat df = new DecimalFormat("0.0");
            double r = (env_temp+room_temp)/2;
            return Precision.round(r,1);
        }
    }
    /*
    // check current activity
    public static Activity getActivity(){

        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);

            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
            if (activities == null)
                return null;

            for(Object activityRecord : activities.values()){
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
*/





}
