package com.example.myapplication.ui.fragment.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.ImageLoader;
import com.example.myapplication.domain.usecase.movielocal.DeleteMovieInLocalUseCase;
import com.example.myapplication.domain.usecase.listmovie.GetListMoviesUseCase;
import com.example.myapplication.domain.usecase.setting.GetSettingsInforSharedPreferenceUseCase;
import com.example.myapplication.domain.usecase.movielocal.InsertMovieToLocalUseCase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final GetListMoviesUseCase getListMoviesUseCase;
    private final InsertMovieToLocalUseCase insertMovieToLocalUseCase;
    private final DeleteMovieInLocalUseCase deleteMovieInLocalUseCase;
    private final GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase;
    ImageLoader imageLoader;

    private final List<MovieResult> listRemote = new ArrayList<>();
    private List<MovieResult> listLocal;

    @Inject
    public HomeViewModel(GetListMoviesUseCase getListMoviesUseCase, InsertMovieToLocalUseCase insertMovieToLocalUseCase, DeleteMovieInLocalUseCase deleteMovieInLocalUseCase, GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase, ImageLoader imageLoader) {
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
    private final MutableLiveData<Boolean> ldIsLoadingAgain = new MutableLiveData<>(true);
    public LiveData<Boolean> mLdIsLoadingAgain = ldIsLoadingAgain;

    public void getAllMovieByTopic(String topic, float point, String sortBy, int year, int num_page, boolean isRefresh, boolean isLoadmore) {
        getListMoviesUseCase.getAllMoviesByTopic(topic, num_page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MovieResponse>() {
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
                if (sortBy != null && sortBy.equals(AppConfig.Companion.KEY_RELEASE_DATE)) {
                    Comparator<MovieResult> byReleaseDate = Comparator.comparing(MovieResult::getReleaseDate);
                    listRs.sort(byReleaseDate.reversed());
                } else if (sortBy != null && sortBy.equals(AppConfig.Companion.KEY_RATING)) {
                    Comparator<MovieResult> byRating = Comparator.comparing(MovieResult::getVoteAverage);
                    listRs.sort(byRating.reversed());
                }
                //set list remote
                if(isRefresh){
                    listRemote.clear();
                    listRemote.addAll(listRs);
                } else if(isLoadmore) {
                    listRemote.addAll(listRs);
                } else {
                    listRemote.addAll(listRs);
                }
                listRemote.remove(null);
                ldListMovieRemote.postValue(listRemote);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }
        });
    }
    public void getListMovieLocal() {
        getListMoviesUseCase.getListMovieLocal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<MovieResult>>() {
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

    public void deleteMovieFavourite(int movieId) {
        deleteMovieInLocalUseCase.deleteFavouriteMovie(movieId);
        getListMovieLocal();
    }

    public boolean isFavouriteMovie(int movieId, List<MovieResult> resultList) {
        if (resultList == null || resultList.isEmpty()) return false;
        else {
            for (MovieResult item : resultList) {
                if (item.getId() == movieId) return true;
            }
            return false;
        }
    }

    public void getKeyTopicSharedPreferences() {
        String topicKey = getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_TOPIC, AppConfig.Companion.POPULAR);
        ldFilterTopic.setValue(topicKey);
    }

    public void getPointSharedPreferences() {
        float point = getSettingsInforSharedPreferenceUseCase.getFloat(AppConfig.Companion.KEY_POINT);
        ldFilterPoint.setValue(point);
    }

    public void getKeySortSharedPreferences() {
        String keySort = getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_SORT, "");
        ldSortBy.setValue(keySort);
    }

    public void getYearSharedPreferences() {
        int year = getSettingsInforSharedPreferenceUseCase.getInt(AppConfig.Companion.KEY_YEAR);
        ldYear.setValue(year);
    }

    public boolean isLoadingAgain(){
        String topicKey = getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_TOPIC, AppConfig.Companion.POPULAR);
        float point = getSettingsInforSharedPreferenceUseCase.getFloat(AppConfig.Companion.KEY_POINT);
        String keySort = getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_SORT, "");
        int year = getSettingsInforSharedPreferenceUseCase.getInt(AppConfig.Companion.KEY_YEAR);
        if( !(Objects.equals(ldFilterTopic.getValue(), topicKey)) || !(point == ldFilterPoint.getValue() ||
                !(keySort.equals(ldSortBy.getValue())) || !(year == ldYear.getValue()))){
            ldFilterTopic.setValue(topicKey);
            ldFilterPoint.setValue(point);
            ldSortBy.setValue(keySort);
            ldYear.setValue(year);
            ldIsLoadingAgain.setValue(true);
            return true;
        }
        ldIsLoadingAgain.setValue(false);
        return false;
    }
}
