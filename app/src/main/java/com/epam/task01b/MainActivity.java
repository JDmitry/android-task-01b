package com.epam.task01b;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SortService sortService;
    private boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(MainActivity.this, SortService.class);
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

    public void onSortClick(View view) {
        int[] array = new Random().ints(1000).toArray();
        sortArray(array);
    }

    private void sortArray(final int[] input) {
        if (!bound) return;
        sortService.sortArray(input);
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
