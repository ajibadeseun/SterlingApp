package com.babycoder.sterlingapp.ui.teams;

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
import com.babycoder.sterlingapp.data.model.teams.Team;
import com.babycoder.sterlingapp.data.model.teams.Teams;
import com.babycoder.sterlingapp.ui.table.TableViewModel;
import com.babycoder.sterlingapp.util.ItemSelectedListener;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder> {

    private static final String LOG_TAG = TeamsAdapter.class.getSimpleName();
    private ItemSelectedListener selectedListener;
    private List<Team> list = new ArrayList<>();
    private Activity act;

    public TeamsAdapter(Activity activity, TeamsViewModel viewModel, LifecycleOwner lifecycleOwner, ItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
        this.act = activity;
        viewModel.getSelectedFixture().observe(lifecycleOwner, competitions -> {
            list.clear();
            if (competitions != null) {
                list.addAll(competitions.getTeamList());
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public TeamsAdapter.TeamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TeamsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsAdapter.TeamsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        else{
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_teams;
    }

    class TeamsViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.image_team_crest)
        ImageView imageTeamCrest;
        @BindView(R.id.text_team_label)
        TextView textTeamLabel;
        private Team team;
        TeamsViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);

            view.setOnClickListener(v -> {
                if (team != null) {
                    selectedListener.onSelected(team);
                }
            });


        }

        void bind(Team team) {
            this.team = team;
            String teamLabel = team.getTeamName();
            String teamLogo = team.getTeamLogo();

            Picasso.get()
                    .load(teamLogo)
                    .placeholder(R.drawable.soccer_black)
                    .into(imageTeamCrest, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            if (!act.isFinishing()) {
                                Glide.with(act)
                                        .load(Uri.parse(teamLogo)).into(imageTeamCrest);
                            }
                        }
                    });
            textTeamLabel.setText(teamLabel);
        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            Integer id = list.get(position).getTeamId();
//            String team = list.get(position).getTeamName();
//            listener.onListItemClick(position, id, team);
//        }
    }
}
