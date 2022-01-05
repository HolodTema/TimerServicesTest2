package com.terabyte.timerservicestest2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    private LayoutInflater inflater;
    private int layout;
    private List<Alarm> alarms;
    Context context;

    public AlarmAdapter(Context context, int layout, List<Alarm> alarms) {
        super(context, layout, alarms);
        this.context = context;
        this.alarms = alarms;
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView textAlarmTime = view.findViewById(R.id.textAlarmTime);
        TextView textAlarmShortDescription = view.findViewById(R.id.textAlarmShortDescription);
        Switch switchAlarm = view.findViewById(R.id.switchAlarm);

        Alarm alarm = alarms.get(position);

        textAlarmTime.setText(alarm.getTimeInString());
        if(alarm.description.length()<25) {
            textAlarmShortDescription.setText(alarm.description);
        }
        else {
            textAlarmShortDescription.setText(alarm.description.substring(0, 21)+"...");
        }
        switchAlarm.setChecked(alarm.isActive);
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, Void> {
                        @Override
                        protected Void doInBackground(AppDatabase ...db) {
                            alarm.isActive = b;
                            db[0].alarmDao().update(alarm);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            super.onPostExecute(unused);
                            if(b) {
                                AlarmServiceStarter.startAlarmService(context, alarm.hours, alarm.minutes, alarm.description);
                            }
                            else {
                                AlarmServiceStarter.stopAlarmService(context);
                            }
                        }
                    }
                    DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
                    asyncTask.execute(DatabaseClient.getInstance(context).getAppDatabase());

                }

        });
        return view;
    }
}
