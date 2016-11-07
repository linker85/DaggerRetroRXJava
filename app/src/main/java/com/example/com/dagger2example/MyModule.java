package com.example.com.dagger2example;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by raul on 07/11/2016.
 */

@Module
public class MyModule {

    private Context context;

    public MyModule (Context context) {
        this.context = context;
    }

    @Provides
    @Named ("MySharedPreferences1")
    public SharedPreferences provideSharedType1() {
        //return PreferenceManager.getDefaultSharedPreferences(this.context);
        return context.getSharedPreferences("MySharedPreferences1", Context.MODE_PRIVATE);
    }

    @Provides
    @Named ("MySharedPreferences2")
    public SharedPreferences provideSharedType2() {
        //return PreferenceManager.getDefaultSharedPreferences(this.context);
        return context.getSharedPreferences("MySharedPreferences2", Context.MODE_PRIVATE);
    }

    /*
    * SharedPreferences sharedPreferences1 comes from the 1sr SharedPreferences injected
    * */
    @Provides
    @Named ("MySharedPreferences1")
    public SharedPreferences.Editor provideEditor1(@Named ("MySharedPreferences1") SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    @Provides
    @Named ("MySharedPreferences2")
    public SharedPreferences.Editor provideEditor2(@Named ("MySharedPreferences2") SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

}