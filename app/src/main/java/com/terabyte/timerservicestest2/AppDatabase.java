package com.terabyte.timerservicestest2;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Alarm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
}
