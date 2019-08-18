package com.babycoder.sterlingapp.data.rest;

import com.babycoder.sterlingapp.data.model.competitions.Competitions;
import com.babycoder.sterlingapp.data.model.matches.Matches;
import com.babycoder.sterlingapp.data.model.standings.Standings;
import com.babycoder.sterlingapp.data.model.teams.Teams;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Call;

public class GameRepository {
    private final ApiService apiService;

    @Inject
    public GameRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Competitions> getCompetitions() {
        return apiService.getCompetitions();
    }


    public Single<Matches> getFixtures(){return apiService.getFixtures();}

    public Single<Standings> getStandings(int id){return apiService.getStandings(id);}

    public Single<Matches> getMatch(int id){return  apiService.getMatches(id);}

    public Single<Teams> getTeams(int id){return  apiService.getTeams(id);}


}
