package com.example.dziennikazja.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.joda.time.LocalDate;

@Entity(foreignKeys = {
        @ForeignKey(entity = Member.class,
                parentColumns = "id",
                childColumns = "id_uczestnika",
                onDelete = ForeignKey.CASCADE)})
public class Attendance {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_uczestnika", index = true)
    public int memberId;

    @TypeConverters({Converters.class})
    @ColumnInfo(name = "data")
    public LocalDate date;

    @ColumnInfo(name = "obecny")
    public Boolean present;

    public Attendance(int memberId, LocalDate date, boolean present) {
        this.memberId = memberId;
        this.date = date;
        this.present = present;
    }
}
