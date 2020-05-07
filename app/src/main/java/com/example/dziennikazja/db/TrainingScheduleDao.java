package com.example.dziennikazja.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TrainingScheduleDao {
    @Query("SELECT * FROM TrainingSchedule")
    LiveData<List<TrainingSchedule>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(TrainingSchedule trainingSchedule);

    @Delete
    void delete(TrainingSchedule trainingSchedule);

    @Query("DELETE FROM TrainingSchedule")
    void deleteAll();

    @Update
    void update(TrainingSchedule trainingSchedule);

    @Query("DELETE FROM TrainingSchedule WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM TrainingSchedule WHERE dzien = :dayOfWeek")
    LiveData<List<TrainingSchedule>> getScheduleForDay(int dayOfWeek);

    @Query("SELECT `Group`.nazwa from TrainingSchedule INNER JOIN `Group` ON id_grupy=`Group`.id WHERE trainingschedule.id=:id")
    String getGroupName(int id);
}

