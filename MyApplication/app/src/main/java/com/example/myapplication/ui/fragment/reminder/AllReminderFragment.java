package com.example.myapplication.ui.fragment.reminder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentAllReminderBinding;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AllReminderFragment extends Fragment {
    FragmentAllReminderBinding binding;
    MainActivity activity;
    ReminderAdapter adapter;
    ReminderViewModel viewModel;
    List<Reminder> reminderList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(ReminderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllReminderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getListReminder();
        viewModel.mLdListReminder.observe(requireActivity(), reminders -> {
            if(reminders.isEmpty()) binding.txtEmpty.setVisibility(View.VISIBLE);
            else binding.txtEmpty.setVisibility(View.GONE);
            reminderList = reminders;
            if(adapter != null) adapter.setReminderList(reminderList);
        });
        adapter = new ReminderAdapter(reminderList, viewModel.imageLoader, (view1, position) -> showDialog(reminderList.get(position).getMovieId()));
        binding.rcvAllReminder.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showDialog(int id) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Want to delete this reminder?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    viewModel.deleteReminder(id);
                    activity.getViewModel().getListReminder();
                })
                .setNeutralButton("Cancel", (dialog, which) -> Log.d("tbh_", "showDialog: AllReminderFragment")).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.settingUIScreenFromDrawer("All Reminder", false);
    }
}
