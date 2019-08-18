package com.babycoder.sterlingapp.ui.todaysFixtures;

import android.os.Bundle;
import android.view.View;
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
import com.babycoder.sterlingapp.ui.competitions.CompetitionsViewModel;
import com.babycoder.sterlingapp.util.ItemSelectedListener;
import com.babycoder.sterlingapp.util.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class TodaysFixtureFragment extends BaseFragment implements ItemSelectedListener {


    @BindView(R.id.list_fixtures)
    RecyclerView list;


    @BindView(R.id.tv_error)
    TextView errorTextView;

    @BindView(R.id.loading_view) View loadingView;

    @Inject
    ViewModelFactory viewModelFactory;
    private TodaysFixturesViewModel todaysFixturesViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_fixtures;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        todaysFixturesViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(TodaysFixturesViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        list.setAdapter(new TodaysFixtureAdapter(todaysFixturesViewModel, this, this));
        displayCompetitions();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    private void displayCompetitions() {
        todaysFixturesViewModel.getSelectedFixture().observe(this, competitions -> {
            if (competitions != null) {
                list.setVisibility(View.VISIBLE);
            }
        });

        todaysFixturesViewModel.getError().observe(this,isError->{
            if (isError != null) if(isError) {
                errorTextView.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            }else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        todaysFixturesViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onSelected(Object object) {

    }
}
