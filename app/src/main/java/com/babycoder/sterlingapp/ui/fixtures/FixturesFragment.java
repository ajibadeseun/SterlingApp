package com.babycoder.sterlingapp.ui.fixtures;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.base.BaseFragment;
import com.babycoder.sterlingapp.main.CompetitionActivity;
import com.babycoder.sterlingapp.ui.table.StandingsAdapter;
import com.babycoder.sterlingapp.ui.table.TableViewModel;
import com.babycoder.sterlingapp.util.ItemSelectedListener;
import com.babycoder.sterlingapp.util.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class FixturesFragment extends BaseFragment implements ItemSelectedListener {
    @BindView(R.id.list_competition_matches)
    RecyclerView list;

    @Inject
    ViewModelFactory viewModelFactory;
    private FixturesViewModel fixturesViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_matches;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fixturesViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(FixturesViewModel.class);
        CompetitionActivity competitionActivity = (CompetitionActivity) getActivity();
        int competitionId = competitionActivity.getCompetitionId();
        fixturesViewModel.setMatchId(competitionId);

        fixturesViewModel.restoreFromBundle(savedInstanceState);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        list.setAdapter(new MatchesAdapter(getBaseActivity(),fixturesViewModel, this, this));
        displayFixtures();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        fixturesViewModel.saveToBundle(outState);
    }

    private void displayFixtures(){
        fixturesViewModel.getSelectedGame().observe(this, repo -> {
            if (repo != null) {
                list.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onSelected(Object object) {

    }
}
