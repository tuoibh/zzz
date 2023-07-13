package com.example.myapplication.domain.repo;

import com.example.myapplication.data.repo.user.User;

public interface UserInfoRepository {
    void saveUserInfo(User user);
    User getUserInfo();
}
