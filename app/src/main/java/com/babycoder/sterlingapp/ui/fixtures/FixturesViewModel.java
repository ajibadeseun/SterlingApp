package com.babycoder.sterlingapp.ui.fixtures;

import android.os.Bundle;

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

public class FixturesViewModel extends ViewModel {
    private final GameRepository gameRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<Matches> selectedGame = new MutableLiveData<>();
    private final MutableLiveData<Integer> id = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();





    @Inject
    public FixturesViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        disposable = new CompositeDisposable();


    }


    LiveData<Boolean> getError() {
        return gameLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<Matches> getSelectedGame() {
        return selectedGame;
    }

    public LiveData<Integer> getMatchId(){
        return id;
    };


    public void restoreFromBundle(Bundle savedInstanceState){
        if(id != null) {

            fetchStandings((int)id.getValue());
        }
    }

    public void saveToBundle(Bundle outState) {
        if(id.getValue() != null) {
            outState.putInt("competition_id",
                    id.getValue());
        }
    }

    public void setMatchId(Integer repo) {
        id.setValue(repo);
    }


    private void fetchStandings(int gameId) {
        loading.setValue(true);
        disposable.add(gameRepository.getMatch(gameId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Matches>() {
                    @Override
                    public void onSuccess(Matches value) {
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
