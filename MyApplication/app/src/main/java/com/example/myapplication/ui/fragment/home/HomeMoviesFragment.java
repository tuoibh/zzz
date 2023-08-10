package com.example.myapplication.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.core.OnItemMovieClickListener;
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
    private String keySort = "";
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
        activity.uiToolBarHome();
        if(listLocal != null) listLocal.clear();
        viewModel.getListMovieLocal();
        getListMovieRemote();

        viewModel.mLdListMovieLocal.observe(requireActivity(), list -> {
            listLocal = list;
            if(adapter != null) adapter.setLdListMovie(listRemote, listLocal);
            activity.setBadgeTextFavourite(list.size());
        });
        viewModel.mLdFilterTopic.observe(requireActivity(), s -> {
            if (!s.isEmpty()) {
                activity.setUISpinner(s);
            }
        });
        viewModel.mLdYear.observe(requireActivity(), integer -> year = integer);
        viewModel.mLdSortBy.observe(requireActivity(), s -> keySort = s);
        viewModel.mLdFilterPoint.observe(requireActivity(), aFloat -> point = aFloat );
        activity.getViewModel().mTopicState.observe(requireActivity(), topic -> {
            sharedTopic = topic;
            viewModel.getAllMovieByTopic(topic.key, point, keySort, year, currentLoadPage);
        });
        viewModel.mLdListMovieRemote.observe(requireActivity(), movieResponse -> {
            if (isRefresh) {
                listRemote.clear();
                listRemote.addAll(movieResponse);
                isRefresh = false;
                binding.swipeRefreshLayout.setRefreshing(false);
            } else if (isLoadingMore) {
                listRemote.addAll(movieResponse);
                isLoadingMore = false;
            } else {
                listRemote.clear();
                listRemote.addAll(movieResponse);
            }
            if (adapter != null) adapter.setLdListMovie(listRemote, listLocal);
            if(movieResponse.isEmpty()){
                binding.txtNothing.setVisibility(View.VISIBLE);
            } else {
                binding.txtNothing.setVisibility(View.GONE);
            }
        });
        // refresh
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            isRefresh = true;
            currentLoadPage = 1;
            new Handler().postDelayed(() -> {
                listRemote.clear();
                binding.swipeRefreshLayout.setRefreshing(false);
                viewModel.getAllMovieByTopic(sharedTopic.key, point, keySort, year, currentLoadPage);
            }, 1000);
        });

        adapter = new MovieAdapter(viewModel.imageLoader, listRemote, listLocal, new OnItemMovieClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NavDirections action = HomeMoviesFragmentDirections.actionHomeMoviesFragmentToMovieDetailFragment(
                    listRemote.get(position).getId(),
                    listRemote.get(position).getTitle(),
                    viewModel.isFavouriteMovie(listRemote.get(position).getId(),listLocal),
                    listRemote.get(position));
            NavHostFragment.findNavController(requireParentFragment()).navigate(action);
            };

            @Override
            public void onFavouriteClick(ImageView view, int position) {
                boolean isFavourite = viewModel.isFavouriteMovie(listRemote.get(position).getId(), listLocal);
                if(isFavourite){
                    view.setImageResource(R.drawable.ic_star_outline);
                    viewModel.deleteMovieFavourite(listRemote.get(position).getId());
                } else {
                    view.setImageResource(R.drawable.ic_star_fill);
                    viewModel.addMovieToFavourite(listRemote.get(position));
                }
            }

            @Override
            public void onChangeFavouriteState(ImageView view, int position) {
                view.setImageResource(viewModel.isFavouriteMovie(listRemote.get(position).getId(), listLocal)?
                        R.drawable.ic_star_fill: R.drawable.ic_star_outline);
            }
        });
        activity.getViewModel().mIsCheck.observe(requireActivity(), isGrid -> {
            adapter.setItemUI(isGrid);
            binding.rcvListMovies.setLayoutManager(isGrid? new GridLayoutManager(requireContext(), 2) : new LinearLayoutManager(requireContext()));
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

    @SuppressLint("NotifyDataSetChanged")
    private void getListMovieRemote(){
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
    @Override
    public void onResume() {
        super.onResume();
    }
}
