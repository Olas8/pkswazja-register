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
public interface GroupDao {
    @Query("SELECT * FROM `group`")
    LiveData<List<Group>> getAll();

    @Query("SELECT * FROM `group`")
    List<Group> getAllAsList();

    @Query("SELECT * FROM `group` WHERE nazwa LIKE :name LIMIT 1")
    Group findByName(String name);

    @Query("SELECT * FROM `group` WHERE id = :id")
    Group findGroupById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Group group);

    @Delete
    void delete(Group group);

    @Query("DELETE FROM `group`")
    void deleteAll();

    @Update
    void update(Group group);

    @Query("DELETE FROM `group` WHERE id = :id")
    void deleteById(int id);
}

