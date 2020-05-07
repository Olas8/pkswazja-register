package com.example.dziennikazja.db;


import androidx.room.TypeConverter;

import org.joda.time.LocalDate;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String localDateToString(LocalDate date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static LocalDate fromString(String value) {
        return value == null ? null : new LocalDate(value);
    }
}