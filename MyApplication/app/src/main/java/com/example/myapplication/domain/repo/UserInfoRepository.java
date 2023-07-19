package com.example.myapplication.domain.repo;

import com.example.myapplication.domain.model.user.User;

public interface UserInfoRepository {
    void saveUserInfo(User user);
    User getUserInfo();
}
