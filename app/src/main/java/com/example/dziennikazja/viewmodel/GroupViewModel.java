package com.example.dziennikazja.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.repo.GroupRepository;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {
    private GroupRepository repository;

    private LiveData<List<Group>> allGroups;

    public GroupViewModel(Application application) {
        super(application);
        repository = new GroupRepository(application);
        allGroups = repository.getAllGroups();
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }


    public long insert(Group group) {
        return repository.insert(group);
    }

    public Group getGroupById(int id) {
        return repository.getGroupById(id);
    }

    public void update(Group group) {
        repository.update(group);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<Group> getAllAsList() {
        return repository.getAllAsList();
    }
}
