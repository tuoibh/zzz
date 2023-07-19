package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.domain.repo.UserInfoRepository;
import com.example.myapplication.domain.model.user.User;

import javax.inject.Inject;

public class GetUserInfoUseCase {

    UserInfoRepository userInfoRepository;

    @Inject
    public GetUserInfoUseCase(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public User getUserInfo(){
        return userInfoRepository.getUserInfo();
    }
}
