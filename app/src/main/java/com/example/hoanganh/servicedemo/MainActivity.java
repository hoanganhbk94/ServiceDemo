package com.example.hoanganh.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String NOFICATION = "com.example.hoanganh";
    private static final String CURRENT_TIME = "current_time";
    private static final String TIMER = "timer";
    private TextView txtTime;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTime = (TextView) findViewById(R.id.txtTime);
        registerReceiver(receiver, new IntentFilter(NOFICATION));
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Get data from Service and Update UI
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String text = bundle.getString(TIMER);
                txtTime.setText(text);
            }
        }
    };

    public void startCount(View v) {
        long startingTime = System.currentTimeMillis();
        intent = new Intent(this, CountTimeService.class);
        intent.putExtra(CURRENT_TIME, startingTime);
        Log.e("startingTime", String.valueOf(startingTime));
        startService(intent);
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
    }

    public void stopCount(View v) {
        if (intent != null)
            stopService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null)
            unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (intent != null)
            stopService(intent);
    }
}
