package com.epam.task01b;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SortService sortService;
    private boolean bound = false;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTextView = findViewById(R.id.service_message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, SortService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    public void onSortClick(View v) {
        int[] array = new Random().ints(100).toArray();
        sortArray(array);
    }

    private void sortArray(final int[] input) {
        if (!bound) return;
        String message = sortService.sortArray(input);
        messageTextView.setText(message);
    }

    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            SortService.SortBinder binder = (SortService.SortBinder) service;
            sortService = binder.getService();
            bound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            bound = false;
        }
    };
}
