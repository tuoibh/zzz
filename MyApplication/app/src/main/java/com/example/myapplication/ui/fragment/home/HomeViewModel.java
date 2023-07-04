package com.example.myapplication.ui.fragment.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.usecase.DeleteMovieInLocalUseCase;
import com.example.myapplication.domain.usecase.GetListMoviesUseCase;
import com.example.myapplication.domain.usecase.GetMovieDetailUseCase;
import com.example.myapplication.domain.usecase.InsertMovieToLocalUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    @Inject
    public HomeViewModel() {
    }

    @Inject
    GetListMoviesUseCase getListMoviesUseCase;
    @Inject
    InsertMovieToLocalUseCase insertMovieToLocalUseCase;
    @Inject
    GetMovieDetailUseCase getMovieDetailUseCase;
    @Inject
    DeleteMovieInLocalUseCase deleteMovieInLocalUseCase;
    private final MutableLiveData<List<MovieResult>> ldListMovieLocal = new MutableLiveData<>();
    public LiveData<List<MovieResult>> mLdListMovieLocal = ldListMovieLocal;

    private final MutableLiveData<Boolean> ldTopicStandStill = new MutableLiveData<>(false);
    public LiveData<Boolean> mLdTopicStandStill = ldTopicStandStill;
    private final MutableLiveData<String> ldCurrentTopic = new MutableLiveData<>();
    public LiveData<String> mLdCurrentTopic = ldCurrentTopic;

    private final MutableLiveData<MovieDetailResponse> ldMovieDetail = new MutableLiveData<>();
    public LiveData<MovieDetailResponse> mLdMovieDetail = ldMovieDetail;

    private final MutableLiveData<List<MovieResult>> ldListMovieRemote = new MutableLiveData<>();
    public LiveData<List<MovieResult>> mLdListMovieRemote = ldListMovieRemote;

    public void getAllMovieByTopic(String topic) {
        Thread thread = new Thread(() -> {
            getListMoviesUseCase.getAllMoviesByTopic(topic)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<MovieResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                        }

                        @Override
                        public void onSuccess(@NonNull MovieResponse movieResponse) {
                            List<MovieResult> listValue;
                            listValue = movieResponse.getResults();
                            ldListMovieRemote.postValue(listValue);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                        }
                    });
        });
        thread.start();
    }

    public void getListMovieLocal(){
        getListMoviesUseCase.getListMovieLocal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<MovieResult> movieResults) {
                        ldListMovieLocal.postValue(movieResults);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public void addMovieToFavourite(MovieResult movieResult) {
        insertMovieToLocalUseCase.insertMovieLocal(movieResult);
        getListMovieLocal();
    }

    public boolean updateCurrentTopic(String topic){
        if(!topic.equals(ldCurrentTopic.getValue())){
            ldCurrentTopic.postValue(topic);
            return false;
        } else{
            return true;
        }
    }

    public void deleteMovieFavourite(int movieId){
        deleteMovieInLocalUseCase.deleteFavouriteMovie(movieId);
        getListMovieLocal();
    }

    public boolean isFavouriteMovie(int movieId){
        getListMovieLocal();
        if(ldListMovieLocal.getValue()==null || ldListMovieLocal.getValue().isEmpty()) return false;
        else{
            for(MovieResult item: ldListMovieLocal.getValue()){
                if(item.getId() == movieId) return true;
            }
            return false;
        }
    }
}
