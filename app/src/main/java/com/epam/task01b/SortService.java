package com.epam.task01b;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.Arrays;

public class SortService extends IntentService {
    private static final String ACTION_SORT = "action.SORT";
    private static final String EXTRA_INPUT = "extra.INPUT";
    private static final String EXTRA_RECEIVER = "extra.RECEIVER";
    private static final String NAME = "SortService";

    public SortService() {
        super(NAME);
    }

    public static void startActionSort(Context context, int[] input, ResultReceiver receiver) {
        Intent intent = new Intent(context, SortService.class);
        intent.setAction(ACTION_SORT);
        intent.putExtra(EXTRA_INPUT, input);
        intent.putExtra(EXTRA_RECEIVER, receiver);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SORT.equals(action)) {
                final int[] input = intent.getIntArrayExtra(EXTRA_INPUT);
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RECEIVER);
                if (receiver != null) {
                    handleActionSort(input, receiver);
                }
            }
        }
    }

    private void handleActionSort(int[] input, ResultReceiver receiver) {
        Arrays.sort(input);
        Bundle data = new Bundle();
        data.putIntArray(Receiver.Constants.KEY_ARRAY, input);
        receiver.send(Receiver.Constants.CODE_SUCCESS, data);
    }

    public interface Responder {
        void onSorted(int[] array);
    }

    public static class Receiver extends ResultReceiver {
        private Responder responder;

        Receiver(Handler handler) {
            super(handler);
        }

        void setReceiver(Responder responder) {
            this.responder = responder;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode != Constants.CODE_SUCCESS || responder == null) return;
            int[] array = resultData.getIntArray(Constants.KEY_ARRAY);
            responder.onSorted(array);
        }

        static class Constants {
            private static final int CODE_SUCCESS = 0;
            private static final String KEY_ARRAY = "array";
        }
    }
}
