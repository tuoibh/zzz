package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.domain.repo.UserInfoRepository;
import com.example.myapplication.domain.model.user.User;

import javax.inject.Inject;

public class SaveUserInfoUseCase {
    UserInfoRepository userInfoRepository;

    @Inject
    public SaveUserInfoUseCase(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public void saveUserInfo(User user){
        userInfoRepository.saveUserInfo(user);
    }
}
