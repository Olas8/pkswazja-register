package com.example.dziennikazja.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Member.class, Group.class, TrainingSchedule.class, Training.class, Attendance.class}, version = 16, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MemberDao memberDao();

    public abstract GroupDao groupDao();

    public abstract TrainingDao trainingDao();

    public abstract TrainingScheduleDao trainingScheduleDao();

    public abstract AttendanceDao attendanceDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.

                GroupDao groupDao = INSTANCE.groupDao();
                groupDao.deleteAll();

                Group group1 = new Group("Gim3 starsi");
                Group group2 = new Group("Gim3 młodsi");

                //group1.id = 666;
                //group2.id = 2;

                int group1Id = (int) groupDao.insert(group1);
                int group2Id = (int) groupDao.insert(group2);

                groupDao.insert(new Group("Szkoła podstawowa nr 3"));
                groupDao.insert(new Group("SP3 Początkujący"));
                groupDao.insert(new Group("SP3 zaawansowani"));
                groupDao.insert(new Group("Piątkowisko"));
                groupDao.insert(new Group("Seniorstwo"));
                groupDao.insert(new Group("Juniorstwo"));
                groupDao.insert(new Group("BVNDIT"));
                groupDao.insert(new Group("ITZY"));
                groupDao.insert(new Group("Angels N Soul"));
                groupDao.insert(new Group("Girls Generation"));

                MemberDao dao = INSTANCE.memberDao();
                dao.deleteAll();

                Member member = new Member("Aleksander",
                        "Muszyński",
                        "Olas",
                        "1995-04-23",
                        "ul. Pomorska 9, 95-200 Tomaszów Mazowiecki",
                        "123456789",
                        "Aleksander",
                        "Klara",
                        "2012-04-23",
                        "I dan",
                        group1.name);
                dao.insert(member);

                Member member2 = new Member("Grzegorz",
                        "Sokołowski",
                        "Sokół",
                        "1986-04-23",
                        "ul. Pomorska 9, 95-200 Pabianice",
                        "123456789",
                        "",
                        "",
                        "2012-04-23",
                        "1 kup",
                        group1.name);
                dao.insert(member2);

                Member member3 = new Member("Mateusz",
                        "Zielonka",
                        "Grubas",
                        "2008-04-23",
                        "ul. Pomorska 9, 95-200 Pabianice",
                        "123456789",
                        "Bogdandddddddddddddddddddd",
                        "Klaraaaaaaaaaaaaaaaaaaaaaa",
                        "2012-04-23",
                        "2 kup",
                        group2.name);
                dao.insert(member3);

                Member member4 = new Member("Tadeusz",
                        "Gorzołak",
                        "",
                        "2008-04-23",
                        "ul. Pomorska 9, 95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "3 kup",
                        group2.name);
                dao.insert(member4);


                Member member5 = new Member("Piotro",
                        "Kowallo",
                        "Kowal",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200\nPabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "4 kup",
                        group2.name);
                dao.insert(member5);


                Member member6 = new Member("Łukasz",
                        "Kacprzak",
                        "Drewno",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "5 kup",
                        group2.name);
                dao.insert(member6);

                Member member7 = new Member("Hanna",
                        "Kang",
                        "",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "6 kup",
                        group2.name);
                dao.insert(member7);

                Member member8 = new Member("한나",
                        "강",
                        "",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "7 kup",
                        group2.name);
                dao.insert(member8);

                Member member9 = new Member("보라",
                        "김",
                        "",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "8 kup",
                        group2.name);
                dao.insert(member9);

                Member m1 = new Member("Bora",
                        "Kim",
                        "SuA",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "9 kup",
                        group2.name);
                dao.insert(m1);

                Member m2 = new Member("예지",
                        "황",
                        "",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "10 kup",
                        group2.name);
                dao.insert(m2);

                Member m3 = new Member("Yeji",
                        "Hwang",
                        "",
                        "2008-04-23",
                        "ul. Pomorska 9\n95-200 Pabianice",
                        "123456789",
                        "Bogdan",
                        "Klara",
                        "2012-04-23",
                        "brak stopnia",
                        group2.name);
                dao.insert(m3);

                TrainingScheduleDao trainingScheduleDao = INSTANCE.trainingScheduleDao();
                trainingScheduleDao.insert(new TrainingSchedule(group1Id, 1, "18:45", "20:00"));
                trainingScheduleDao.insert(new TrainingSchedule(group1Id, 5, "18:45", "20:00"));
                trainingScheduleDao.insert(new TrainingSchedule(group2Id, 1, "17:30", "18:45"));
                trainingScheduleDao.insert(new TrainingSchedule(group2Id, 3, "18:00", "20:00"));

                AttendanceDao attendanceDao = INSTANCE.attendanceDao();
                attendanceDao.deleteAll();

                //TrainingDao trainingDao = INSTANCE.trainingDao();
                //trainingDao.deleteAll();
            });
        }
    };

}