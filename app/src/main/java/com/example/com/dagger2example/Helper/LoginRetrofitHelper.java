package com.example.com.dagger2example.Helper;

import com.example.com.dagger2example.Model.User;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by raul on 07/11/2016.
 */

public class LoginRetrofitHelper {
    public static final String BASE_URL = "http://www.mocky.io";

    public static class Factory {
        public static Retrofit create() {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }

        public static Observable<List<User>> createLogin(String key) {
            Retrofit retrofit = create();
            LoginService loginService = retrofit.create(LoginService.class);
            return loginService.getUsers(key);
        }
    }

    public interface LoginService {
        @GET("/v2/{key}")
        Observable<List<User>> getUsers(@Path("key") String key);
    }
}
