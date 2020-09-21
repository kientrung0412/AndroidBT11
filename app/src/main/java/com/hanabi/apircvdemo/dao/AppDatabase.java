package com.hanabi.apircvdemo.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hanabi.apircvdemo.models.News;

@Database(entities = News.class, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase database;

    public static AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, AppDatabase.class, "db.news")
                    .allowMainThreadQueries()
                    .build();
        }

        return database;
    }

    public abstract NewsDao newsDao();
}
