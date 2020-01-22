package com.epam.task01b;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.Arrays;

public class SortService extends Service {
    private final IBinder binder = new SortBinder();

    public class SortBinder extends Binder {
        SortService getService() {
            return SortService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String sortArray(int[] input) {
        Arrays.sort(input);
        return getResources().getString(R.string.sort_success_result);
    }
}
