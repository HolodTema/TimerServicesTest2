package com.terabyte.timerservicestest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listAlarms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAlarms = findViewById(R.id.listAlarms);
        listAlarms.setItemsCanFocus(false);
        fillListAlarms();

    }

    public void onClickButtonCreateNewAlarm(View view) {
        Intent intent = new Intent(getApplicationContext(), AlarmEditorActivity.class);
        intent.putExtra(Constant.INTENT_KEY_ALARM_EDITOR_MODE, Constant.MODE_CREATING);
        startActivity(intent);
    }

    private void fillListAlarms() {
        class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, List<Alarm>> {
            @Override
            protected List<Alarm> doInBackground(AppDatabase ...db) {
                return db[0].alarmDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Alarm> alarms) {
                super.onPostExecute(alarms);
                AlarmAdapter adapter = new AlarmAdapter(getApplicationContext(), R.layout.list_alarms, alarms);
                listAlarms.setAdapter(adapter);
                listAlarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), AlarmEditorActivity.class);
                        intent.putExtra(Constant.INTENT_KEY_ALARM_EDITOR_MODE, Constant.MODE_MODIFICATION);
                        intent.putExtra(Constant.INTENT_KEY_ALARM_ID, alarms.get((int) id).id); // FIXME: 01.01.2022
                        startActivity(intent);
                    }
                });
            }
        }
        DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
        asyncTask.execute(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase());

    }
}