package com.terabyte.timerservicestest2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);
    @Update
    void update(Alarm alarm);
    @Delete
    void delete(Alarm alarm);
    @Query("SELECT * FROM alarm WHERE id = :id")
    Alarm getById(long id);
    @Query("SELECT * FROM alarm")
    List<Alarm> getAll();
}
