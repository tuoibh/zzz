package com.example.myapplication.ui.fragment.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.model.castncrew.CastNCrewResponse;
import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.usecase.DeleteMovieInLocalUseCase;
import com.example.myapplication.domain.usecase.GetCastNCrewMovieUseCase;
import com.example.myapplication.domain.usecase.GetMovieDetailUseCase;
import com.example.myapplication.domain.usecase.InsertMovieToLocalUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<MovieDetailResponse> ldMovieDetail = new MutableLiveData<>();
    public LiveData<MovieDetailResponse> mLdMovieDetail = ldMovieDetail;

    private MutableLiveData<CastNCrewResponse> ldCastNCrew = new MutableLiveData<>();
    public LiveData<CastNCrewResponse> mLdCastNCrew = ldCastNCrew;
    @Inject
    GetMovieDetailUseCase getMovieDetailUseCase;
    @Inject
    GetCastNCrewMovieUseCase getCastNCrewMovieUseCase;
    @Inject
    InsertMovieToLocalUseCase insertMovieToLocalUseCase;
    @Inject
    DeleteMovieInLocalUseCase deleteMovieInLocalUseCase;
    @Inject
    public MovieDetailViewModel(GetMovieDetailUseCase getMovieDetailUseCase) {
        this.getMovieDetailUseCase = getMovieDetailUseCase;
    }

    /*@Inject
    public MovieDetailViewModel(GetMovieDetailUseCase getMovieDetailUseCase, GetCastNCrewMovieUseCase getCastNCrewMovieUseCase, InsertMovieToLocalUseCase insertMovieToLocalUseCase, DeleteMovieInLocalUseCase deleteMovieInLocalUseCase) {
        this.getMovieDetailUseCase = getMovieDetailUseCase;
        this.getCastNCrewMovieUseCase = getCastNCrewMovieUseCase;
        this.insertMovieToLocalUseCase = insertMovieToLocalUseCase;
        this.deleteMovieInLocalUseCase = deleteMovieInLocalUseCase;
    }*/

    public void getMovieDetailRemote(String id){
        Thread thread = new Thread(() ->
                getMovieDetailUseCase.getMovieDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieDetailResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull MovieDetailResponse movieDetailResponse) {
                        ldMovieDetail.setValue(movieDetailResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
        thread.start();
    }

    public void getMovieCastNCrew(String id){
        getCastNCrewMovieUseCase.getCastNCrew(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CastNCrewResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull CastNCrewResponse castNCrewResponse) {
                        ldCastNCrew.setValue(castNCrewResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void addFavouriteMovie(MovieResult movieResult){
        Log.d("tbh_", "addFavouriteMovie: MovieDetailViewModel");
        insertMovieToLocalUseCase.insertMovieLocal(movieResult);
    }

    public void deleteFavourite(int movieId){
        Log.d("tbh_", "deleteFavourite: MovieDetailViewModel");
        deleteMovieInLocalUseCase.deleteFavouriteMovie(movieId);
    }
}
