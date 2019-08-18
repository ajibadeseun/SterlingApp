package com.babycoder.sterlingapp.ui.teams;

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

public class TeamsFragment extends BaseFragment implements ItemSelectedListener {
    @BindView(R.id.list_competition_teams)
    RecyclerView list;

    @Inject
    ViewModelFactory viewModelFactory;
    private TeamsViewModel teamsViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_teams;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        teamsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(TeamsViewModel.class);
        CompetitionActivity competitionActivity = (CompetitionActivity) getActivity();
        int competitionId = competitionActivity.getCompetitionId();
        teamsViewModel.setMatchId(competitionId);

        teamsViewModel.restoreFromBundle(savedInstanceState);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        list.setAdapter(new TeamsAdapter(getBaseActivity(),teamsViewModel, this, this));

        displayTeams();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        teamsViewModel.saveToBundle(outState);
    }

    private void displayTeams(){
        teamsViewModel.getSelectedFixture().observe(this, repo -> {
            if (repo != null) {
                list.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onSelected(Object object) {

    }
}
