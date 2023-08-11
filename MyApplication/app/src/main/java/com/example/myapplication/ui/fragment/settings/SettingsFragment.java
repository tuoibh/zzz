package com.example.myapplication.ui.fragment.settings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorKt;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.databinding.FragmentSettingsBinding;
import com.example.myapplication.ui.activity.MainActivity;

import javax.inject.Inject;

public class SettingsFragment extends Fragment {

    private SettingsViewModel viewModel;

    private FragmentSettingsBinding binding;

    int total_point_movie = 100;
    private MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.uiToolbarOtherPage("Settings");
        binding.txtTitleSettingLoading.setText("Loading: "+ activity.getCurrentLoadPage());
        viewModel.mLdMoviePoint.observe(requireActivity(), integer -> {
            binding.txtRateFromText.setText(integer + "");
            binding.seekbarMoviePoint.setProgress((int) (integer*10));
        });
        binding.seekbarMoviePoint.setMax(total_point_movie);
        binding.seekbarMoviePoint.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Thread thread = new Thread(() -> viewModel.updateSettingMoviePoint((float) progress/10f));
                thread.start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.txtYearFromText.setOnClickListener(v -> {
            setYearDialog();
        });
        viewModel.mLdYear.observe(requireActivity(), integer -> {
            binding.txtYearFromText.setText(integer + "");
        });
        filerCLick();
        sortClick();
    }
    private void setYearDialog() {
        try {
            NumberPicker numberPicker = new NumberPicker(requireContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(getString(R.string.year_picker_title));
            numberPicker.setMinValue(1970);
            numberPicker.setMaxValue(2024);
            numberPicker.setWrapSelectorWheel(false);
            builder.setView(numberPicker);
            builder.setPositiveButton("OK", (dialog, which) -> {
                viewModel.updateSettingYear(numberPicker.getValue());
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                //do nth
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filerCLick(){
        binding.txtTitleFilterPopular.setOnClickListener(v -> {
            setting_filter_click((TextView) v);
            updateTopicFilter(AppConfig.Companion.POPULAR);
        });
        binding.txtTitleFilterNowPlaying.setOnClickListener(v -> {
            setting_filter_click((TextView) v);
            updateTopicFilter(AppConfig.Companion.NOW_PLAYING);
        });
        binding.txtTitleFilterUpComing.setOnClickListener(v -> {
            setting_filter_click((TextView) v);
            updateTopicFilter(AppConfig.Companion.UP_COMING);
        });
        binding.txtTitleFilterTopRate.setOnClickListener(v -> {
            setting_filter_click((TextView) v);
            updateTopicFilter(AppConfig.Companion.TOP_RATED);
        });
    }

    private void sortClick(){
        binding.txtTitleSortRating.setOnClickListener(v -> {
            setting_sort_click((TextView) v);
            updateSortKey(AppConfig.Companion.KEY_RATING);
        });
        binding.txtTitleSortReleaseDate.setOnClickListener(v -> {
            setting_sort_click((TextView) v);
            updateSortKey(AppConfig.Companion.KEY_RELEASE_DATE);
        });
    }

    private void updateTopicFilter(String topic){
        viewModel.updateFilterTopic(topic);
    }

    private void updateSortKey(String sortKey){
        viewModel.updateSortKey(sortKey);
    }
    private void setting_filter_click(TextView textView){
        binding.txtTitleFilterPopular.setBackgroundColor(Color.WHITE);
        binding.txtTitleFilterTopRate.setBackgroundColor(Color.WHITE);
        binding.txtTitleFilterUpComing.setBackgroundColor(Color.WHITE);
        binding.txtTitleFilterNowPlaying.setBackgroundColor(Color.WHITE);
        textView.setBackgroundColor(Color.parseColor("#D5D5D5"));
    }

    private void setting_sort_click(TextView textView){
        binding.txtTitleSortRating.setBackgroundColor(Color.WHITE);
        binding.txtTitleSortReleaseDate.setBackgroundColor(Color.WHITE);
        textView.setBackgroundColor(Color.parseColor("#D5D5D5"));
    }

}
