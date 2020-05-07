package com.example.dziennikazja.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.AppDatabase;
import com.example.dziennikazja.db.Attendance;
import com.example.dziennikazja.db.AttendanceDao;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AttendanceRepository {

    private AttendanceDao attendanceDao;
    private LiveData<List<Attendance>> allAttendances;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public AttendanceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        attendanceDao = db.attendanceDao();
        allAttendances = attendanceDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Attendance>> getAllAttendances() {
        return allAttendances;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Attendance attendance) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            attendanceDao.insert(attendance);
        });
    }

    public void insertAll(List<Attendance> attendances) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            attendanceDao.insertAll(attendances);
        });
    }

    public void update(Attendance attendance) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            attendanceDao.update(attendance);
        });
    }

    public void deleteById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            attendanceDao.deleteById(id);
        });
    }

    public List<Integer> getAttendanceOnDay(LocalDate day) {
        List<Integer> list = new ArrayList<>();
        try {
            list = AppDatabase.databaseWriteExecutor.submit(() -> attendanceDao.getAttendanceOnDay(day)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Attendance> getAttendanceBetweenTwoDates(LocalDate dateFrom, LocalDate dateTo) {
        List<Attendance> list = new ArrayList<>();
        try {
            list = AppDatabase.databaseWriteExecutor.submit(() -> attendanceDao.getAttendanceBetweenTwoDates(dateFrom, dateTo)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
