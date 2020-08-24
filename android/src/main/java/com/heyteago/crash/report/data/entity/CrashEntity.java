package com.heyteago.crash.report.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crash")
public class CrashEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String log;
    @ColumnInfo(name = "is_uploaded")
    private boolean isUploaded = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }
}
