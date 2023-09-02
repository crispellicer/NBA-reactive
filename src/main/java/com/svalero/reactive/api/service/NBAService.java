package com.svalero.reactive.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svalero.reactive.api.model.DataPlayer;
import com.svalero.reactive.api.model.DataTeam;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NBAService {

    private String BASE_URL = "https://balldontlie.io";

    private NBAApi  nbaApi;

    public NBAService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.nbaApi = retrofit.create(NBAApi.class);
    }

    public Observable<DataPlayer> getPlayers() {
        return this.nbaApi.getPlayers();
    }
    public Observable<DataTeam> getTeams() {
        return this.nbaApi.getTeams();
    }
}
