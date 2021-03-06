package com.example.com.dagger2example;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.com.dagger2example.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG_";
    @Inject @Named ("MySharedPreferences1")
    SharedPreferences sharedPreferences1;
    @Inject @Named ("MySharedPreferences2")
    SharedPreferences sharedPreferences2;
    @Inject @Named ("MySharedPreferences1")
    SharedPreferences.Editor editor1;
    @Inject @Named ("MySharedPreferences2")
    SharedPreferences.Editor editor2;

    @Inject
    Observable<List<User>> retroModule;

    @Inject
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // It will be red at the begging, rebuild to autogenerate the class
        DaggerMyComponent.builder().
                myModule(new MyModule(getApplicationContext())). // context
                myRetroModule(new MyRetroModule("11")).
                build().
                inject(this); // instance

        try {
            Request request = new Request.Builder()
                    .url("http://www.google.com")
                    .build();
            Response responses = null;

            try {
                responses = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonData = responses.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            JSONArray Jarray = Jobject.getJSONArray("employees");

            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject object     = Jarray.getJSONObject(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        retroModule
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<User> users) {

                    }
                });



    }

    public void doMagic(View view) {
        editor2.putString("new_date", "" + new Date());
        editor2.commit();
        String date = sharedPreferences2.getString("new_date", "0");

        editor1.putString("new_date", "a");
        editor1.commit();
        String some = sharedPreferences1.getString("new_date", "0");

        Log.d(TAG, "doMagic: " + date + "-" + some);
    }
}