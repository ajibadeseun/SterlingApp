package com.babycoder.sterlingapp.ui.table;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.babycoder.sterlingapp.data.model.competitions.Competitions;
import com.babycoder.sterlingapp.data.model.matches.Matches;
import com.babycoder.sterlingapp.data.model.standings.Standings;
import com.babycoder.sterlingapp.data.rest.GameRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TableViewModel  extends ViewModel {
    private final GameRepository gameRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<Standings> selectedGame = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Integer> id = new MutableLiveData<>();



    @Inject
    public TableViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        disposable = new CompositeDisposable();


    }

    LiveData<Boolean> getError() {
        return gameLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<Standings> getSelectedGame() {
        return selectedGame;
    }
    public LiveData<Integer> getMatchId(){
        return id;
    };

    public void setMatchId(int repo) {
        id.setValue(repo);
    }


    public void restoreFromBundle(Bundle savedInstanceState){
        if(id != null ) {
          int gameId = (int)id.getValue();

          fetchGames(gameId);
        }
    }


    public void saveToBundle(Bundle outState) {
        if(id.getValue() != null) {
            outState.putInt("competition_id",
                    id.getValue()
            );
        }
    }

    private void fetchGames(int gameId) {


        loading.setValue(true);
        disposable.add(gameRepository.getStandings(gameId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Standings>() {
                    @Override
                    public void onSuccess(Standings value) {
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
