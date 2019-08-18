package com.babycoder.sterlingapp.ui.teams;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.babycoder.sterlingapp.data.model.matches.Matches;
import com.babycoder.sterlingapp.data.model.teams.Teams;
import com.babycoder.sterlingapp.data.rest.GameRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TeamsViewModel extends ViewModel {
    private final GameRepository gameRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<Teams> selectedTeams = new MutableLiveData<>();
    private final MutableLiveData<Boolean> fixtureLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Integer> id = new MutableLiveData<>();




    @Inject
    public TeamsViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        disposable = new CompositeDisposable();


    }

    public void saveToBundle(Bundle outState) {
        if(id.getValue() != null) {
            outState.putInt("competition_id",
                    id.getValue());
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState){
        if(id != null) {

            fetchTeams((int)id.getValue());
        }
    }

    public void setMatchId(Integer repo) {
        id.setValue(repo);
    }

    LiveData<Boolean> getError() {
        return fixtureLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<Teams> getSelectedFixture() {
        return selectedTeams;
    }
    public LiveData<Integer> getMatchId(){
        return id;
    };


    private void fetchTeams(int id) {
        loading.setValue(true);
        disposable.add(gameRepository.getTeams(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Teams>() {
                    @Override
                    public void onSuccess(Teams value) {
                        if(value != null){
                            fixtureLoadError.setValue(false);
                            selectedTeams.setValue(value);
                            loading.setValue(false);
                        }

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
