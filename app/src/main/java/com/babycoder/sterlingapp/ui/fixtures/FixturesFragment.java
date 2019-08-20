package com.babycoder.sterlingapp.ui.fixtures;

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

public class FixturesFragment extends BaseFragment implements ItemSelectedListener {
    @BindView(R.id.list_competition_matches)
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

        retryBtn.setOnClickListener(v->{
            displayFixtures();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        fixturesViewModel.saveToBundle(outState);
    }

    private void displayFixtures(){
        fixturesViewModel.getSelectedGame().observe(this, repo -> {
            if (repo != null) {
                if(repo.getMatchList().size() > 0){
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
        fixturesViewModel.getError().observe(this,error->{
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
        fixturesViewModel.getLoading().observe(this,loading->{
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
