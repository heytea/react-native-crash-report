package com.heyteago.crash.report.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.heyteago.crash.report.data.entity.CrashEntity;

@Dao
public interface CrashDao {
    @Insert
    void insertCrashEntities(CrashEntity... crashEntities);

    @Update
    void updateCrashEntities(CrashEntity... crashEntities);

    @Query("SELECT * FROM crash WHERE is_uploaded = :isUploaded")
    CrashEntity[] findByIsUploaded(boolean isUploaded);
}
