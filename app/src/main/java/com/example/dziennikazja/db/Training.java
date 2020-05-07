package com.example.dziennikazja.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Group.class,
        parentColumns = "id",
        childColumns = "id_grupy",
        onDelete = ForeignKey.CASCADE))
public class Training {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_grupy", index = true)
    public int groupId;

    @ColumnInfo(name = "dzien")
    public String day;

    @ColumnInfo(name = "czas_rozpoczecia")
    public String startTime;

    @ColumnInfo(name = "czas_zakonczenia")
    public String endTime;

    public Training(int groupId, String day, String startTime, String endTime) {
        this.groupId = groupId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
