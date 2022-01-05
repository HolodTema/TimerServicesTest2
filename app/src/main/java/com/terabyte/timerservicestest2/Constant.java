package com.terabyte.timerservicestest2;

import java.util.Calendar;

public class Constant {
    public static final String DATABASE_NAME = "alarmDatabase";
    public static final String INTENT_KEY_ALARM_EDITOR_MODE = "intentKeyAlarmEditorMode";
    public static final String INTENT_KEY_ALARM_ID = "intentKeyAlarmId";
    public static final String INTENT_KEY_ALARM_HOURS = "intentKeyAlarmHours";
    public static final String INTENT_KEY_ALARM_MINUTES = "intentKeyAlarmMinutes";
    public static final String INTENT_KEY_ALARM_DESCRIPTION = "intentKeyAlarmDescription";
    public static final int MODE_CREATING = 0;
    public static final int MODE_MODIFICATION = 1;
    public static final String INTENT_KEY_ALARM_MILLISECONDS = "intentKeyAlarmMilliseconds";
    public static final int SERVICE_ID = 1;
    public static final long MILLISECONDS_INTENT_DEFAULT_VALUE = 10000L;
    public static final int HOURS_INTENT_DEFAULT_VALUE = Calendar.getInstance().getTime().getHours();
    public static final int MINUTES_INTENT_DEFAULT_VALUE = Calendar.getInstance().getTime().getMinutes();
    public static final int NOTIFICATION_PENDING_INTENT_REQUEST_CODE = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "notificationAlarmId";
    public static final String NOTIFICATION_CHANNEL_NAME = "notificationAlarmName";

}
