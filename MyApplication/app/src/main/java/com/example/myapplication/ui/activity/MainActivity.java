package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.domain.model.Topic;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<Topic> listTopic = new ArrayList<Topic>();
    private ActivityMainBinding binding;
    private NavController navController;
    private Toolbar toolbar;
    public MainViewModel getViewModel() {
        return viewModel;
    }
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        createListTopic();
        setSupportActionBar(toolbar);
        setSpinner();
        binding.imgBack.setOnClickListener(v -> onBackPressed());
    }

    private void setSpinner() {
        binding.spinnerTopic.setOnItemSelectedListener(this);
        ArrayAdapter<Topic> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, listTopic);
        adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        binding.spinnerTopic.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        viewModel.updateTopic(listTopic.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void createListTopic(){
        listTopic.add(new Topic(AppConfig.Companion.POPULAR, "POPULAR"));
        listTopic.add(new Topic(AppConfig.Companion.TOP_RATED, "TOP RATE"));
        listTopic.add(new Topic(AppConfig.Companion.UP_COMING, "UP COMING"));
        listTopic.add(new Topic(AppConfig.Companion.NOW_PLAYING, "NOW PLAYING"));
    }

    public void uiToolBarHome(){
        binding.imgBack.setVisibility(View.GONE);
        binding.txtTitleScreen.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.VISIBLE);
        binding.spinnerTopic.setVisibility(View.VISIBLE);
        binding.imvGridToolbar.setVisibility(View.VISIBLE);
        binding.imvMoreToolbar.setVisibility(View.VISIBLE);
    }

    public void uiToolbarOtherPage(String title){
        binding.imgBack.setVisibility(View.GONE);
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imvMoreToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(title);
    }

    public void uiToolbarDetail(String movieTitle){
        binding.spinnerTopic.setVisibility(View.GONE);
        binding.imvGridToolbar.setVisibility(View.GONE);
        binding.imvMoreToolbar.setVisibility(View.GONE);
        binding.imgMenuMain.setVisibility(View.GONE);
        binding.imgBack.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setVisibility(View.VISIBLE);
        binding.txtTitleScreen.setText(movieTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tbh_", "onResume: MainActivity");
    }
}