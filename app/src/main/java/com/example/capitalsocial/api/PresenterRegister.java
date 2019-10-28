package com.example.capitalsocial.api;

import android.util.Log;
import com.example.capitalsocial.models.Register;
import com.example.capitalsocial.utilities.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PresenterRegister implements Callback<Register> {

    public ListenerRegister listener;

    public void attempRegister(String email, String password){
        Retrofit retrofit = Utils.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Register> request = apiService.register(email, password);
        request.enqueue(this);
        if(listener != null){
            listener.showProgress();
        }
    }

    @Override
    public void onResponse(Call<Register> call, Response<Register> response) {
        if(response.isSuccessful()){
            Register register = response.body();
            if(register != null && register.error == null) {
                if(listener != null) {
                    listener.successEntry(register);
                }
            } else {
                if(listener != null){
                    listener.showErrorMessage("Successful is failed");
                }
            }
        } else {
            if(listener != null){
                listener.showErrorMessage("Error");
            }
        }
        if(listener != null){
            listener.hideProgress();
        }
    }

    @Override
    public void onFailure(Call<Register> call, Throwable t) {
        if(listener != null){
            listener.hideProgress();
            listener.showErrorMessage(t.getMessage());
        }
    }
}
