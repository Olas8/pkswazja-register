package com.example.dziennikazja.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.AppDatabase;
import com.example.dziennikazja.db.Training;
import com.example.dziennikazja.db.TrainingDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrainingRepository {

    private TrainingDao trainingDao;
    private LiveData<List<Training>> allTrainings;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public TrainingRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        trainingDao = db.trainingDao();
        allTrainings = trainingDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Training>> getAllTrainings() {
        return allTrainings;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Training training) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingDao.insert(training);
        });
    }

    public Training getTrainingById(int id) {
        Training t = new Training(-1, null, null, null);
        try {
            t = AppDatabase.databaseWriteExecutor.submit(() -> trainingDao.findTrainingById(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return t;
    }

    public void update(Training training) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingDao.update(training);
        });
    }

    public void deleteById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingDao.deleteById(id);
        });
    }

    public String getGroupName(int id) {
        String groupName = "";
        try {
            groupName = AppDatabase.databaseWriteExecutor.submit(() -> trainingDao.getGroupName(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return groupName;
    }

    public LiveData<List<Training>> getTrainingsOnDay(String nameOfDay) {
        return trainingDao.getTrainingsOnDay(nameOfDay);
    }
}
