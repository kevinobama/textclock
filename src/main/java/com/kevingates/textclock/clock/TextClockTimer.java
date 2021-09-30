package com.kevingates.textclock.clock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TextClockTimer extends TimerTask {
    private String name;

    public TextClockTimer(String n){
        this.name=n;
    }

    @Override
    public void run()  {
        //String pattern = "yyyy-MM-dd HH:mm:ss";
        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        System.out.println(Thread.currentThread().getName()+" "+name+": "+ simpleDateFormat.format(new Date()));

        if("clock".equalsIgnoreCase(name)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}