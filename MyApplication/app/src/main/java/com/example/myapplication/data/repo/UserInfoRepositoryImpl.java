package com.example.myapplication.data.repo;

import com.example.myapplication.data.local.dao.UserDao;
import com.example.myapplication.data.model.user.Users;
import com.example.myapplication.domain.repo.UserInfoRepository;
import com.example.myapplication.domain.model.user.User;

import org.modelmapper.ModelMapper;

import javax.inject.Inject;

public class UserInfoRepositoryImpl implements UserInfoRepository {

    UserDao userDao;

    @Inject
    public UserInfoRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUserInfo(User user) {
        ModelMapper modelMapper = new ModelMapper();
        userDao.saveInfo(modelMapper.map(user, Users.class));
    }

    @Override
    public User getUserInfo() {
        ModelMapper modelMapper = new ModelMapper();
        Users users = userDao.getUserInfo();
        if(users != null) return modelMapper.map(userDao.getUserInfo(), User.class);
        return null;
    }
}
