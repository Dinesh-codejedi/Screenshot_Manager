package com.example.screenshotmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotmanager.data.ScreenshotEntity;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScreenshotAdapter extends ListAdapter<ScreenshotEntity, ScreenshotAdapter.ScreenshotViewHolder> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public ScreenshotAdapter() {
        super(new DiffUtil.ItemCallback<ScreenshotEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull ScreenshotEntity oldItem, @NonNull ScreenshotEntity newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ScreenshotEntity oldItem, @NonNull ScreenshotEntity newItem) {
                return oldItem.getImagePath().equals(newItem.getImagePath()) &&
                        oldItem.getNotes().equals(newItem.getNotes()) &&
                        oldItem.getVoiceNotePath().equals(newItem.getVoiceNotePath()) &&
                        oldItem.getReminderTime() == newItem.getReminderTime();
            }
        });
    }

    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_screenshot, parent, false);
        return new ScreenshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        ScreenshotEntity screenshot = getItem(position);
        holder.bind(screenshot);
    }

    static class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView notesText;
        private final TextView timestampText;
        private final ImageView voiceNoteIcon;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_screenshot);
            notesText = itemView.findViewById(R.id.tv_notes);
            timestampText = itemView.findViewById(R.id.tv_timestamp);
            voiceNoteIcon = itemView.findViewById(R.id.iv_voice_note);
        }

        public void bind(ScreenshotEntity screenshot) {
            Glide.with(itemView.getContext())
                    .load(screenshot.getImagePath())
                    .into(imageView);

            notesText.setText(screenshot.getNotes());
            timestampText.setText(dateFormat.format(new Date(screenshot.getTimestamp())));
            
            voiceNoteIcon.setVisibility(screenshot.getVoiceNotePath() != null && !screenshot.getVoiceNotePath().isEmpty() 
 