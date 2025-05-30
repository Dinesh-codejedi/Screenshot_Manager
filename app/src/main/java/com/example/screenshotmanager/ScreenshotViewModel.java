package com.example.screenshotmanager;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.screenshotmanager.data.AppDatabase;
import com.example.screenshotmanager.data.ScreenshotEntity;
import com.example.screenshotmanager.data.ScreenshotDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScreenshotViewModel extends AndroidViewModel {
    private final ScreenshotDao screenshotDao;
    private final LiveData<List<ScreenshotEntity>> allScreenshots;
    private final ExecutorService executorService;

    public ScreenshotViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        screenshotDao = db.screenshotDao();
        allScreenshots = screenshotDao.getAllScreenshots();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ScreenshotEntity>> getAllScreenshots() {
        return allScreenshots;
    }

    public void insert(ScreenshotEntity screenshot) {
        executorService.execute(() -> screenshotDao.insert(screenshot));
    }

    public void update(ScreenshotEntity screenshot) {
        executorService.execute(() -> screenshotDao.update(screenshot));
    }

    public void delete(ScreenshotEntity screenshot) {
        executorService.execute(() -> screenshotDao.delete(screenshot));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
} 