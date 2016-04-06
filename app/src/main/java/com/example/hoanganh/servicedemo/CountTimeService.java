package com.example.hoanganh.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by HoangAnh on 4/6/2016.
 */
public class CountTimeService extends Service {
    public static boolean isRunnung = true;
    private static final String NOFICATION = "com.example.hoanganh";
    private static final String CURRENT_TIME = "current_time";
    private static final String TIMER = "timer";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, final int startId) {
        super.onStart(intent, startId);

        final long startingTime = intent.getExtras().getLong(CURRENT_TIME);

        Thread triggerService = new Thread(new Runnable() {


            public void run() {
                while (isRunnung) {
                    try {
                        long tics = 0;
                        long currentTime = System.currentTimeMillis();
                        tics = currentTime - startingTime;
                        Intent myFilteredResponse = new Intent(NOFICATION);
                        myFilteredResponse.putExtra(TIMER, String.valueOf(tics));
                        sendBroadcast(myFilteredResponse);
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        triggerService.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunnung = false;
    }
}
