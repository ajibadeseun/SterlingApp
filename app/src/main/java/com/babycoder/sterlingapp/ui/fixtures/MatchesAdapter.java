package com.babycoder.sterlingapp.ui.fixtures;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.data.model.matches.Match;
import com.babycoder.sterlingapp.ui.table.TableViewModel;
import com.babycoder.sterlingapp.util.ItemSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.FixturesViewHolder> {

    private static final String LOG_TAG = MatchesAdapter.class.getSimpleName();
    private ItemSelectedListener selectedListener;
    private List<Match> list = new ArrayList<>();
    private Activity activity;

    public MatchesAdapter(Activity activity, FixturesViewModel viewModel, LifecycleOwner lifecycleOwner
            , ItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
        this.activity = activity;
        viewModel.getSelectedGame().observe(lifecycleOwner, competitions -> {
            list.clear();
            if (competitions != null) {
                list.addAll(competitions.getMatchList());
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public MatchesAdapter.FixturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new FixturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.FixturesViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_matches;
    }

    class FixturesViewHolder extends RecyclerView.ViewHolder  {

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

        FixturesViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);
            view.setOnClickListener(v -> {
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

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            int id = list.get(position).getMatchId();
//            String name = "";
//
//            try {
//                name = list.get(position).getMatchCompetition().getCompetitionName();
//
//                if (name == null || name.equals("null")) {
//                    name = "";
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            listener.onListItemClick(position, id, name);
//        }
    }
}
