package com.terabyte.timerservicestest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AlarmSignalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_signal);
        TextView textAlarmSignalHeader = findViewById(R.id.textAlarmSignalHeader);
        TextView textAlarmSignalDescription = findViewById(R.id.textAlarmSignalDescription);
        textAlarmSignalHeader.setText(getIntent().getExtras().getString(Constant.INTENT_KEY_ALARM_HOURS)+" : "+getIntent().getExtras().getString(Constant.INTENT_KEY_ALARM_MINUTES));
        textAlarmSignalDescription.setText(getIntent().getExtras().getString(Constant.INTENT_KEY_ALARM_DESCRIPTION));

    }

    public void onClickButtonStop(View view) {
        finish();
    }
}