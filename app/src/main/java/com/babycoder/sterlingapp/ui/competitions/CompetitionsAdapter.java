package com.babycoder.sterlingapp.ui.competitions;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.babycoder.sterlingapp.R;
import com.babycoder.sterlingapp.data.model.competitions.Competition;
import com.babycoder.sterlingapp.util.ItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.CompetitionsViewHolder> {

    private static final String LOG_TAG = CompetitionsAdapter.class.getSimpleName();
    private ItemSelectedListener selectedListener;

    private List<Competition> list = new ArrayList<>();

    public CompetitionsAdapter(CompetitionsViewModel viewModel, LifecycleOwner lifecycleOwner, ItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
        viewModel.getSelectedGame().observe(lifecycleOwner, competitions -> {
            list.clear();
            if (competitions != null) {
                list.addAll(competitions.getCompetitionList());
                notifyDataSetChanged();
            }
        });
//        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CompetitionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new CompetitionsViewHolder(view,selectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CompetitionsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list.size() >0){
            return list.size();
        }
        else{
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_competitions;
    }



    public class CompetitionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_competition_name)
        TextView textCompetitionName;

        private Competition competition;

        CompetitionsViewHolder(View view, ItemSelectedListener selectedListener) {
            super(view);

            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {
                if (competition != null) {
                    selectedListener.onSelected(competition);
                }
            });


        }

        void bind(Competition competition) {
            this.competition = competition;
            textCompetitionName.setText(competition.getCompetitionName());
        }


    }
}
