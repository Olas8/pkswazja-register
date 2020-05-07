package com.example.dziennikazja.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.joda.time.LocalDate;

import java.util.List;

@Dao
public interface AttendanceDao {
    @Query("SELECT * FROM Attendance")
    LiveData<List<Attendance>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Attendance attendance);

    @Insert
    void insertAll(List<Attendance> attendances);

    @Delete
    void delete(Attendance attendance);

    @Query("DELETE FROM Attendance")
    void deleteAll();

    @Update
    void update(Attendance attendance);

    @Query("DELETE FROM Attendance WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT id_uczestnika FROM Attendance WHERE data = :day AND obecny = 1")
    List<Integer> getAttendanceOnDay(LocalDate day);

    @Query("SELECT * FROM Attendance WHERE data BETWEEN :dateFrom AND :dateTo")
    List<Attendance> getAttendanceBetweenTwoDates(LocalDate dateFrom, LocalDate dateTo);
}

