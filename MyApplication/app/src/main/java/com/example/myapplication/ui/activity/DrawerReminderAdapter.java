package com.example.myapplication.ui.activity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemReminderDrawerBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.model.reminder.Reminder;

import java.util.List;

public class DrawerReminderAdapter extends RecyclerView.Adapter<DrawerReminderAdapter.ReminderHolder> {

    List<Reminder> reminderList;

    public DrawerReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLdListMovie(List<Reminder> reminderList) {
        this.reminderList = reminderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DrawerReminderAdapter.ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReminderDrawerBinding binding = ItemReminderDrawerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReminderHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DrawerReminderAdapter.ReminderHolder holder, int position) {
        holder.binding.txtMovieTitle.setText(reminderList.get(position).getMovieName());
        holder.binding.txtPoint.setText(reminderList.get(position).getVoteAverage()+"/10");
        holder.binding.txtDateTimeReminder.setText(reminderList.get(position).getDateReminder()+"\t"+reminderList.get(position).getTimeReminder());
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public static class ReminderHolder extends RecyclerView.ViewHolder {
        ItemReminderDrawerBinding binding;
        public ReminderHolder(ItemReminderDrawerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
