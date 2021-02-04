package com.example.myapplication;

import android.app.Activity;

import com.example.implementation.reasoning.weather;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_get_weather_temp(){
        Activity act = new Activity();
        double r = weather.get_local_temp(act);
        System.out.println(r);
    }
}