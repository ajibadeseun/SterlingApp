package com.babycoder.sterlingapp.ui.table;

import android.app.Activity;
import android.net.Uri;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.data.model.standings.Standing;
import com.babycoder.sterlingapp.data.model.standings.Standings;
import com.babycoder.sterlingapp.data.model.standings.Table;
import com.babycoder.sterlingapp.ui.competitions.CompetitionsViewModel;
import com.babycoder.sterlingapp.util.ItemSelectedListener;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder> {

    private static final String LOG_TAG = StandingsAdapter.class.getSimpleName();
    private ItemSelectedListener selectedListener;
    private List<Table> list = new ArrayList<>();
    private Activity act;

    public StandingsAdapter(Activity activity,TableViewModel viewModel, LifecycleOwner lifecycleOwner, ItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
        this.act = activity;
        viewModel.getSelectedGame().observe(lifecycleOwner, competitions -> {
            list.clear();
            if (competitions != null) {
                list.addAll(competitions.getStandingList().get(0).getTableList());
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public StandingsAdapter.StandingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new StandingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StandingsAdapter.StandingsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list != null ){
            return list.size();
        }
        return 0;

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_standings;
    }

    class StandingsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_team_logo)
        ImageView imageStandingTeam;
        @BindView(R.id.text_table_position)
        TextView textTeamPosition;
        @BindView(R.id.text_team_name)
        TextView textTeamName;
        @BindView(R.id.text_team_played)
        TextView textTeamPlayed;
        @BindView(R.id.text_team_difference)
        TextView textTeamDifference;
        @BindView(R.id.text_team_points)
        TextView textTeamPoints;

        private Table table;
        StandingsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {
                if (table != null) {
                    selectedListener.onSelected(table);
                }
            });




        }

        void bind(Table table) {
            this.table = table;
            Integer teamPosition = table.getTablePosition();
            String teamName = table.getTableTeam().getTeamName();
            String teamLogo = table.getTableTeam().getTeamLogo();
            Integer teamPlayed = table.getTablePlayed();
            Integer teamDifference = table.getTableDifference();
            Integer teamPoints = table.getTablePoints();

            if(teamLogo != null && !teamLogo.isEmpty()){
                Picasso.get()
                        .load(teamLogo)
                        .placeholder(R.drawable.soccer_black)
                        .into(imageStandingTeam, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                if (!act.isFinishing()) {
                                    Glide.with(act)
                                            .load(Uri.parse(teamLogo))
                                            .centerCrop()
                                            .placeholder(R.drawable.soccer_black)
                                            .into(imageStandingTeam) ;
                                }
                            }
                        });
            }


            textTeamPosition.setText(String.format("%d", teamPosition));
            textTeamName.setText(teamName);
            textTeamPlayed.setText(String.format("%d", teamPlayed));
            textTeamDifference.setText(String.format("%d", teamDifference));
            textTeamPoints.setText(String.format("%d", teamPoints));
        }


    }
}
