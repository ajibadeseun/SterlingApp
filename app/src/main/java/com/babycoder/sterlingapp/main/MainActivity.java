package com.babycoder.sterlingapp.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.base.BaseActivity;
import com.babycoder.sterlingapp.ui.competitions.CompetitionsFragment;
import com.babycoder.sterlingapp.ui.fixtures.FixturesFragment;
import com.babycoder.sterlingapp.ui.todaysFixtures.TodaysFixtureFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        ActionBar toolbar = getSupportActionBar();
        final int navCompetitions = R.id.navigation_competitions;
        final int navFixtures = R.id.navigation_fixtures;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int id = item.getItemId();
                assert toolbar != null;

                switch (id) {
                    case navCompetitions:
                        Log.d(LOG_TAG, "Competitions tab selected");
                        toolbar.setTitle(R.string.navigation_competitions);
                        fragment = new CompetitionsFragment();
                        loadFragment(fragment);
                        break;
                    case navFixtures:
                    default:
                        Log.d(LOG_TAG, " Fixtures tab selected");
                        toolbar.setTitle(R.string.navigation_fixtures);
                        fragment = new TodaysFixtureFragment();
                        loadFragment(fragment);
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(navFixtures);
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
