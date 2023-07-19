package com.example.myapplication.ui.fragment.profile.editprofile;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.domain.usecase.profile.GetUserInfoUseCase;
import com.example.myapplication.domain.usecase.profile.SaveUserInfoUseCase;
import com.example.myapplication.domain.model.user.User;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditProfileViewModel extends ViewModel {
    private final GetUserInfoUseCase getUserInfoUseCase;
    private final SaveUserInfoUseCase saveUserInfoUseCase;

    @Inject
    public EditProfileViewModel(GetUserInfoUseCase getUserInfoUseCase, SaveUserInfoUseCase saveUserInfoUseCase) {
        this.getUserInfoUseCase = getUserInfoUseCase;
        this.saveUserInfoUseCase = saveUserInfoUseCase;
    }

    public void saveUserInfo(User user){
        saveUserInfoUseCase.saveUserInfo(user);;
    }

    public User getUserInfo(String packageName){
        User userGet = getUserInfoUseCase.getUserInfo();
        if (userGet != null) return userGet;
        return new User(1, "User name", "Email", "YYYY/MM/DD", "None",
                Uri.parse("android.resource://" + packageName + "/" + R.drawable.ic_default_avatar));
    }
}
