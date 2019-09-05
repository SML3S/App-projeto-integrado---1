package com.sml3s.trabalho;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private String baseUrl = "https://jsonplaceholder.typicode.com/";;
    private EndPoints api;
    private static RetrofitService instancia;

    private RetrofitService() {
        api = criaRetrofit().create(EndPoints.class);
    }

    public static EndPoints getServico() {
        if (instancia == null)
            instancia = new RetrofitService();
        return instancia.api;
    }

    private Retrofit criaRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

}
