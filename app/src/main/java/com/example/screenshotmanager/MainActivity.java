package com.example.screenshotmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotmanager.data.ScreenshotEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private static final int SCREEN_CAPTURE_REQUEST_CODE = 1000;
    
    private ScreenshotViewModel viewModel;
    private MediaRecorder mediaRecorder;
    private String currentVoiceNotePath;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ScreenshotViewModel.class);

        // Setup UI components
        FloatingActionButton fabCapture = findViewById(R.id.fab_capture);
        Button btnRecordVoice = findViewById(R.id.btn_record_voice);
        EditText etNotes = findViewById(R.id.et_notes);
        RecyclerView rvScreenshots = findViewById(R.id.rv_screenshots);

        // Setup RecyclerView
        rvScreenshots.setLayoutManager(new LinearLayoutManager(this));
        ScreenshotAdapter adapter = new ScreenshotAdapter();
        rvScreenshots.setAdapter(adapter);

        // Observe screenshots
        viewModel.getAllScreenshots().observe(this, adapter::submitList);

        // Setup click listeners
        fabCapture.setOnClickListener(v -> startScreenCapture());
        btnRecordVoice.setOnClickListener(v -> toggleVoiceRecording());

        // Request permissions
        requestPermissions();
    }

    private void requestPermissions() {
        String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        };

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
                break;
            }
        }
    }

    private void startScreenCapture() {
        MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        startActivityForResult(projectionManager.createScreenCaptureIntent(), SCREEN_CAPTURE_REQUEST_CODE);
    }

    private void toggleVoiceRecording() {
        if (!isRecording) {
            startVoiceRecording();
        } else {
            stopVoiceRecording();
        }
    }

    private void startVoiceRecording() {
        File voiceNotesDir = new File(getExternalFilesDir(null), "VoiceNotes");
        if (!voiceNotesDir.exists()) {
            voiceNotesDir.mkdirs();
        }

        currentVoiceNotePath = new File(voiceNotesDir, "voice_" + System.currentTimeMillis() + ".mp3").getAbsolutePath();
        
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(currentVoiceNotePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to start recording", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopVoiceRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            Toast.makeText(this, "Recording saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREEN_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent serviceIntent = new Intent(this, ScreenshotService.class);
            serviceIntent.putExtra("resultCode", resultCode);
            serviceIntent.putExtra("data", data);
            startService(serviceIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
} 