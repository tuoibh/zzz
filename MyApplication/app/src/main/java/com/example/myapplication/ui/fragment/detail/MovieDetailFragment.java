package com.example.myapplication.ui.fragment.detail;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.App;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.databinding.FragmentMovieDetailBinding;
import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.service.ReminderMovieReceiver;
import com.example.myapplication.service.ReminderMovieService;
import com.example.myapplication.ui.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MovieDetailFragment extends Fragment {
    private FragmentMovieDetailBinding binding;
    private MainActivity activity;
    MovieDetailViewModel viewModel;
    MovieDetailResponse mMovieDetailResponse;
    MovieDetailFragmentArgs args;
    private Calendar calendar;

    private int year, month, day, hour, minutes;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);
        viewModel.getListMovieFavourite();
        args = MovieDetailFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint({"NotifyDataSetChanged", "InlinedApi"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getMovieDetailRemote(args.getMovieId());
        binding.lnWaiting.setVisibility(View.VISIBLE);
        viewModel.mLdMovieDetail.observe(requireActivity(), movieDetailResponse -> {
            mMovieDetailResponse = movieDetailResponse;
            if(isAdded()){
                binding.lnWaiting.setVisibility(View.GONE);
                viewModel.mLdListMovieRS.observe(requireActivity(), movieResults -> {
                    activity.setBadgeTextFavourite(movieResults.size());
                    binding.imvIsFavourite.setChecked(viewModel.isFavourite(args.getMovieId(), movieResults));
                });
                setUI();
            }
        });
        viewModel.getMovieCastNCrew(args.getMovieId()+"");
        viewModel.mLdCastNCrew.observe(requireActivity(), castNCrewResponse -> {
            CastNCrewAdapter adapter = new CastNCrewAdapter(viewModel.imageLoader, castNCrewResponse, viewModel, (view1, position) -> {
            });
            adapter.notifyDataSetChanged();
            binding.rcvCastNCrew.setAdapter(adapter);
        });
        binding.imvIsFavourite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(binding.imvIsFavourite.isChecked()){
                viewModel.addFavouriteMovie(args.getMovieResult());
            } else {
                viewModel.deleteFavourite(args.getMovieId());
            }
        });
        binding.txtReminderDate.setOnClickListener(v -> openDateDialog());
        binding.txtReminderTime.setOnClickListener(v -> openTimeDialog());
        binding.btnReminderMovie.setOnClickListener(v -> {
            checkPermission(Manifest.permission.POST_NOTIFICATIONS, 1);
            checkPermission(Manifest.permission.FOREGROUND_SERVICE, 1);
        });
    }
    @SuppressLint("NewApi")
    private boolean isPossibleToReminder(String date, String time) {
        if(date.compareTo(viewModel.getCurrentDate())<0){
            Toast.makeText(activity, "Invalid Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if(date.compareTo(viewModel.getCurrentDate())==0){
            if(time.compareTo(viewModel.getCurrentTime()) <= 0){
                Toast.makeText(activity, "Invalid Time", Toast.LENGTH_SHORT).show();
                return false;
            } else return true;
        } else return true;
    }

    @SuppressLint({"SetTextI18n", "NewApi"})
    private void setUI() {
        binding.txtReleaseDateText.setText(mMovieDetailResponse.getReleaseDate());
        binding.txtRatingText.setText(mMovieDetailResponse.getVoteAverage()+ "/10");
        Glide.with(this).load(mMovieDetailResponse.getPosterPath()).into(binding.imvPosterMovie);
        binding.txtOveriewText.setText(mMovieDetailResponse.getOverview());
        binding.txtReminderDate.setText(viewModel.getCurrentDate());
        binding.txtReminderTime.setText(viewModel.getCurrentTime());
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getListMovieFavourite();
        activity.uiToolbarDetail(args.getMovieTitle());
    }
    @SuppressLint("SetTextI18n")
    private void openDateDialog(){
        DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            this.year = year;
            this.month = month;
            this.day = dayOfMonth;
            binding.txtReminderDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
        }, viewModel.calendar.get(Calendar.YEAR),  viewModel.calendar.get(Calendar.MONTH),  viewModel.calendar.get(Calendar.DATE));
        dialog.show();
    }
    private void openTimeDialog(){
        @SuppressLint("SetTextI18n") TimePickerDialog dialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            hour = hourOfDay;
            minutes = minute;
            String _hour = hourOfDay<10? ("0"+hourOfDay) : (hourOfDay+"");
            String _minutes = minute<10? ("0"+minutes) : (minutes+"");
            binding.txtReminderTime.setText(_hour+":"+_minutes);
        }, hour, minutes, true);
        dialog.show();
    }
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[] { permission }, requestCode);
        }
        else {
            if(isPossibleToReminder(binding.txtReminderDate.getText().toString(), binding.txtReminderTime.getText().toString())){
                Reminder reminder = new Reminder(mMovieDetailResponse.getId(),
                        mMovieDetailResponse.getTitle(),
                        mMovieDetailResponse.getPosterPath(),
                        mMovieDetailResponse.getVoteAverage().toString(),
                        binding.txtReminderDate.getText().toString(), binding.txtReminderTime.getText().toString());
                reminder.setAlarm(requireContext());
                viewModel.addReminder(reminder);
                activity.getViewModel().getListReminder();
                Toast.makeText(activity, "Add reminder successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

}