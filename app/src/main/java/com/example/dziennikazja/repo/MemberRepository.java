package com.example.dziennikazja.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.AppDatabase;
import com.example.dziennikazja.db.Member;
import com.example.dziennikazja.db.MemberDao;
import com.example.dziennikazja.db.MemberMinimal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MemberRepository {

    private MemberDao memberDao;
    private LiveData<List<Member>> allMembers;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public MemberRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        memberDao = db.memberDao();
        allMembers = memberDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Member>> getAllMembers() {
        return allMembers;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Member member) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            memberDao.insert(member);
        });
    }

    public Member getMemberById(int id) {
        Member m = new Member("");
        try {
            m = AppDatabase.databaseWriteExecutor.submit(() -> memberDao.findMemberById(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return m;
    }

    public void update(Member member) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            memberDao.update(member);
        });
    }

    public void deleteById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            memberDao.deleteById(id);
        });
    }

    public List<Member> getMembersInGroup(int groupId) {
        List<Member> list = new ArrayList<>();
        try {
            list = AppDatabase.databaseWriteExecutor.submit(() -> memberDao.getMembersInGroup(groupId)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public LiveData<List<MemberMinimal>> getAllMinimal() {
        return memberDao.getAllMinimal();
    }
}
