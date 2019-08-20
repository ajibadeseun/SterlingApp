package com.babycoder.sterlingapp.ui.teams;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    @BindView(R.id.no_fixtures_layout)
    LinearLayout noTableLayout;
    @BindView(R.id.retryBtn)
    Button retryBtn;
    @BindView(R.id.tv_error)
    TextView errorText;
    @BindView(R.id.loading_view)
    ProgressBar progressBar;

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

        //Retry button clicked
        retryBtn.setOnClickListener(v->{
            displayTeams();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        teamsViewModel.saveToBundle(outState);
    }

    private void displayTeams(){
        teamsViewModel.getSelectedFixture().observe(this, repo -> {
            if (repo != null) {
                if(repo.getTeamList().size() > 0){
                    list.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);
                    noTableLayout.setVisibility(View.GONE);

                }
                else{
                    noTableLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);


                }

            }
        });

        teamsViewModel.getError().observe(this,error ->{
            if(error != null){
                if(error){
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("An error occurred, please try again later");
                    progressBar.setVisibility(View.GONE);
                    noTableLayout.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                }
            }

        });

        teamsViewModel.getLoading().observe(this,loading->{
            if(loading != null){
                if(loading){
                    progressBar.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                    noTableLayout.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onSelected(Object object) {

    }
}
