package com.example.dziennikazja.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MemberDao {
    @Query("SELECT * FROM member")
    LiveData<List<Member>> getAll();

    @Query("SELECT * FROM member WHERE id = :id")
    Member findMemberById(int id);

    @Query("SELECT * FROM member WHERE imie LIKE :first AND " +
            "nazwisko LIKE :last LIMIT 1")
    Member findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Member member);

    @Insert
    void insertAll(Member... members);

    @Delete
    void delete(Member member);

    @Query("DELETE FROM member")
    void deleteAll();

    @Update
    void update(Member member);

    @Query("DELETE FROM member WHERE id = :id")
    void deleteById(int id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    //@Query("SELECT * FROM member INNER JOIN `Group` ON id_grupy=`Group`.id WHERE id_grupy=:groupId")
    @Query("SELECT * FROM member WHERE id_grupy=:groupId")
    List<Member> getMembersInGroup(int groupId);

    @Query("SELECT id, imie as firstName, nazwisko as lastName, pseudonim as pseudonym, nazwa_grupy as groupName, stopien as tkdGrade FROM member")
    LiveData<List<MemberMinimal>> getAllMinimal();

}

