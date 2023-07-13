package com.example.myapplication.ui.fragment.profile.editprofile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.databinding.FragmentEditProfileBinding;
import com.example.myapplication.data.repo.user.User;
import com.example.myapplication.ui.activity.MainActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Objects;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    private MainActivity activity;
    private EditProfileViewModel viewModel;
    Uri uri;
    private ProgressDialog progDailog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(EditProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUI();
        binding.btnCancel.setOnClickListener(v -> activity.onBackPressed());
        binding.imvAvatar.setOnClickListener(v -> chooseImageAvatar());
        binding.txtDateOfBirth.setOnClickListener(v -> openDateDialog());
        binding.btnDone.setOnClickListener(v -> {
            progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
            new Handler().postDelayed(() -> {
                String gender = (binding.rgGender.getCheckedRadioButtonId()==binding.rbFemale.getId()) ? AppConfig.Companion.GENDER_F : AppConfig.Companion.GENDER_M;
                User user_update = new User(1,
                        binding.edtName.getText().toString(),
                        binding.edtEmail.getText().toString(),
                        binding.txtDateOfBirth.getText().toString(),
                        gender, uri);
                viewModel.saveUserInfo(user_update);
                progDailog.dismiss(); // auto cancel ProgressDialog
                activity.onBackPressed();
                activity.setUIDrawer();
            }, 1500);
        });
    }

    private void setUI() {
        User currentUser = viewModel.getUserInfo(activity.getPackageName());
        binding.imvAvatar.setImageURI(currentUser.getUri());
        binding.edtName.setText(currentUser.getName());
        binding.edtEmail.setText(currentUser.getEmail());
        binding.txtDateOfBirth.setText(currentUser.getDate_of_birth());
        if(Objects.equals(currentUser.getGender(), AppConfig.Companion.GENDER_F)) binding.rbFemale.setChecked(true);
        else if(Objects.equals(currentUser.getGender(), AppConfig.Companion.GENDER_M)) binding.rbMale.setChecked(true);
        else binding.rgGender.clearCheck();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.settingUIScreenFromDrawer("Edit", true);
    }

    private void chooseImageAvatar(){
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if (data != null) {
                uri = data.getData();
                binding.imvAvatar.setImageURI(uri);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void openDateDialog(){
        DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            binding.txtDateOfBirth.setText(year + "/" + (month+1) + "/" + dayOfMonth);
        }, 2023, 1, 1);
        dialog.show();
    }
}
