package com.sml3s.trabalho;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EndPoints {
    @GET("users")
    Call<List<User>> obterUsuarios();
}
