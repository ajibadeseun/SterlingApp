package com.babycoder.sterlingapp.ui.todaysFixtures;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.babycoder.sterlingapp.data.model.competitions.Competitions;
import com.babycoder.sterlingapp.data.model.matches.Matches;
import com.babycoder.sterlingapp.data.rest.GameRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TodaysFixturesViewModel extends ViewModel {
    private final GameRepository gameRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<Matches> selectedFixture = new MutableLiveData<>();
    private final MutableLiveData<Boolean> fixtureLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();



    @Inject
    public TodaysFixturesViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        disposable = new CompositeDisposable();
        fetchFixtures();
    }


    LiveData<Boolean> getError() {
        return fixtureLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<Matches> getSelectedFixture() {
        return selectedFixture;
    }

    private void fetchFixtures() {
        loading.setValue(true);
        disposable.add(gameRepository.getFixtures().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Matches>() {
                    @Override
                    public void onSuccess(Matches value) {
                        fixtureLoadError.setValue(false);
                        selectedFixture.setValue(value);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        fixtureLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }


}
