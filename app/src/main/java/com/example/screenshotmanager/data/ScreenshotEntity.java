package com.example.screenshotmanager.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "screenshots")
public class ScreenshotEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String imagePath;
    private String voiceNotePath;
    private String notes;
    private long reminderTime;
    private long timestamp;

    public ScreenshotEntity(String imagePath, String voiceNotePath, String notes, long reminderTime) {
        this.imagePath = imagePath;
        this.voiceNotePath = voiceNotePath;
        this.notes = notes;
        this.reminderTime = reminderTime;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getVoiceNotePath() { return voiceNotePath; }
    public void setVoiceNotePath(String voiceNotePath) { this.voiceNotePath = voiceNotePath; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public long getReminderTime() { return reminderTime; }
    public void setReminderTime(long reminderTime) { this.reminderTime = reminderTime; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
} 