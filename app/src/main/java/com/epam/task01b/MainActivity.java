package com.epam.task01b;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SortService.Responder {
    private SortService.Receiver receiver;
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
        receiver = new SortService.Receiver(new Handler());
        receiver.setReceiver(this);
    }

    public void onSortClick(View v) {
        int[] array = new Random().ints(100).toArray();
        sortArray(array);
    }

    private void sortArray(int[] input) {
        SortService.startActionSort(this, input, receiver);
    }

    @Override
    public void onSorted(int[] array) {
        messageTextView.setText(R.string.sort_success_result);
    }

    @Override
    protected void onPause() {
        super.onPause();
        receiver.setReceiver(null);
    }
}
