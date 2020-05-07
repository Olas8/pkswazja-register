package com.example.dziennikazja.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"nazwa"}, unique = true)})
public class Group {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nazwa")
    public String name;

    public Group(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Ignore
    public Group() {
    }
}
