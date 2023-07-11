package com.example.myapplication.ui.fragment.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentAboutBinding;
import com.example.myapplication.ui.activity.MainActivity;

public class AboutFragment extends Fragment {
    private MainActivity activity;
    private FragmentAboutBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.txtAbout.setOnClickListener(v -> {
            NavDirections action = AboutFragmentDirections.actionAboutFragmentToWebViewFragment();
            NavHostFragment.findNavController(requireParentFragment()).navigate(action);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.uiToolbarOtherPage("About");
    }
}
