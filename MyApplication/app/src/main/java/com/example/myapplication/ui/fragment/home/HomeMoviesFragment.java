package com.example.myapplication.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.OnItemMovieClickListener;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.model.movie.Topic;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeMoviesFragment extends Fragment {
    private final List<MovieResult> listRemote = new ArrayList<>();
    HomeViewModel viewModel;
    int lastVisibleItemPosition;
    private List<MovieResult> listLocal;
    private FragmentHomeBinding binding;
    private MainActivity activity;
    private boolean isLoadingMore = false;
    private boolean isRefresh = false;
    private MovieAdapter adapter;
    private String keySort = "";
    private Topic sharedTopic;
    private int year;
    private float point;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        getLocalInfoToGetListMovieRemote();
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
        activity.uiToolBarHome();
        if (listLocal != null) listLocal.clear();
        viewModel.getListMovieLocal();
        observer();
        if(viewModel.isLoadingAgain()){
            getLocalInfoToGetListMovieRemote();
            binding.rcvListMovies.smoothScrollToPosition(0);
        }
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            isRefresh = true;
            activity.setCurrentLoadPage(1);
            new Handler().postDelayed(() -> {
                binding.swipeRefreshLayout.setRefreshing(false);
                viewModel.getAllMovieByTopic(sharedTopic.key, point, keySort, year, activity.getCurrentLoadPage(), isRefresh, isLoadingMore);
                isRefresh = false;
            }, 1000);
        });

        adapter = new MovieAdapter(viewModel.imageLoader, listRemote, listLocal, new OnItemMovieClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NavDirections action = HomeMoviesFragmentDirections.actionHomeMoviesFragmentToMovieDetailFragment(listRemote.get(position).getId(), listRemote.get(position).getTitle(), viewModel.isFavouriteMovie(listRemote.get(position).getId(), listLocal), listRemote.get(position));
                NavHostFragment.findNavController(requireParentFragment()).navigate(action);
            }

            @Override
            public void onFavouriteClick(ImageView view, int position) {
                boolean isFavourite = viewModel.isFavouriteMovie(listRemote.get(position).getId(), listLocal);
                if (isFavourite) {
                    view.setImageResource(R.drawable.ic_star_outline);
                    viewModel.deleteMovieFavourite(listRemote.get(position).getId());
                } else {
                    view.setImageResource(R.drawable.ic_star_fill);
                    viewModel.addMovieToFavourite(listRemote.get(position));
                }
            }

            @Override
            public void onChangeFavouriteState(ImageView view, int position) {
                view.setImageResource(viewModel.isFavouriteMovie(listRemote.get(position).getId(), listLocal) ? R.drawable.ic_star_fill : R.drawable.ic_star_outline);
            }
        });
        activity.getViewModel().mIsCheck.observe(requireActivity(), isGrid -> {
            int position = 0;
            adapter.setItemUI(isGrid);
            try {
                if(isGrid){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rcvListMovies.getLayoutManager();
                    assert layoutManager != null;
                    position = Objects.requireNonNull(layoutManager).findFirstVisibleItemPosition();
                }
                else {
                    GridLayoutManager layoutManager = (GridLayoutManager) binding.rcvListMovies.getLayoutManager();
                    assert layoutManager != null;
                    position = layoutManager.findFirstVisibleItemPosition();
                }
            } catch (Exception e) { e.printStackTrace(); }
            binding.rcvListMovies.setLayoutManager(isGrid ? new GridLayoutManager(requireContext(), 2) : new LinearLayoutManager(requireContext()));
            binding.rcvListMovies.scrollToPosition(position
            );
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
                    lastVisibleItemPosition = Objects.requireNonNull(layoutManager).findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (!isLoadingMore && lastVisibleItemPosition == totalItemCount-1) {
                        adapter.addLoadingItem();
                        isLoadingMore = true;
                        adapter.notifyDataSetChanged();
                        loadMoreData();
                    }
                });
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMoreData() {
        activity.setCurrentLoadPage(activity.getCurrentLoadPage() + 1);
        viewModel.getAllMovieByTopic(sharedTopic.key, point, keySort, year, activity.getCurrentLoadPage(), isRefresh, isLoadingMore);
        adapter.notifyDataSetChanged();
        isLoadingMore = false;
    }
    private void observer(){
        viewModel.mLdListMovieLocal.observe(requireActivity(), list -> {
            listLocal = list;
            if (adapter != null) adapter.setLdListMovie(listRemote, listLocal);
            activity.setBadgeTextFavourite(list.size());
        });
        viewModel.mLdFilterTopic.observe(requireActivity(), s -> {
            if (!s.isEmpty()) {
                activity.setUISpinner(s);
            }
        });
        viewModel.mLdYear.observe(requireActivity(), integer -> {
            if(year != integer || Boolean.TRUE.equals(viewModel.mLdIsLoadingAgain.getValue())){
                year = integer;
                getNewList();
            }
        });
        viewModel.mLdSortBy.observe(requireActivity(), s -> {
            if(!keySort.equals(s) || Boolean.TRUE.equals(viewModel.mLdIsLoadingAgain.getValue())){
                keySort = s;
                getNewList();
            }
        });
        viewModel.mLdFilterPoint.observe(requireActivity(), aFloat -> {
            if(point != aFloat || Boolean.TRUE.equals(viewModel.mLdIsLoadingAgain.getValue())){
                point = aFloat;
                getNewList();
            }
        });
        activity.getViewModel().mTopicState.observe(requireActivity(), topic -> {
            if(sharedTopic == null || !(sharedTopic == topic)||Boolean.TRUE.equals(viewModel.mLdIsLoadingAgain.getValue())){
                sharedTopic = topic;
                getNewList();
            }
        });
        viewModel.mLdListMovieRemote.observe(requireActivity(), movieResponse -> {
            listRemote.clear();
            listRemote.addAll(movieResponse);
            if (adapter != null) adapter.setLdListMovie(listRemote, listLocal);
            if (movieResponse.isEmpty()) {
                binding.txtNothing.setVisibility(View.VISIBLE);
            } else {
                binding.txtNothing.setVisibility(View.GONE);
            }
        });
    }

    private void getNewList() {
        try {
            activity.setCurrentLoadPage(1);
            viewModel.getAllMovieByTopic(sharedTopic.key, point, keySort, year, activity.getCurrentLoadPage(), true, isLoadingMore);
            binding.rcvListMovies.smoothScrollToPosition(0);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getLocalInfoToGetListMovieRemote() {
        // get list local
        viewModel.getListMovieLocal();
        // get all info in sharedPreference
        //topic
        viewModel.getKeyTopicSharedPreferences();
        //year
        viewModel.getYearSharedPreferences();
        //sort by
        viewModel.getKeySortSharedPreferences();
        //point & get local
        viewModel.getPointSharedPreferences();
    }

    public void onResume() {
        super.onResume();
    }
}
