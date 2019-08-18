package com.babycoder.sterlingapp.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.babycoder.sterlingapp.ui.competitions.CompetitionsViewModel;
import com.babycoder.sterlingapp.ui.fixtures.FixturesViewModel;
import com.babycoder.sterlingapp.ui.table.TableViewModel;
import com.babycoder.sterlingapp.ui.teams.TeamsViewModel;
import com.babycoder.sterlingapp.ui.todaysFixtures.TodaysFixturesViewModel;
import com.babycoder.sterlingapp.util.ViewModelFactory;
import com.babycoder.sterlingapp.util.ViewModelKey;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;



@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CompetitionsViewModel.class)
    abstract ViewModel bindCompetitionsViewModel(CompetitionsViewModel competitionsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FixturesViewModel.class)
    abstract ViewModel bindFixturesViewModel(FixturesViewModel fixturesViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TableViewModel.class)
    abstract ViewModel bindTableViewModel(TableViewModel tableViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TeamsViewModel.class)
    abstract ViewModel bindTeamsViewModel(TeamsViewModel teamsViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TodaysFixturesViewModel.class)
    abstract ViewModel bindTodaysFixturesViewModel(TodaysFixturesViewModel teamsViewModel);




    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
