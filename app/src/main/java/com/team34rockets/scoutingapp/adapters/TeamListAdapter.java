package com.team34rockets.scoutingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team34rockets.scoutingapp.R;
import com.team34rockets.scoutingapp.models.Competition;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Competition dataset;
    private ItemClickListener itemClickListener;

    public TeamListAdapter(Competition dataset, Context context) {
        this.dataset = dataset;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.team_row_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.teamNumberTextView.setText(String.valueOf(dataset.getTeam(position).getNumber()));
        holder.teamNameTextView.setText(dataset.getTeam(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataset.getTeams();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView teamNameTextView;
        TextView teamNumberTextView;

        ViewHolder(View itemView) {
            super(itemView);
            teamNameTextView = itemView.findViewById(R.id.teamRowName);
            teamNumberTextView = itemView.findViewById(R.id.teamRowNumber);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
