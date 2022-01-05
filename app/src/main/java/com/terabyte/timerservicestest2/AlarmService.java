package com.terabyte.timerservicestest2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;


public class AlarmService extends Service {
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //i don't know why but method onStartCommand just work again after alarm timer with null value in intent variable. And so, we have to check on null
        //so, maybe it can happen because we have start sticky mode, but i will not delete that code rows
        long milliseconds = intent==null ? Constant.MILLISECONDS_INTENT_DEFAULT_VALUE : intent.getLongExtra(Constant.INTENT_KEY_ALARM_MILLISECONDS, Constant.MILLISECONDS_INTENT_DEFAULT_VALUE);
        int hours = intent==null ? Constant.HOURS_INTENT_DEFAULT_VALUE : intent.getIntExtra(Constant.INTENT_KEY_ALARM_HOURS, Constant.HOURS_INTENT_DEFAULT_VALUE);
        int minutes = intent==null ? Constant.MINUTES_INTENT_DEFAULT_VALUE : intent.getIntExtra(Constant.INTENT_KEY_ALARM_MINUTES, Constant.MINUTES_INTENT_DEFAULT_VALUE);
        String description = intent==null ? "" : intent.getStringExtra(Constant.INTENT_KEY_ALARM_DESCRIPTION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { //for API 26 or higher we have to create a special notification channel
            NotificationChannel notificationChannel = new NotificationChannel(Constant.NOTIFICATION_CHANNEL_ID, Constant.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                Constant.NOTIFICATION_PENDING_INTENT_REQUEST_CODE, notificationIntent,
                PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Constant.NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(getResources().getString(R.string.notification_title) + " " + hours+" часов "+minutes+" минут.");
        builder.setContentText(getResources().getString(R.string.notification_text));
        builder.setTicker(getResources().getString(R.string.notification_ticker));
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();
        startForeground(Constant.SERVICE_ID, notification);

        class AlarmTimerThread extends Thread {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent signalActivityIntent = new Intent(getApplicationContext(), AlarmSignalActivity.class);
                signalActivityIntent.putExtra(Constant.INTENT_KEY_ALARM_HOURS, hours);
                signalActivityIntent.putExtra(Constant.INTENT_KEY_ALARM_MINUTES, minutes);
                signalActivityIntent.putExtra(Constant.INTENT_KEY_ALARM_DESCRIPTION, description);
                signalActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // we do this to start activity in other context
                startActivity(signalActivityIntent);
                stopForeground(true);
                stopSelf(startId);
            }
        }
        AlarmTimerThread timerThread = new AlarmTimerThread();
        timerThread.start();
        return START_REDELIVER_INTENT;
    }

}