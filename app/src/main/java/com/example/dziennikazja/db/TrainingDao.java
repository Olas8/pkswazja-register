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
public interface TrainingDao {
    @Query("SELECT * FROM training")
    LiveData<List<Training>> getAll();

    @Query("SELECT * FROM training WHERE id = :id")
    Training findTrainingById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Training training);

    @Delete
    void delete(Training training);

    @Query("DELETE FROM training")
    void deleteAll();

    @Update
    void update(Training training);

    @Query("DELETE FROM training WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT `Group`.nazwa from training INNER JOIN `Group` ON id_grupy=`Group`.id WHERE training.id=:id")
    String getGroupName(int id);

    @Query("SELECT * FROM training WHERE dzien = :nameOfDay")
    LiveData<List<Training>> getTrainingsOnDay(String nameOfDay);
}

