package com.heyteago.crash.report.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.heyteago.crash.report.data.dao.CrashDao;
import com.heyteago.crash.report.data.entity.CrashEntity;

@Database(entities = {CrashEntity.class}, version = 1, exportSchema = false)
public abstract class CrashDB extends RoomDatabase {
    private static CrashDB instance;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (CrashDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), CrashDB.class, "CrashDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
    }

    public static CrashDB getDb(Context context) {
        init(context.getApplicationContext());
        return instance;
    }

    public abstract CrashDao crashDao();

}
