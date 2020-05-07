package com.example.dziennikazja.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.TrainingSchedule;
import com.example.dziennikazja.repo.TrainingScheduleRepository;

import java.util.List;

public class TrainingScheduleViewModel extends AndroidViewModel {
    private TrainingScheduleRepository repository;

    private LiveData<List<TrainingSchedule>> allTrainingSchedules;

    public TrainingScheduleViewModel(Application application) {
        super(application);
        repository = new TrainingScheduleRepository(application);
        allTrainingSchedules = repository.getAllTrainingSchedules();
    }

    public LiveData<List<TrainingSchedule>> getAllTrainingSchedules() {
        return allTrainingSchedules;
    }


    public long insert(TrainingSchedule trainingSchedule) {
        return repository.insert(trainingSchedule);
    }

    public void update(TrainingSchedule trainingSchedule) {
        repository.update(trainingSchedule);
    }

    public LiveData<List<TrainingSchedule>> getScheduleForDay(int dayOfWeek) {
        return repository.getScheduleForDay(dayOfWeek);
    }

    public String getGroupName(int id) {
        return repository.getGroupName(id);
    }
}
