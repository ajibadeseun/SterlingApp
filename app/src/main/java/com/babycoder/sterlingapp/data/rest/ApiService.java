package com.babycoder.sterlingapp.data.rest;



import com.babycoder.sterlingapp.Configs;
import com.babycoder.sterlingapp.data.model.competitions.Competitions;
import com.babycoder.sterlingapp.data.model.matches.Matches;
import com.babycoder.sterlingapp.data.model.standings.Standings;
import com.babycoder.sterlingapp.data.model.teams.Team;
import com.babycoder.sterlingapp.data.model.teams.Teams;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiService {

    @Headers(Configs.BASE_KEY)
    @GET("/v2/competitions")
    Single<Competitions> getCompetitions();

    @Headers(Configs.BASE_KEY)
    @GET("/v2/matches")
    Single<Matches> getFixtures();

    @Headers(Configs.BASE_KEY)
    @GET("/v2/competitions/{id}/matches")
    Single<Matches> getMatches(@Path("id") int id);

    @Headers(Configs.BASE_KEY)
    @GET("/v2/competitions/{id}/standings")
    Single<Standings> getStandings(@Path("id") int id);

    @Headers(Configs.BASE_KEY)
    @GET("/v2/competitions/{id}/teams")
    Single<Teams> getTeams(@Path("id") int id);

    @Headers(Configs.BASE_KEY)
    @GET("/v2/teams/{id}")
    Single<Team> getPlayers(@Path("id") int id);
}
