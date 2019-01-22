package com.team34rockets.scoutingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team34rockets.scoutingapp.R;
import com.team34rockets.scoutingapp.models.Match;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Match> dataset;

    private ItemClickListener itemClickListener;

    public MatchListAdapter(List<Match> dataset, Context context) {
        this.dataset = dataset;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.match_row_recycler, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match match = dataset.get(position);
        if (match.getResult() == 0) {
            holder.resultTextView.setText("L");
        } else if (match.getResult() == 1) {
            holder.resultTextView.setText("W");
        } else {
            holder.resultTextView.setText("T");
        }
        holder.blueScoreTextView.setText(String.valueOf(match.getBlueScore()));
        holder.redScoreTextView.setText(String.valueOf(match.getRedScore()));
        holder.matchApr.setText(String.valueOf(match.matchApr()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView resultTextView;
        TextView blueScoreTextView;
        TextView redScoreTextView;
        TextView matchApr;

        ViewHolder(View itemView) {
            super(itemView);
            resultTextView = itemView.findViewById(R.id.matchResult);
            blueScoreTextView = itemView.findViewById(R.id.blueScore);
            redScoreTextView = itemView.findViewById(R.id.redScore);
            matchApr = itemView.findViewById(R.id.matchApr);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
