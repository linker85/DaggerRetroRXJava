package com.example.com.dagger2example;

import dagger.Component;

/**
 * Created by raul on 07/11/2016.
 */
//@Singleton // 1 Instance in the whole application
@Component (modules = {MyModule.class, MyRetroModule.class}) // can receive modules or dependencies
public interface MyComponent {
    // Where to inject
    void inject(MainActivity mainActivity);
}