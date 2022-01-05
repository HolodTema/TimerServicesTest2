package com.terabyte.timerservicestest2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String description;
    public int hours, minutes;
    public boolean isActive;

    public Alarm(int hours, int minutes, String description, boolean isActive) {
        this.hours = hours;
        this.minutes = minutes;
        this.description = description;
        this.isActive = isActive;
    }

    public String getTimeInString() {
        String result = "";
        if(String.valueOf(hours).length()==1) {
            result = "0";
        }
        result+=String.valueOf(hours);
        result+=":";
        if(String.valueOf(minutes).length()==1) {
            result += "0";
        }
        result+=String.valueOf(minutes);
        return result;
    }
}
