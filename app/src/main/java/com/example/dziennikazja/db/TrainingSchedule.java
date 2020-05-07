package com.example.dziennikazja.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Group.class,
        parentColumns = "id",
        childColumns = "id_grupy",
        onDelete = ForeignKey.CASCADE))
public class TrainingSchedule {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_grupy", index = true)
    public int groupId;

    @ColumnInfo(name = "dzien")
    public int dayOfWeek;

    @ColumnInfo(name = "czas_rozpoczecia")
    public String timeStart;

    @ColumnInfo(name = "czas_zakonczenia")
    public String timeEnd;

    public TrainingSchedule(int groupId, int dayOfWeek, String timeStart, String timeEnd) {
        this.groupId = groupId;
        this.dayOfWeek = dayOfWeek;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
}
