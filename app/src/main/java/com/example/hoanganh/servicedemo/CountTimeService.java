package com.example.hoanganh.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by HoangAnh on 4/6/2016.
 */
public class CountTimeService extends Service {
    public static boolean isRunnung;
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        final long startingTime = intent.getExtras().getLong(CURRENT_TIME);
        isRunnung = true;

        Thread triggerService = new Thread(new Runnable() {


            public void run() {
                while (isRunnung) {
                    try {
                        long currentTime = System.currentTimeMillis();
                        long tics = currentTime - startingTime;
                        Intent myFilteredResponse = new Intent(NOFICATION);
                        myFilteredResponse.putExtra(TIMER, String.valueOf(tics / 1000));
                        Log.e("AAA", String.valueOf(tics));
                        sendBroadcast(myFilteredResponse);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        triggerService.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunnung = false;
    }
}
