package com.team34rockets.scoutingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team34rockets.scoutingapp.R;
import com.team34rockets.scoutingapp.models.ScoutingReport;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScoutingQuestionAdapter
        extends RecyclerView.Adapter<ScoutingQuestionAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ScoutingReport dataset;

    public ScoutingQuestionAdapter(ScoutingReport dataset, Context context) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ScoutingQuestionAdapter.ViewHolder holder, int position) {
        String question = dataset.questions.get(position);
        holder.questionView.setText(question);
        Object o = dataset.answers.get(position);
        if (o instanceof Boolean) {
            if ((Boolean) o) {
                holder.answerView.setText("Yes");
            } else {
                holder.answerView.setText("No");
            }
        } else {
            holder.answerView.setText(String.valueOf(o));
        }

    }

    @Override
    public int getItemCount() {
        return dataset.questions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionView;
        TextView answerView;

        ViewHolder(View itemView) {
            super(itemView);
            questionView = itemView.findViewById(R.id.question);
            answerView = itemView.findViewById(R.id.answer);
        }
    }
}
