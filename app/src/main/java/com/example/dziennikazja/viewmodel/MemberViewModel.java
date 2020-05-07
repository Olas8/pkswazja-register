package com.example.dziennikazja.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.db.Member;
import com.example.dziennikazja.db.MemberMinimal;
import com.example.dziennikazja.repo.GroupRepository;
import com.example.dziennikazja.repo.MemberRepository;

import java.util.ArrayList;
import java.util.List;

public class MemberViewModel extends AndroidViewModel {
    private MemberRepository memberRepo;
    private GroupRepository groupRepo;

    private LiveData<List<Member>> mAllMembers;

    public MemberViewModel(Application application) {
        super(application);
        memberRepo = new MemberRepository(application);
        groupRepo = new GroupRepository(application);
        mAllMembers = memberRepo.getAllMembers();
    }

    public LiveData<List<Member>> getAllMembers() {
        return mAllMembers;
    }

    public void insert(Member member) {
        memberRepo.insert(member);
    }

    public Member getMemberById(int id) {
        return memberRepo.getMemberById(id);
    }

    public void update(Member member) {
        memberRepo.update(member);
    }

    public void deleteById(int id) {
        memberRepo.deleteById(id);
    }

    public List<Member> getMembersInGroup(int groupId) {
        return memberRepo.getMembersInGroup(groupId);
    }

    public ArrayList<Group> getGroups() {
        return groupRepo.getAllAsList();
    }

    public LiveData<List<MemberMinimal>> getAllMembersMinimal() {
        return memberRepo.getAllMinimal();
    }
}
