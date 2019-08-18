package com.babycoder.sterlingapp.ui.competitions;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.ui.fixtures.FixturesFragment;
import com.babycoder.sterlingapp.ui.table.TableFragment;
import com.babycoder.sterlingapp.ui.teams.TeamsFragment;


public class CompetitionAdapter extends FragmentPagerAdapter {

    private static final int TABS = 3;
    private Context context;

    public CompetitionAdapter(Context con, FragmentManager fm) {
        super(fm);
        context = con;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TableFragment();
        } else if (position == 1) {
            return new FixturesFragment();
        } else {
            return new TeamsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.category_standings);
        } else if (position == 1) {
            return context.getString(R.string.category_matches);
        } else {
            return context.getString(R.string.category_teams);
        }
    }

    @Override
    public int getCount() {
        return TABS;
    }
}
