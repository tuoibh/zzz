package com.example.myapplication.ui.profile;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileViewModel extends ViewModel {
    @Inject
    public ProfileViewModel() {
    }
}
