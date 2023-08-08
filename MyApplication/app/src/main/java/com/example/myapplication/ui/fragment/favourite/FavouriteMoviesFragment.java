package com.example.myapplication.ui.fragment.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.core.OnItemMovieClickListener;
import com.example.myapplication.core.OnSearchViewClickListener;
import com.example.myapplication.databinding.FragmentFavoriteBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMoviesFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private FavouriteMoviesViewModel viewModel;
    private MovieFavouriteAdapter adapter;
    private MainActivity activity;
    private List<MovieResult> listSearchMovie = new ArrayList<>();

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
        activity.uiToolbarOtherPage("Favorite");
        if (isAdded()) {
            binding.lnWaiting.setVisibility(View.GONE);
        }
        activity.setSearchUI(new OnSearchViewClickListener() {
            @Override
            public void clickCloseSearch() {
                viewModel.getListFavouriteMovie();
            }

            @Override
            public void clickSearchIcon(String str) {
                viewModel.searchMovie(str);
            }
        });
        viewModel.mLdListMovieLocal.observe(requireActivity(), movieResults -> {
            activity.setBadgeTextFavourite(movieResults.size());
            adapter = new MovieFavouriteAdapter(movieResults, new OnItemMovieClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    MovieResult rs = movieResults.get(position);
                    NavDirections action = FavouriteMoviesFragmentDirections.actionFavouriteMoviesFragmentToMovieDetailFragment(rs.getId(), rs.getTitle(), rs.isFavourite(), rs);
                    NavHostFragment.findNavController(requireParentFragment()).navigate(action);
                }

                @Override
                public void onFavouriteClick(View view, boolean isCheck, int position) {
                    if(!isCheck){
                        viewModel.deleteFavouriteMovie(movieResults.get(position).getId());
                    }
                }

                @Override
                public void onChangeFavouriteState(CheckBox view, int position) {}
            });
            adapter.notifyDataSetChanged();
            binding.rcvFavouriteMovies.setAdapter(adapter);
        });
        viewModel.mLdListSearchMovie.observe(requireActivity(), resultList -> {
            listSearchMovie.clear();
            listSearchMovie.addAll(resultList);
            adapter.setListMovie(listSearchMovie);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
