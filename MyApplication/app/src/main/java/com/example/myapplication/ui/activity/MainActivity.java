package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.data.repo.user.User;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.domain.model.Topic;
import com.example.myapplication.ui.adapter.AppPagerAdapter;
import com.example.myapplication.ui.fragment.about.AboutFragment;
import com.example.myapplication.ui.fragment.favourite.FavouriteMoviesFragment;
import com.example.myapplication.ui.fragment.home.HomeMoviesFragment;
import com.example.myapplication.ui.fragment.profile.editprofile.EditProfileFragment;
import com.example.myapplication.ui.fragment.settings.SettingsFragment;
import com.example.myapplication.ui.fragment.settings.SettingsViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ActivityMainBinding binding;
    private NavController navController;
    private Toolbar toolbar;
    public MainViewModel getViewModel() {
        return viewModel;
    }
    private MainViewModel viewModel;
    private ArrayAdapter<Topic> adapter;
    private List<Fragment> listFragment = new ArrayList<>();

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        FragmentManager fragmentManager = binding.viewPager.get();
//        setViewPager();
        //nav controller

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.createListTopic();
        setSpinner();
        viewModel.getTopic();
        binding.imgBack.setOnClickListener(v -> onBackPressed());

        binding.imgMenuMain.setOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
        drawerClick();

        setUIDrawer();
    }

    private void setSpinner() {
        binding.spinnerTopic.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter<>(this, R.layout.item_spinner, viewModel.listTopic);
        adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        binding.spinnerTopic.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        viewModel.updateTopic(viewModel.listTopic.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void uiToolBarHome(){
        binding.imgBack.setVisibility(View.GONE);
        binding.txtTitleScreen.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.VISIBLE);
        binding.spinnerTopic.setVisibility(View.VISIBLE);
        binding.imvGridToolbar.setVisibility(View.VISIBLE);
        binding.imvMoreToolbar.setVisibility(View.VISIBLE);
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void uiToolbarOtherPage(String title){
        binding.imgBack.setVisibility(View.GONE);
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imvMoreToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(title);
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void uiToolbarDetail(String movieTitle){
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imvMoreToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.GONE);
        binding.imgBack.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(movieTitle);
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setUISpinner(String topicKey){
        for(Topic item: viewModel.listTopic){
            if(item.key.equals(topicKey)){
                binding.spinnerTopic.setSelection(adapter.getPosition(item));
            }
        }
    }

    private void setViewPager(){
        setListFragment();
//        binding.viewPager.setAdapter(new AppPagerAdapter(this, listFragment));
    }

    private void setListFragment() {
        listFragment.add(new HomeMoviesFragment());
        listFragment.add(new FavouriteMoviesFragment());
        listFragment.add(new SettingsFragment());
        listFragment.add(new AboutFragment());
    }

    public void setBadgeTextFavourite(int num){
        BadgeDrawable badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.favouriteMoviesFragment);
        badgeDrawable.setVerticalOffset(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, this.getResources().getDisplayMetrics())));
        badgeDrawable.setBadgeTextColor(Color.WHITE);
        badgeDrawable.setNumber(num);
        badgeDrawable.setBackgroundColor(Color.BLACK);
        if(num >0) badgeDrawable.setVisible(true);
        else badgeDrawable.setVisible(false);
    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void drawerClick(){
        binding.layoutProfile.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openScreenFromDrawer(new EditProfileFragment());
            }
        });
    }
    private void openScreenFromDrawer(Fragment fragment){
        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void settingUIScreenFromDrawer(String title, boolean isToolbarOff){
        closeDrawer();
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imvMoreToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.GONE);
        binding.imgBack.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(title);
        binding.bottomNavigationView.setVisibility(View.GONE);
        if(isToolbarOff) binding.toolbar.setVisibility(View.GONE);
    }

    private void closeDrawer(){
        binding.drawerLayout.close();
    }

    public void setUIDrawer(User user){
        binding.layoutProfile.imvUserImage.setImageURI(user.getUri());
        binding.layoutProfile.txtUserFullname.setText(user.getName());
        binding.layoutProfile.txtUserMail.setText(user.getEmail());
        binding.layoutProfile.txtUserDob.setText(user.getDate_of_birth());
        binding.layoutProfile.txtUserGender.setText(user.getGender());
    }

    public void setUIDrawer(){
        setUIDrawer(viewModel.getUser(getPackageName()));
    }
}