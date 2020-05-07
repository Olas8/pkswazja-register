package com.example.dziennikazja.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.AppDatabase;
import com.example.dziennikazja.db.TrainingSchedule;
import com.example.dziennikazja.db.TrainingScheduleDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrainingScheduleRepository {

    private TrainingScheduleDao trainingScheduleDao;
    private LiveData<List<TrainingSchedule>> allTrainingSchedules;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public TrainingScheduleRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        trainingScheduleDao = db.trainingScheduleDao();
        allTrainingSchedules = trainingScheduleDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<TrainingSchedule>> getAllTrainingSchedules() {
        return allTrainingSchedules;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public long insert(TrainingSchedule trainingSchedule) {
        long newId = -1;
        try {
            newId = AppDatabase.databaseWriteExecutor.submit(() -> trainingScheduleDao.insert(trainingSchedule)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return newId;
    }


    public void update(TrainingSchedule trainingSchedule) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingScheduleDao.update(trainingSchedule);
        });
    }

    public void deleteById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingScheduleDao.deleteById(id);
        });
    }

    public LiveData<List<TrainingSchedule>> getScheduleForDay(int dayOfWeek) {
        return trainingScheduleDao.getScheduleForDay(dayOfWeek);
    }

    public String getGroupName(int id) {
        String groupName = "";
        try {
            groupName = AppDatabase.databaseWriteExecutor.submit(() -> trainingScheduleDao.getGroupName(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return groupName;
    }
}
