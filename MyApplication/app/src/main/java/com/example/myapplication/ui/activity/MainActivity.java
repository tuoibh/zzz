package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.SearchView;
import com.example.myapplication.R;
import com.example.myapplication.core.OnSearchViewClickListener;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.model.user.User;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.domain.model.movie.Topic;
import com.example.myapplication.ui.fragment.profile.editprofile.EditProfileFragment;
import com.example.myapplication.ui.fragment.reminder.AllReminderFragment;
import com.google.android.material.badge.BadgeDrawable;
import java.util.ArrayList;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, PopupMenu.OnMenuItemClickListener {
    private ActivityMainBinding binding;
    public MainViewModel getViewModel() {
        return viewModel;
    }
    private MainViewModel viewModel;
    private ArrayAdapter<Topic> adapter;
    private DrawerReminderAdapter reminderAdapter;
    private int currentLoadPage = 1;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        viewModel.createListTopic();
        setSpinner();
        viewModel.getTopic();
        binding.imgBack.setOnClickListener(v -> onBackPressed());
        binding.imgMenuMain.setOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
        drawerClick();
        setUIDrawer();
        binding.imvGridToolbar.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setCheck(isChecked));
        binding.svSearchView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (Math.abs(right - left) <= 144) binding.txtTitleScreen.setVisibility(View.VISIBLE);
            else binding.txtTitleScreen.setVisibility(View.GONE);
        });
        binding.svSearchView.setOnClickListener(v -> binding.txtTitleScreen.setVisibility(View.GONE));
        binding.imvMoreToolbar.setOnClickListener(v -> showPopUpMenu(v));
    }

    private void showPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.bottom_nav);
        popupMenu.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeMoviesFragment:
                binding.bottomNavigationView.setSelectedItemId(R.id.homeMoviesFragment);
                return true;
            case R.id.favouriteMoviesFragment:
                binding.bottomNavigationView.setSelectedItemId(R.id.favouriteMoviesFragment);
                return true;
            case R.id.settingsFragment:
                binding.bottomNavigationView.setSelectedItemId(R.id.settingsFragment);
                return true;
            case R.id.aboutFragment:
                binding.bottomNavigationView.setSelectedItemId(R.id.aboutFragment);
                return true;
            default:
                return false;
        }
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
                return false;
            }
        });
    }

    private void setSpinner() {
        binding.spinnerTopic.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter<>(this, R.layout.item_spinner, viewModel.listTopic);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    public void setUISpinner(String topicKey) {
        for (Topic item : viewModel.listTopic) {
            if (item.key.equals(topicKey)) {
                binding.spinnerTopic.setSelection(adapter.getPosition(item));
            }
        }
    }

    public void setBadgeTextFavourite(int num) {
        BadgeDrawable badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.favouriteMoviesFragment);
        badgeDrawable.setVerticalOffset(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, this.getResources().getDisplayMetrics())));
        badgeDrawable.setBadgeTextColor(Color.WHITE);
        badgeDrawable.setNumber(num);
        badgeDrawable.setBackgroundColor(Color.BLACK);
        badgeDrawable.setVisible(num > 0);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void drawerClick() {
        binding.layoutProfile.btnEditProfile.setOnClickListener(v -> openScreenFromDrawer(new EditProfileFragment()));
        binding.layoutProfile.btnShowAll.setOnClickListener(v -> openScreenFromDrawer(new AllReminderFragment()));
    }

    private void openScreenFromDrawer(Fragment fragment) {
        closeDrawer();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void settingUIScreenFromDrawer(String title, boolean isToolbarOff) {
        closeDrawer();
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imvMoreToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.GONE);
        binding.imgBack.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(title);
        binding.bottomNavigationView.setVisibility(View.GONE);
        if (isToolbarOff) binding.toolbar.setVisibility(View.GONE);
    }

    private void closeDrawer() {
        binding.drawerLayout.close();
    }

    public void setUIDrawer(User user) {
        binding.layoutProfile.imvUserImage.setImageURI(user.getUri());
        binding.layoutProfile.txtUserFullname.setText(user.getName());
        binding.layoutProfile.txtUserMail.setText(user.getEmail());
        binding.layoutProfile.txtUserDob.setText(user.getDate_of_birth());
        binding.layoutProfile.txtUserGender.setText(user.getGender());
    }

    public void setUIDrawer() {
        setUIDrawer(viewModel.getUser(getPackageName()));
        setRcvReminder();
    }

    private void setRcvReminder() {
        viewModel.getListReminder();
        List<Reminder> reminderList = new ArrayList<>();
        viewModel.mLdReminder.observe(this, reminders -> {
            reminderList.addAll(reminders);
            if (reminders.isEmpty())
                binding.layoutProfile.txtReminderEmpty.setVisibility(View.VISIBLE);
            else binding.layoutProfile.txtReminderEmpty.setVisibility(View.GONE);
            if (reminderAdapter != null) reminderAdapter.setLdListMovie(reminders);
        });
        reminderAdapter = new DrawerReminderAdapter(reminderList);
        binding.layoutProfile.rcvReminder.setAdapter(reminderAdapter);
        adapter.notifyDataSetChanged();
    }

    public void uiToolBarHome() {
        binding.svSearchView.setVisibility(View.GONE);
        binding.imgBack.setVisibility(View.GONE);
        binding.txtTitleScreen.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.VISIBLE);
        binding.spinnerTopic.setVisibility(View.VISIBLE);
        binding.imvGridToolbar.setVisibility(View.VISIBLE);
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void uiToolbarOtherPage(String title) {
        binding.svSearchView.setVisibility(View.GONE);
        binding.imgBack.setVisibility(View.GONE);
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(title);
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void uiToolbarDetail(String movieTitle) {
        binding.svSearchView.setVisibility(View.GONE);
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.GONE);
        binding.imgBack.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(movieTitle);
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public int getCurrentLoadPage() {
        return currentLoadPage;
    }

    public void setCurrentLoadPage(int currentLoadPage) {
        this.currentLoadPage = currentLoadPage;
    }
}