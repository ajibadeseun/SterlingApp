package com.babycoder.sterlingapp.di.component;

import android.app.Application;

import com.babycoder.sterlingapp.base.BaseApplication;
import com.babycoder.sterlingapp.di.module.ActivityBindingModule;
import com.babycoder.sterlingapp.di.module.ApplicationModule;
import com.babycoder.sterlingapp.di.module.ContextModule;
import com.babycoder.sterlingapp.main.CompetitionFragmentBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;



@Component(modules = {ContextModule.class, ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}