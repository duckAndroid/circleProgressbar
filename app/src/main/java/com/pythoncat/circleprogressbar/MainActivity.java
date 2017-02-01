package com.pythoncat.circleprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pythoncat.circleprogressbarview.CircleProgressBar;

public class MainActivity extends AppCompatActivity {
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        findViewById(R.id.activity_main)
        final CircleProgressBar cb = (CircleProgressBar) findViewById(R.id.circle_progressBar);

        findViewById(R.id.btn_start)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cb.setProgress(index);
                        index += 5;
                    }
                });
    }
}
