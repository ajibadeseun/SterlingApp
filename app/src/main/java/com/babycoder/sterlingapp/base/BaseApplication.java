package com.babycoder.sterlingapp.base;

import android.app.Activity;
import android.app.Application;

import com.babycoder.sterlingapp.di.component.ApplicationComponent;
import com.babycoder.sterlingapp.di.component.DaggerApplicationComponent;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends Application implements HasActivityInjector {
    private static BaseApplication sInstance;


    public static BaseApplication getAppContext() {
        return sInstance;
    }

    private static synchronized void setInstance(BaseApplication app) {
        sInstance = app;
    }

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        setInstance(this);
    }

    private void initializeComponent() {
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return  activityDispatchingInjector;
    }
}
