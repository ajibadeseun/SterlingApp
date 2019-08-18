package com.babycoder.sterlingapp.ui.todaysFixtures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.data.model.competitions.Competition;
import com.babycoder.sterlingapp.data.model.matches.Match;
import com.babycoder.sterlingapp.ui.competitions.CompetitionsAdapter;
import com.babycoder.sterlingapp.ui.competitions.CompetitionsViewModel;
import com.babycoder.sterlingapp.util.ItemSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodaysFixtureAdapter extends RecyclerView.Adapter<TodaysFixtureAdapter.TodaysFixtureViewHolder> {
    private ItemSelectedListener selectedListener;

    private List<Match> list = new ArrayList<>();

    public TodaysFixtureAdapter(TodaysFixturesViewModel viewModel, LifecycleOwner lifecycleOwner, ItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
        viewModel.getSelectedFixture().observe(lifecycleOwner, matches -> {
            list.clear();
            if (matches != null) {
                list.addAll(matches.getMatchList());
                notifyDataSetChanged();
            }
        });
//        setHasStableIds(true);
    }

    @NonNull
    @Override
    public TodaysFixtureAdapter.TodaysFixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TodaysFixtureAdapter.TodaysFixtureViewHolder(view,selectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodaysFixtureAdapter.TodaysFixtureViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_matches;
    }

    public class TodaysFixtureViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_match_status)
        TextView textMatchStatus;
        @BindView(R.id.text_match_time)
        TextView textMatchTime;
        @BindView(R.id.text_match_day)
        TextView textMatchDay;
        @BindView(R.id.text_home_team)
        TextView textHomeTeam;
        @BindView(R.id.text_away_team)
        TextView textAwayTeam;
        @BindView(R.id.text_home_wins)
        TextView textHomeWins;
        @BindView(R.id.text_away_wins)
        TextView textAwayWins;

        private Match match;

        public TodaysFixtureViewHolder(@NonNull View itemView,ItemSelectedListener selectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (match != null) {
                    selectedListener.onSelected(match);
                }
            });

        }

        void bind(Match match) {
            this.match = match;
            String matchStatus = match.getMatchStatus();
            String matchTime = match.getMatchDate().substring(11, 16);
            String matchHomeTeam = match.getMatchHomeTeam().getHomeTeamName();
            String matchAwayTeam = match.getMatchAwayTeam().getAwayTeamName();

            try {
                String matchDay = String.format("MD: %d", match.getMatchDay());
                String matchHomeWins = String.valueOf(match.getMatchScore().getFullTime().getHomeTeamWin());
                String matchAwayWins = String.valueOf(match.getMatchScore().getFullTime().getAwayTeamWin());

                if (matchDay == null || matchDay.equals("null")) {
                    matchDay = "-";
                }
                textMatchDay.setText(matchDay);

                if (matchHomeWins == null || matchHomeWins.equals("null")) {
                    matchHomeWins = "-";
                }
                textHomeWins.setText(matchHomeWins);

                if (matchAwayWins == null || matchAwayWins.equals("null")) {
                    matchAwayWins = "-";
                }
                textAwayWins.setText(matchAwayWins);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            textMatchStatus.setText(matchStatus);
            textMatchTime.setText(matchTime);
            textHomeTeam.setText(matchHomeTeam);
            textAwayTeam.setText(matchAwayTeam);
        }
    }
}
