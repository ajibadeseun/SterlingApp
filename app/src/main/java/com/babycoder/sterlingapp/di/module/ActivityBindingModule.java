package com.babycoder.sterlingapp.di.module;

import com.babycoder.sterlingapp.main.CompetitionActivity;
import com.babycoder.sterlingapp.main.CompetitionFragmentBindingModule;
import com.babycoder.sterlingapp.main.MainActivity;
import com.babycoder.sterlingapp.main.MainFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {CompetitionFragmentBindingModule.class})
    abstract CompetitionActivity bindCompetitionActivity ();

}
