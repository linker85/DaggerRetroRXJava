package com.example.com.dagger2example;

import com.example.com.dagger2example.Helper.LoginRetrofitHelper;
import com.example.com.dagger2example.Model.User;

import java.util.List;

import dagger.Module;
import dagger.Provides;
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
@Module
public class MyRetroModule {

    private String key = "";

    MyRetroModule (String key) {
        this.key = key;
    }


    @Provides
    public Retrofit provideRetrofitFactory() {
        return new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    public Observable<List<User>> provideCreateLogin(Retrofit retrofit) {
        LoginRetrofitHelper.LoginService loginService = retrofit.create(LoginRetrofitHelper.LoginService.class);
        return loginService.getUsers(key);
    }


    public interface LoginService {
        @GET("/v2/{key}")
        Observable<List<User>> getUsers(@Path("key") String key);
    }
}