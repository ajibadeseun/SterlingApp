package com.babycoder.sterlingapp.ui.competitions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.babycoder.sterlingapp.data.model.competitions.Competitions;
import com.babycoder.sterlingapp.data.rest.GameRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CompetitionsViewModel extends ViewModel {
    private final GameRepository gameRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<Competitions> selectedGame = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public CompetitionsViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        disposable = new CompositeDisposable();
        fetchCompetitions();
    }


    LiveData<Boolean> getError() {
        return gameLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<Competitions> getSelectedGame() {
        return selectedGame;
    }


    private void fetchCompetitions() {
        loading.setValue(true);
        disposable.add(gameRepository.getCompetitions().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Competitions>() {
                    @Override
                    public void onSuccess(Competitions value) {
                        if(value != null){
                            gameLoadError.setValue(false);
                            selectedGame.setValue(value);
                            loading.setValue(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        gameLoadError.setValue(true);
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
