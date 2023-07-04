package com.example.myapplication.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.ui.activity.MainActivity;

import java.util.List;


public class HomeMoviesFragment extends Fragment {
    private FragmentHomeBinding binding;
    HomeViewModel viewModel;
    private MainActivity activity;
    private boolean isLoading = false;
    private MovieAdapter adapter;
    private int lastVisiblePosition;
    int lastVisibleItemPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        activity = (MainActivity) getActivity();
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
        activity.getViewModel().mTopicState.observe(requireActivity(), topic -> viewModel.getAllMovieByTopic(topic.key));

        viewModel.getListMovieLocal();
        viewModel.mLdListMovieRemote.observe(getActivity(), movieResponse -> {
            List<MovieResult> listRs = movieResponse;
            MovieAdapter adapter = new MovieAdapter(listRs, viewModel, (view1, position) ->
            {
                NavDirections action = HomeMoviesFragmentDirections.actionHomeMoviesFragmentToMovieDetailFragment(listRs.get(position).getId() + "", listRs.get(position).getTitle(), viewModel.isFavouriteMovie(listRs.get(position).getId()), listRs.get(position));
                NavHostFragment.findNavController(this).navigate(action);
            });
            adapter.notifyDataSetChanged();
            binding.rcvListMovies.setItemViewCacheSize(movieResponse.size());
            binding.rcvListMovies.setAdapter(adapter);
        });

        binding.rcvListMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data
                    loadMoreData();
                }
            }
        });
        if (savedInstanceState != null) {
            lastVisibleItemPosition = savedInstanceState.getInt("lastVisibleItemPosition");
            binding.rcvListMovies.getLayoutManager().scrollToPosition(lastVisiblePosition);
        }
    }

    private void loadMoreData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                isLoading = true;
                //loaddata
                Log.d("tbh_", "run: loadmore");
                isLoading = false;
            }
        });
        thread.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.uiToolBarHome();
    }
}
