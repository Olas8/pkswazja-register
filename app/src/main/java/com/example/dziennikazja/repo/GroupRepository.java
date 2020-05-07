package com.example.dziennikazja.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.AppDatabase;
import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.db.GroupDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GroupRepository {

    private GroupDao groupDao;
    private LiveData<List<Group>> allGroups;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public GroupRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        groupDao = db.groupDao();
        allGroups = groupDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public long insert(Group group) {
        long newId = -1;
        try {
            newId = AppDatabase.databaseWriteExecutor.submit(() -> groupDao.insert(group)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return newId;
    }

    public Group getGroupById(int id) {
        Group g = new Group("");
        try {
            g = AppDatabase.databaseWriteExecutor.submit(() -> groupDao.findGroupById(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return g;
    }

    public void update(Group group) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.update(group);
        });
    }

    public void deleteById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.deleteById(id);
        });
    }

    public void findByName(String name) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.findByName(name);
        });
    }

    public ArrayList<Group> getAllAsList() {
        List<Group> g = new ArrayList<>();
        try {
            g = AppDatabase.databaseWriteExecutor.submit(() -> groupDao.getAllAsList()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(g);
    }
}
