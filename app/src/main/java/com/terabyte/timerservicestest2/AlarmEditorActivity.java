package com.terabyte.timerservicestest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AlarmEditorActivity extends AppCompatActivity {
    private int mode;
    private long alarmId;

    private TimePicker timePickerAlarm;
    private EditText editAlarmDescription;
    private Switch switchAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_editor);
        mode = getIntent().getExtras().getInt(Constant.INTENT_KEY_ALARM_EDITOR_MODE);
        if(mode==Constant.MODE_CREATING) {
            Button buttonDelete = findViewById(R.id.buttonDelete);
            buttonDelete.setVisibility(View.GONE);
        }
        if (mode==Constant.MODE_MODIFICATION) {
            alarmId = getIntent().getExtras().getLong(Constant.INTENT_KEY_ALARM_ID);
        }

        timePickerAlarm = findViewById(R.id.timePickerAlarm);
        editAlarmDescription = findViewById(R.id.editAlarmDescription);
        switchAlarm = findViewById(R.id.switchAlarm);

    }

    public void onClickButtonApply(View view) {
        class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, Void> {
            @Override
            protected Void doInBackground(AppDatabase ...db) {
                if(mode==Constant.MODE_MODIFICATION) {
                    Alarm alarm = db[0].alarmDao().getById(alarmId);
                    alarm.hours = timePickerAlarm.getHour();
                    alarm.minutes = timePickerAlarm.getMinute();
                    alarm.description = editAlarmDescription.getText().toString();
                    alarm.isActive = switchAlarm.isChecked();
                    db[0].alarmDao().update(alarm);
                }
                if(mode==Constant.MODE_CREATING) {
                    Alarm alarm = new Alarm(timePickerAlarm.getHour(), timePickerAlarm.getMinute(), editAlarmDescription.getText().toString(), switchAlarm.isChecked());
                    db[0].alarmDao().insert(alarm);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(switchAlarm.isChecked()) {
                    AlarmServiceStarter.startAlarmService(getApplicationContext(), timePickerAlarm.getHour(),timePickerAlarm.getMinute(), editAlarmDescription.getText().toString());
                }
                else {
                    AlarmServiceStarter.stopAlarmService(getApplicationContext());
                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
        DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
        asyncTask.execute(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase());
    }

    public void onClickButtonCancel(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onClickButtonDelete(View view) {
        class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, Void> {
            @Override
            protected Void doInBackground(AppDatabase ...db) {
                if(mode==Constant.MODE_MODIFICATION) {
                    db[0].alarmDao().delete(db[0].alarmDao().getById(alarmId));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(mode==Constant.MODE_MODIFICATION) {
                    AlarmServiceStarter.stopAlarmService(getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        }
        DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
        asyncTask.execute(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase());
    }


}