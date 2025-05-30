package com.example.screenshotmanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScreenshotDao {
    @Insert
    long insert(ScreenshotEntity screenshot);

    @Update
    void update(ScreenshotEntity screenshot);

    @Delete
    void delete(ScreenshotEntity screenshot);

    @Query("SELECT * FROM screenshots ORDER BY timestamp DESC")
    LiveData<List<ScreenshotEntity>> getAllScreenshots();

    @Query("SELECT * FROM screenshots WHERE reminderTime > :currentTime ORDER BY reminderTime ASC")
    LiveData<List<ScreenshotEntity>> getUpcomingReminders(long currentTime);
} 