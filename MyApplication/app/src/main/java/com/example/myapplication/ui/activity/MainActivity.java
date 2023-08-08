package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SearchView;

import com.example.myapplication.R;
import com.example.myapplication.core.OnSearchViewClickListener;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.model.user.User;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.domain.model.movie.Topic;
import com.example.myapplication.ui.adapter.AppPagerAdapter;
import com.example.myapplication.ui.fragment.about.AboutFragment;
import com.example.myapplication.ui.fragment.favourite.FavouriteMoviesFragment;
import com.example.myapplication.ui.fragment.home.HomeMoviesFragment;
import com.example.myapplication.ui.fragment.profile.editprofile.EditProfileFragment;
import com.example.myapplication.ui.fragment.reminder.AllReminderFragment;
import com.example.myapplication.ui.fragment.settings.SettingsFragment;
import com.google.android.material.badge.BadgeDrawable;

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
    private DrawerReminderAdapter reminderAdapter;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //nav viewpager
//        setViewPager();
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
        binding.imvGridToolbar.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setCheck(isChecked));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setSearchUI(OnSearchViewClickListener listener) {
        binding.svSearchView.setVisibility(View.VISIBLE);
        binding.svSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchMovie(query);
                listener.clickSearchIcon(query);
                binding.svSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listener.clickCloseSearch();
//                binding.svSearchView.clearFocus();
                return false;
            }
        });
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

    @Override
    protected void onStart() {
        super.onStart();
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

    private void setViewPager()
    {
        setListFragment();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.viewPager.setAdapter(new AppPagerAdapter(this, listFragment));
        Navigation.setViewNavController(binding.viewPager, navController);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        binding.bottomNavigationView.setSelectedItemId(R.id.homeMoviesFragment);
                        break;
                    case 1:
                        binding.bottomNavigationView.setSelectedItemId(R.id.favouriteMoviesFragment);
                        break;
                    case 2:
                        binding.bottomNavigationView.setSelectedItemId(R.id.settingsFragment);
                        break;
                    case 3:
                        binding.bottomNavigationView.setSelectedItemId(R.id.aboutFragment);
                }
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homeMoviesFragment:
                    binding.viewPager.setCurrentItem(0);
                    break;
                case R.id.favouriteMoviesFragment:
                    binding.viewPager.setCurrentItem(1);
                    break;
                case R.id.settingsFragment:
                    binding.viewPager.setCurrentItem(2);
                    break;
                case R.id.aboutFragment:
                    binding.viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });
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
        binding.layoutProfile.btnEditProfile.setOnClickListener(v -> openScreenFromDrawer(new EditProfileFragment()));
        binding.layoutProfile.btnShowAll.setOnClickListener(v -> openScreenFromDrawer(new AllReminderFragment()));
    }
    private void openScreenFromDrawer(Fragment fragment){
        closeDrawer();
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
        setRcvReminder();
    }
    private void setRcvReminder() {
        viewModel.getListReminder();
        List<Reminder> reminderList  = new ArrayList<>();
        viewModel.mLdReminder.observe(this, reminders -> {
            reminderList.addAll(reminders);
            if(reminders.isEmpty()) binding.layoutProfile.txtReminderEmpty.setVisibility(View.VISIBLE);
            else binding.layoutProfile.txtReminderEmpty.setVisibility(View.GONE);
            if(reminderAdapter != null) reminderAdapter.setLdListMovie(reminders);
        });
        reminderAdapter = new DrawerReminderAdapter(reminderList);
        binding.layoutProfile.rcvReminder.setAdapter(reminderAdapter);
        adapter.notifyDataSetChanged();
    }
    public void uiToolBarHome(){
        binding.svSearchView.setVisibility(View.GONE);
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
        binding.svSearchView.setVisibility(View.GONE);
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
        binding.svSearchView.setVisibility(View.GONE);
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
}