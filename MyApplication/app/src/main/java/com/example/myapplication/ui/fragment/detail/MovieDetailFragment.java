package com.example.myapplication.ui.fragment.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.FragmentMovieDetailBinding;
import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.ui.activity.MainActivity;

public class MovieDetailFragment extends Fragment {
    private FragmentMovieDetailBinding binding;
    private MainActivity activity;
    MovieDetailViewModel viewModel;
    MovieDetailResponse mMovieDetailResponse;
    MovieDetailFragmentArgs args;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);
        viewModel.getListMovieFavourite();
        args = MovieDetailFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getMovieDetailRemote(args.getMovieId());
        binding.lnWaiting.setVisibility(View.VISIBLE);
        viewModel.mLdMovieDetail.observe(requireActivity(), movieDetailResponse -> {
            mMovieDetailResponse = movieDetailResponse;
            if(isAdded()){
                binding.lnWaiting.setVisibility(View.GONE);
                viewModel.mLdListMovieRS.observe(requireActivity(), movieResults -> {
                    activity.setBadgeTextFavourite(movieResults.size());
                    binding.imvIsFavourite.setChecked(viewModel.isFavourite(args.getMovieId(), movieResults));
                });
                setUI();
            }
        });
        viewModel.getMovieCastNCrew(args.getMovieId()+"");
        viewModel.mLdCastNCrew.observe(requireActivity(), castNCrewResponse -> {
            CastNCrewAdapter adapter = new CastNCrewAdapter(viewModel.imageLoader, castNCrewResponse, viewModel, (view1, position) -> {
            });
            adapter.notifyDataSetChanged();
            binding.rcvCastNCrew.setAdapter(adapter);
        });
        binding.imvIsFavourite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(binding.imvIsFavourite.isChecked()){
                viewModel.addFavouriteMovie(args.getMovieResult());
            } else {
                viewModel.deleteFavourite(args.getMovieId());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        binding.txtReleaseDateText.setText(mMovieDetailResponse.getReleaseDate());
        binding.txtRatingText.setText(mMovieDetailResponse.getVoteAverage()+ "/10");
        Glide.with(this).load(mMovieDetailResponse.getPosterPath()).into(binding.imvPosterMovie);
        binding.txtOveriewText.setText(mMovieDetailResponse.getOverview());
        binding.txtReminderDate.setText(mMovieDetailResponse.getReleaseDate());
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getListMovieFavourite();
        activity.uiToolbarDetail(args.getMovieTitle());
    }
}