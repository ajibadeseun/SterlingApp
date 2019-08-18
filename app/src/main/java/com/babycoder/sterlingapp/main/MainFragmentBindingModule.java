package com.babycoder.sterlingapp.main;

import com.babycoder.sterlingapp.ui.competitions.CompetitionsFragment;
import com.babycoder.sterlingapp.ui.fixtures.FixturesFragment;
import com.babycoder.sterlingapp.ui.table.TableFragment;
import com.babycoder.sterlingapp.ui.teams.TeamsFragment;
import com.babycoder.sterlingapp.ui.todaysFixtures.TodaysFixtureFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract CompetitionsFragment provideCompetitionsFragment();

    @ContributesAndroidInjector
    abstract TodaysFixtureFragment provideTodaysFixtureFragment();





}
