package com.svalero.reactive.api.service;

import com.svalero.reactive.api.model.DataPlayer;
import com.svalero.reactive.api.model.DataTeam;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NBAApi {

    //obtener lista de jugadores
    @GET("api/v1/players")
    Observable<DataPlayer> getPlayers();

    //obtener lista de equipos
    @GET("api/v1/teams")
    Observable<DataTeam> getTeams();
}
