package com.example.myapplication.ui.fragment.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.model.movie.SettingInfo;
import com.example.myapplication.domain.repo.ImageLoader;
import com.example.myapplication.domain.usecase.movielocal.DeleteMovieInLocalUseCase;
import com.example.myapplication.domain.usecase.listmovie.GetListMoviesUseCase;
import com.example.myapplication.domain.usecase.detailmovie.GetMovieDetailUseCase;
import com.example.myapplication.domain.usecase.setting.GetSettingsInforSharedPreferenceUseCase;
import com.example.myapplication.domain.usecase.movielocal.InsertMovieToLocalUseCase;

import java.util.ArrayList;
import java.util.Comparator;
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
    private GetListMoviesUseCase getListMoviesUseCase;
    private InsertMovieToLocalUseCase insertMovieToLocalUseCase;
    private DeleteMovieInLocalUseCase deleteMovieInLocalUseCase;
    private GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase;
    ImageLoader imageLoader;

    @Inject
    public HomeViewModel(GetListMoviesUseCase getListMoviesUseCase,
                         InsertMovieToLocalUseCase insertMovieToLocalUseCase,
                         DeleteMovieInLocalUseCase deleteMovieInLocalUseCase,
                         GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase,
                         ImageLoader imageLoader) {
        this.getListMoviesUseCase = getListMoviesUseCase;
        this.insertMovieToLocalUseCase = insertMovieToLocalUseCase;
        this.deleteMovieInLocalUseCase = deleteMovieInLocalUseCase;
        this.getSettingsInforSharedPreferenceUseCase = getSettingsInforSharedPreferenceUseCase;
        this.imageLoader = imageLoader;
    }

    private final MutableLiveData<List<MovieResult>> ldListMovieLocal = new MutableLiveData<>();
    public LiveData<List<MovieResult>> mLdListMovieLocal = ldListMovieLocal;

    private final MutableLiveData<List<MovieResult>> ldListMovieRemote = new MutableLiveData<>();
    public LiveData<List<MovieResult>> mLdListMovieRemote = ldListMovieRemote;

    private final MutableLiveData<String> ldFilterTopic = new MutableLiveData<>();
    public LiveData<String> mLdFilterTopic = ldFilterTopic;

    private final MutableLiveData<Float> ldFilterPoint = new MutableLiveData<>();
    public LiveData<Float> mLdFilterPoint = ldFilterPoint;

    private final MutableLiveData<String> ldSortBy = new MutableLiveData<>();
    public LiveData<String> mLdSortBy = ldSortBy;

    private final MutableLiveData<Integer> ldYear = new MutableLiveData<>();
    public LiveData<Integer> mLdYear = ldYear;

    private final MutableLiveData<SettingInfo> ldSettingInfo = new MutableLiveData<>();
    public LiveData<SettingInfo> mLdSettingInfo = ldSettingInfo;

    public void getAllMovieByTopic(String topic, float point, String sortBy, int year, int num_page) {
        getListMoviesUseCase.getAllMoviesByTopic(topic, num_page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<MovieResponse>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}
                @Override
                public void onSuccess(@NonNull MovieResponse movieResponse) {
                    List<MovieResult> listValue;
                    listValue = movieResponse.getResults();
                    List<MovieResult> listRs = new ArrayList<>();
                    for (MovieResult item : listValue) {
                        if ((Float.parseFloat(item.getVoteAverage()) >= point) && (Integer.parseInt(item.getReleaseDate().substring(0, 4)) >= year)) {
                            listRs.add(item);
                        }
                    }
                    if (sortBy.equals(AppConfig.Companion.KEY_RELEASE_DATE)) {
                        Comparator<MovieResult> byReleaseDate = Comparator.comparing(MovieResult::getReleaseDate);
                        listRs.sort(byReleaseDate.reversed());
                    } else if (sortBy.equals(AppConfig.Companion.KEY_RATING)) {
                        Comparator<MovieResult> byRating = Comparator.comparing(MovieResult::getVoteAverage);
                        listRs.sort(byRating.reversed());
                    }
                    ldListMovieRemote.postValue(listRs);
                }
                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }
            });
    }

    public void getListMovieLocal() {
        getListMoviesUseCase.getListMovieLocal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}
                    @Override
                    public void onSuccess(@NonNull List<MovieResult> movieResults) {
                        ldListMovieLocal.postValue(movieResults);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {}
                });
    }

    public void addMovieToFavourite(MovieResult movieResult) {
        insertMovieToLocalUseCase.insertMovieLocal(movieResult);
        getListMovieLocal();
    }

    public void deleteMovieFavourite(int movieId) {
        deleteMovieInLocalUseCase.deleteFavouriteMovie(movieId);
        getListMovieLocal();
    }

    public boolean isFavouriteMovie(int movieId, List<MovieResult> resultList) {
        if (resultList == null || resultList.isEmpty())
            return false;
        else {
            for (MovieResult item : resultList) {
                if (item.getId() == movieId) return true;
            }
            return false;
        }
    }

    public void getKeyTopicSharedPreferences() {
        String topicKey = getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_TOPIC, AppConfig.Companion.POPULAR);
        ldFilterTopic.postValue(topicKey);
    }

    public void getPointSharedPreferences() {
        float point = getSettingsInforSharedPreferenceUseCase.getFloat(AppConfig.Companion.KEY_POINT);
        ldFilterPoint.postValue(point);
    }

    public void getKeySortSharedPreferences() {
        String keySort = getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_SORT, "");
        ldSortBy.postValue(keySort);
    }

    public void getYearSharedPreferences() {
        int year = getSettingsInforSharedPreferenceUseCase.getInt(AppConfig.Companion.KEY_YEAR);
        ldYear.postValue(year);
    }
}
