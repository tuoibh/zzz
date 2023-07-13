package com.example.myapplication.domain.usecase;

import com.example.myapplication.domain.repo.UserInfoRepository;
import com.example.myapplication.data.repo.user.User;

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
