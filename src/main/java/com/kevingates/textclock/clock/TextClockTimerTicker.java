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

public class TextClockTimerTicker extends TimerTask {
    private String name;

    public TextClockTimerTicker(String n){
        this.name=n;
    }

    @Override
    public void run()  {
        //String pattern = "yyyy-MM-dd HH:mm:ss";
        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        System.out.println(Thread.currentThread().getName()+" "+name+": "+ simpleDateFormat.format(new Date()));

        try {
            String cmd = "curl -x socks5h://localhost:8888 https://api.bitfinex.com/v1/pubticker/btcusd";//ethusd
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(cmd);
            pr.waitFor();

            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line=buf.readLine())!=null) {
                json.append(line);
            }

            JSONParser parser = new JSONParser();
            Object jsonDataOject = parser.parse(json.toString());
            JSONObject tickerJsonObject = (JSONObject)jsonDataOject;

            String price = tickerJsonObject.get("last_price").toString();
            System.out.println(price);

        } catch(IOException e) {
            System.out.println(e);
        }catch(InterruptedException e) {
            System.out.println(e);
        }catch(ParseException e) {
            System.out.println(e);
        }

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
