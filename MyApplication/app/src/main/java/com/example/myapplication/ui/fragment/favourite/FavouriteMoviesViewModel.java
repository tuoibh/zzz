package com.example.myapplication.ui.fragment.favourite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.usecase.movielocal.DeleteMovieInLocalUseCase;
import com.example.myapplication.domain.usecase.listmovie.GetListMoviesUseCase;
import com.example.myapplication.domain.usecase.movielocal.SearchMovieUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class FavouriteMoviesViewModel extends ViewModel {
    @Inject
    GetListMoviesUseCase getListMoviesUseCase;
    @Inject
    DeleteMovieInLocalUseCase deleteMovieInLocalUseCase;
    @Inject
    SearchMovieUseCase searchMovieUseCase;

    @Inject
    public FavouriteMoviesViewModel(GetListMoviesUseCase getListMoviesUseCase) {
        this.getListMoviesUseCase = getListMoviesUseCase;
    }

    private final MutableLiveData<List<MovieResult>> ldListMovieLocal = new MutableLiveData<>();
    public LiveData<List<MovieResult>> mLdListMovieLocal = ldListMovieLocal;

    private final MutableLiveData<List<MovieResult>> ldListSearchMovie = new MutableLiveData<>();
    public LiveData<List<MovieResult>> mLdListSearchMovie = ldListSearchMovie;

    public void getListFavouriteMovie(){
        Thread thread = new Thread(() -> getListMoviesUseCase.getListMovieLocal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onSuccess(@NonNull List<MovieResult> movieResults) {
                        ldListMovieLocal.postValue(movieResults);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
        thread.start();
    }

    public void deleteFavouriteMovie(int movieId){
        Thread thread = new Thread(() -> {
            deleteMovieInLocalUseCase.deleteFavouriteMovie(movieId);
            getListFavouriteMovie();
        });
        thread.start();
    }

    public void searchMovie(String movieTitle){
        Thread thread = new Thread(() -> searchMovieUseCase.searchMovieLocal(movieTitle).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<MovieResult>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}
                @Override
                public void onSuccess(@NonNull List<MovieResult> movieResults) {
                    ldListSearchMovie.postValue(movieResults);
                }
                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }
            }));
        thread.start();
    }
}
