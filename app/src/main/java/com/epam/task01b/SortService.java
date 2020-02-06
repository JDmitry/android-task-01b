package com.epam.task01b;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.widget.Toast;
import java.util.Arrays;

public class SortService extends Service {
    private Handler handler = new Handler();
    private final IBinder binder = new SortBinder();

    class SortBinder extends Binder {
        SortService getService() {
            return SortService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void sortArray(final int[] input) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Arrays.sort(input);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.sort_success_result),Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,400);
                        toast.show();
                    }
                });
            }
        });
        thread.start();
    }
}
