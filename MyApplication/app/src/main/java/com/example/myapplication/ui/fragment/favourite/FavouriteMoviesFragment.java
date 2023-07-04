package com.example.myapplication.ui.fragment.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentFavoriteBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.ui.activity.MainActivity;

public class FavouriteMoviesFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private FavouriteMoviesViewModel viewModel;
    private MovieFavouriteAdapter adapter;
    private MainActivity activity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(FavouriteMoviesViewModel.class);
        activity = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        viewModel.getListFavouriteMovie();
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isAdded()){
            binding.lnWaiting.setVisibility(View.GONE);
        }
        viewModel.mLdListMovieLocal.observe(requireActivity(), movieResults -> {
            adapter = new MovieFavouriteAdapter(movieResults, viewModel, (view1, position) -> {
                MovieResult rs = movieResults.get(position);
                NavDirections action = FavouriteMoviesFragmentDirections.actionFavouriteMoviesFragmentToMovieDetailFragment(rs.getId()+"", rs.getTitle(), rs.isFavourite(), rs);
                NavHostFragment.findNavController(this).navigate(action);
            });
            adapter.notifyDataSetChanged();
            binding.rcvFavouriteMovies.setAdapter(adapter);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.uiToolbarOtherPage("Favorite");
    }
}
