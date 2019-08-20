package com.babycoder.sterlingapp.ui.table;

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
        //Retry button pressed
        retryBtn.setOnClickListener(v->{
            displayTable();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        tableViewModel.saveToBundle(outState);
    }

    private void displayTable(){
        tableViewModel.getSelectedGame().observe(this, repo -> {
            if (repo != null) {
                if(repo.getStandingList().size() > 0){
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

        tableViewModel.getError().observe(this,error ->{
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
        tableViewModel.getLoading().observe(this,loading ->{
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
