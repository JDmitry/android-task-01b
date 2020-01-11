package com.epam.task01b;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SortService.Responder {
    private SortService.Receiver mReceiver;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTextView = findViewById(R.id.service_message);
        mReceiver = new SortService.Receiver(new Handler());
        mReceiver.setResponder(this);
    }

    public void onSortClick(View v) {
        messageTextView.setText(R.string.sorting);
        int[] array = new Random().ints(100).toArray();
        sortArray(array);
    }

    private void sortArray(int[] input) {
        SortService.startActionSort(this, input, mReceiver);
    }

    @Override
    public void didSort(int[] array) {
        messageTextView.setText(R.string.sort_success_result);
    }
}
