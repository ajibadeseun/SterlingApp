package com.babycoder.sterlingapp.ui.table;

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
import com.babycoder.sterlingapp.ui.competitions.CompetitionsAdapter;
import com.babycoder.sterlingapp.ui.competitions.CompetitionsViewModel;
import com.babycoder.sterlingapp.util.ItemSelectedListener;
import com.babycoder.sterlingapp.util.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableFragment  extends BaseFragment implements ItemSelectedListener {
    @BindView(R.id.list_competition_standings)
    RecyclerView list;


    @Inject
    ViewModelFactory viewModelFactory;
    private TableViewModel tableViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_standings;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        tableViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(TableViewModel.class);
        CompetitionActivity competitionActivity = (CompetitionActivity) getActivity();
        int competitionId = competitionActivity.getCompetitionId();
        tableViewModel.setMatchId(competitionId);

        tableViewModel.restoreFromBundle(savedInstanceState);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        list.setAdapter(new StandingsAdapter(getBaseActivity(),tableViewModel, this, this));
        displayTable();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        tableViewModel.saveToBundle(outState);
    }

    private void displayTable(){
        tableViewModel.getSelectedGame().observe(this, repo -> {
            if (repo != null) {
                list.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onSelected(Object object) {

    }
}
