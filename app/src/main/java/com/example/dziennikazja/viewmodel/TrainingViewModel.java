package com.example.dziennikazja.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.Training;
import com.example.dziennikazja.repo.TrainingRepository;

import java.util.List;

public class TrainingViewModel extends AndroidViewModel {
    private TrainingRepository repository;

    private LiveData<List<Training>> allTrainings;

    public TrainingViewModel(Application application) {
        super(application);
        repository = new TrainingRepository(application);
        allTrainings = repository.getAllTrainings();
    }

    public LiveData<List<Training>> getAllTrainings() {
        return allTrainings;
    }


    public void insert(Training training) {
        repository.insert(training);
    }

    public Training getTrainingById(int id) {
        return repository.getTrainingById(id);
    }

    public void update(Training training) {
        repository.update(training);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public String getGroupName(int id) {
        return repository.getGroupName(id);
    }

    public LiveData<List<Training>> getTrainingsOnDay(int dayOfWeek) {
        String nameOfDay = "";
        switch (dayOfWeek) {
            case 1:
                nameOfDay = "Niedziela";
                break;
            case 2:
                nameOfDay = "Poniedziałek";
                break;
            case 3:
                nameOfDay = "Wtorek";
                break;
            case 4:
                nameOfDay = "Środa";
                break;
            case 5:
                nameOfDay = "Czwartek";
                break;
            case 6:
                nameOfDay = "Piątek";
                break;
            case 7:
                nameOfDay = "Sobota";
                break;
        }
        return repository.getTrainingsOnDay(nameOfDay);
    }
}
