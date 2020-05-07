package com.example.dziennikazja.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.Attendance;
import com.example.dziennikazja.repo.AttendanceRepository;

import org.joda.time.LocalDate;

import java.util.List;

public class AttendanceViewModel extends AndroidViewModel {
    private AttendanceRepository repository;

    private LiveData<List<Attendance>> allAttendances;

    public AttendanceViewModel(Application application) {
        super(application);
        repository = new AttendanceRepository(application);
        allAttendances = repository.getAllAttendances();
    }

    public LiveData<List<Attendance>> getAllAttendances() {
        return allAttendances;
    }


    public void insert(Attendance attendance) {
        repository.insert(attendance);
    }

    public void update(Attendance attendance) {
        repository.update(attendance);
    }

    public void insertAll(List<Attendance> attendances) {
        repository.insertAll(attendances);
    }

    public List<Integer> getAttendanceOnDay(LocalDate day) {
        return repository.getAttendanceOnDay(day);
    }

    public List<Attendance> getAttendanceBetweenTwoDates(LocalDate dateFrom, LocalDate dateTo) {
        return repository.getAttendanceBetweenTwoDates(dateFrom, dateTo);
    }
}
