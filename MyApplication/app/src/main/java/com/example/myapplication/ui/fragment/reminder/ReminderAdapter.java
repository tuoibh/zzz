package com.example.myapplication.ui.fragment.reminder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.core.OnItemClickListener;
import com.example.myapplication.databinding.ItemReminderBinding;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.repo.ImageLoader;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderHolder> {
    List<Reminder> reminderList;
    ImageLoader imageLoader;
    OnItemClickListener listener;

    public ReminderAdapter(List<Reminder> reminderList, ImageLoader imageLoader, OnItemClickListener listener) {
        this.reminderList = reminderList;
        this.imageLoader = imageLoader;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setReminderList(List<Reminder> list){
        this.reminderList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReminderAdapter.ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReminderBinding binding = ItemReminderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReminderHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ReminderHolder holder, int position) {
        imageLoader.loadImage(reminderList.get(position).getPosterUrl(), holder.binding.imvMovieImage);
        holder.binding.txtMovieTitle.setText(reminderList.get(position).getMovieName());
        holder.binding.txtMoviePoint.setText(reminderList.get(position).getVoteAverage()+"/10");
        holder.binding.txtDateTimeReminder.setText(reminderList.get(position).getDateReminder()+ "\t" + reminderList.get(position).getTimeReminder());
        holder.onClickItem(listener, position);
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public static class ReminderHolder extends RecyclerView.ViewHolder {
        ItemReminderBinding binding;
        public ReminderHolder(ItemReminderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void onClickItem(OnItemClickListener listener, int position){
            itemView.setOnClickListener(v -> {
                listener.onItemClick(v, position);
            });
        }
    }
}
