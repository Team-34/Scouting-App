package com.team34rockets.scoutingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team34rockets.scoutingapp.R;
import com.team34rockets.scoutingapp.models.ScoutingReport;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScoutingQuestionAdapter
        extends RecyclerView.Adapter<ScoutingQuestionAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ScoutingReport> dataset;

    private ItemClickListener itemClickListener;

    public ScoutingQuestionAdapter(List<ScoutingReport> dataset, Context context) {
        this.dataset = dataset;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ScoutingQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.short_answer_row, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScoutingQuestionAdapter.ViewHolder holder, int position) {
        ScoutingReport scoutingReport = dataset.get(position);

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

        TextView questionView;
        TextView answerView;

        ViewHolder(View itemView) {
            super(itemView);
            questionView = itemView.findViewById(R.id.question);
            answerView = itemView.findViewById(R.id.answer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
