package com.example.capitalsocial.api;

import com.example.capitalsocial.models.Register;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<Register> register(@Field("email") String email,
                            @Field("password") String password);

}
