package com.babycoder.sterlingapp.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.base.BaseActivity;
import com.babycoder.sterlingapp.ui.competitions.CompetitionAdapter;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompetitionActivity extends BaseActivity {
    private static final String KEY_ID = "competition_id";
    private static final String KEY_NAME = "competition_name";
    private int competitionId;
    private String competitionName;



    public int getCompetitionId() {
        return competitionId;
    }

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected int layoutRes() {
        return R.layout.activity_competition;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        competitionId = intent.getIntExtra("competitionId", 0);
        competitionName = intent.getStringExtra("competitionName");


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_ID)) {
                competitionId = savedInstanceState.getInt(KEY_ID);
            }

            if (savedInstanceState.containsKey(KEY_NAME)) {
                competitionName = savedInstanceState.getString(KEY_NAME);
            }
        }

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(competitionName);

        CompetitionAdapter adapter = new CompetitionAdapter(this, getSupportFragmentManager());


        viewPager.setAdapter(adapter);


        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putInt(KEY_ID, competitionId);
        bundle.putString(KEY_NAME, competitionName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
