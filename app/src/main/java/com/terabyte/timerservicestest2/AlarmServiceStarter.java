package com.terabyte.timerservicestest2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.security.Provider;
import java.util.Calendar;
import java.util.Date;

public class AlarmServiceStarter {
    private static boolean isServiceStarted = false;
    public static void startAlarmService(Context context, int alarmHours, int alarmMinutes, String alarmDescription) {
        //GOVNOCODE IS BEGINNING HERE!!!
        // FIXME: 26.12.2021 govnocode!
        Date currentDate = Calendar.getInstance().getTime();
        long nowMilliseconds = currentDate.getTime();
        long alarmSignalMilliseconds = nowMilliseconds;

        if(alarmHours>currentDate.getHours()) {
            Date alarmSignalDate = (Date) currentDate.clone();
            alarmSignalDate.setHours(alarmHours);
            alarmSignalDate.setMinutes(alarmMinutes);
            alarmSignalDate.setSeconds(0);
            alarmSignalMilliseconds = alarmSignalDate.getTime();
        }
        if(alarmHours<currentDate.getHours()) {
            Date alarmSignalDateMinusOneDay = (Date) currentDate.clone();
            alarmSignalDateMinusOneDay.setHours(alarmHours);
            alarmSignalDateMinusOneDay.setMinutes(alarmMinutes);
            alarmSignalDateMinusOneDay.setSeconds(0);
            alarmSignalMilliseconds = nowMilliseconds+86400000-(nowMilliseconds-alarmSignalDateMinusOneDay.getTime());
        }
        if(alarmHours==currentDate.getHours()) {
            if(alarmMinutes>currentDate.getMinutes()) {
                Date alarmSignalDate = (Date) currentDate.clone();
                alarmSignalDate.setHours(alarmHours);
                alarmSignalDate.setMinutes(alarmMinutes);
                alarmSignalDate.setSeconds(0);
                alarmSignalMilliseconds = alarmSignalDate.getTime();
            }
            if(alarmMinutes<currentDate.getMinutes()) {
                Date alarmSignalDateMinusOneDay = (Date) currentDate.clone();
                alarmSignalDateMinusOneDay.setHours(alarmHours);
                alarmSignalDateMinusOneDay.setMinutes(alarmMinutes);
                alarmSignalDateMinusOneDay.setSeconds(0);
                alarmSignalMilliseconds = nowMilliseconds+86400000-(nowMilliseconds-alarmSignalDateMinusOneDay.getTime());
            }
            if(alarmMinutes==currentDate.getMinutes()) {
                alarmSignalMilliseconds = nowMilliseconds+86400;
            }
        }
        long milliseconds = alarmSignalMilliseconds - nowMilliseconds;

        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(Constant.INTENT_KEY_ALARM_MILLISECONDS, milliseconds);
        intent.putExtra(Constant.INTENT_KEY_ALARM_HOURS, alarmHours);
        intent.putExtra(Constant.INTENT_KEY_ALARM_MINUTES, alarmMinutes);
        intent.putExtra(Constant.INTENT_KEY_ALARM_DESCRIPTION, alarmDescription);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }
        else {
            context.startService(intent);
        }
        //GOVNOCODE IS FINISHING HERE!!!
        long hours = milliseconds/3600000;
        long minutes = (milliseconds%3600000)/60000;
        Toast.makeText(context, context.getResources().getString(R.string.alarm_signal_is_happening_for)+" "+hours+" часов "+minutes+" минут.", Toast.LENGTH_LONG).show();

        isServiceStarted = true;
    }

    public static void stopAlarmService(Context context) {
        if(isServiceStarted) {
            Intent intent = new Intent(context, AlarmService.class);
            context.stopService(intent);
            isServiceStarted = false;
        }
    }
}
