package com.example.myapplication.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.model.movie.Topic;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class HomeMoviesFragment extends Fragment {
    private FragmentHomeBinding binding;
    HomeViewModel viewModel;
    private MainActivity activity;
    private boolean isLoadingMore = false;
    private boolean isRefresh = true;
    private MovieAdapter adapter;
    int lastVisibleItemPosition;
    private String keySort;
    private Topic sharedTopic;
    private int year;
    private float point;
    private List<MovieResult> listLocal;
    private List<MovieResult> listRemote = new ArrayList<>();
    private int currentLoadPage = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        getListMovieRemote();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getListMovieLocal();

        // refresh
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            isRefresh = true;
            currentLoadPage = 1;
//            viewModel.getAllMovieByTopic(sharedTopic.key, point, keySort, year, currentLoadPage);
            new Handler().postDelayed(() -> {
                listRemote.clear();
                getListMovieRemote();
                binding.swipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });
        //set adapter
        adapter = new MovieAdapter(viewModel.imageLoader, requireContext(), listRemote, viewModel, listLocal, (view1, position) -> {
            NavDirections action = HomeMoviesFragmentDirections.actionHomeMoviesFragmentToMovieDetailFragment(
                    listRemote.get(position).getId(),
                    listRemote.get(position).getTitle(),
                    viewModel.isFavouriteMovie(listRemote.get(position).getId(),listLocal),
                    listRemote.get(position));
            NavHostFragment.findNavController(this).navigate(action);

        });
        adapter.notifyDataSetChanged();
        binding.rcvListMovies.setItemViewCacheSize(listRemote.size());
        binding.rcvListMovies.setAdapter(adapter);
        // load more
        binding.rcvListMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                binding.rcvListMovies.post(() -> {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (!isLoadingMore && lastVisibleItemPosition == totalItemCount - 1) {
                        loadMoreData();
                    }
                });
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMoreData() {
        adapter.removeLoadingItem();
        currentLoadPage++;
        isLoadingMore = true;
        viewModel.getAllMovieByTopic(sharedTopic.key, point, keySort, year, currentLoadPage);
        adapter.notifyDataSetChanged();
    }

    private void getListMovieRemote(){
        // get list local
        viewModel.getListMovieLocal();
        viewModel.mLdListMovieLocal.observe(requireActivity(), list -> {
            listLocal = list;
            activity.setBadgeTextFavourite(list.size());
        });

        // get all info in sharedPreference
        //topic
        viewModel.getKeyTopicSharedPreferences();
        viewModel.mLdFilterTopic.observe(requireActivity(), s -> {
            if (!s.isEmpty()) {
                activity.setUISpinner(s);
            }
        });
        //year
        viewModel.getYearSharedPreferences();
        viewModel.mLdYear.observe(requireActivity(), integer -> year = integer);
        //sort by
        viewModel.getKeySortSharedPreferences();
        viewModel.mLdSortBy.observe(requireActivity(), s -> keySort = s);
        //point & get local
        viewModel.getPointSharedPreferences();
        viewModel.mLdFilterPoint.observe(requireActivity(), aFloat ->
            {
                point = aFloat;
                activity.getViewModel().mTopicState.observe(requireActivity(), topic -> {
                    sharedTopic = topic;
                    currentLoadPage = 1;
                    viewModel.getAllMovieByTopic(topic.key, aFloat, keySort, year, currentLoadPage);
                });
            }
        );
        // get list remote
        viewModel.mLdListMovieRemote.observe(requireActivity(), movieResponse -> {
            /*if(isLoadingMore){
                listRemote.addAll(movieResponse);
                isLoadingMore = false;
            } else{
                listRemote.clear();
                listRemote.addAll(movieResponse);
            }
            if (adapter != null) adapter.setLdListMovie(listRemote);
            if(movieResponse.isEmpty()){
                binding.txtNothing.setVisibility(View.VISIBLE);
            } else {
                binding.txtNothing.setVisibility(View.GONE);
            }*/
            if (isRefresh) {
                listRemote.clear();
                listRemote.addAll(movieResponse);
                isRefresh = false;
            } else if (isLoadingMore) {
                listRemote.addAll(movieResponse);
                isLoadingMore = false;
            }
            if (adapter != null) adapter.setLdListMovie(listRemote);
            if(movieResponse.isEmpty()){
                binding.txtNothing.setVisibility(View.VISIBLE);
            } else {
                binding.txtNothing.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        activity.uiToolBarHome();
    }
}
