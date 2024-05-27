package com.sspu.intelligentlifeassistant.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sspu.intelligentlifeassistant.dao.ScheduleDao;
import com.sspu.intelligentlifeassistant.models.ScheduleItem;

@Database(entities = {ScheduleItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScheduleDao scheduleDao();
}
