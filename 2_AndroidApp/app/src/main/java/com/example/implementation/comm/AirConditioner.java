package com.example.implementation.comm;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

 /*
  * @author  Qiushi Wang
  *
  * Description:
  * the basic data structure of an air conditioner setting.
  * the toString() function was rewritten, for generating a json string.
  *
  *
  * */

public class AirConditioner {

    private int id;
    private String room;
    private int on_off;                 // on_off on 1, off 0         // send
    private String fan_speed;           // fan fast,medium,slow // send
    private int mode;                   // 1:cooling , 2:warming  // send
    private double ac_temperature;      // temperature set// send
    private double room_temperature;    // room temperature
    private double humidity;
    public AirConditioner() {

    }
    public AirConditioner(int id, String room, int on_off, String fan_speed, int mode, double ac_temperature, double room_temperature, double humidity) {
        this.id = id;
        this.room = room;
        this.on_off = on_off;
        this.fan_speed = fan_speed;
        this.mode = mode;
        this.ac_temperature = ac_temperature;
        this.room_temperature = room_temperature;
        this.humidity = humidity;
    }

    @Override
    public String toString(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",this.id);
        map.put("room",this.room);
        map.put("on_off",this.on_off);
        map.put("fan_speed",this.fan_speed);
        map.put("mode",this.mode);
        map.put("ac_temperature",this.ac_temperature);
        map.put("room_temperature",this.room_temperature);
        map.put("humidity",this.humidity);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getOn_off(Object on_off) {
        return this.on_off;
    }

    public void setOn_off(int on_off) {
        this.on_off = on_off;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getAc_temperature() {
        return ac_temperature;
    }

    public void setAc_temperature(double ac_temperature) {
        this.ac_temperature = ac_temperature;
    }

    public double getRoom_temperature() {
        return room_temperature;
    }

    public void setRoom_temperature(double room_temperature) {
        this.room_temperature = room_temperature;
    }

    public String getFan_speed() {
        return fan_speed;
    }

    public void setFan_speed(String fan_speed) {
        this.fan_speed = fan_speed;
    }

    public int getOn_off() {
        return on_off;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
