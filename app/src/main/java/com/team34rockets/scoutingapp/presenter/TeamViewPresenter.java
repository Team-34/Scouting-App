package com.team34rockets.scoutingapp.presenter;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.team34rockets.scoutingapp.R;
import com.team34rockets.scoutingapp.TeamMatchActivity;
import com.team34rockets.scoutingapp.adapters.MatchListAdapter;
import com.team34rockets.scoutingapp.contracts.TeamViewContract;
import com.team34rockets.scoutingapp.handlers.TBAHandler;
import com.team34rockets.scoutingapp.models.Competition;
import com.team34rockets.scoutingapp.models.Match;
import com.team34rockets.scoutingapp.models.ScoutingReport;
import com.team34rockets.scoutingapp.models.Team;
import com.team34rockets.scoutingapp.models.tbaresults.EventOprsTbaResult;
import com.team34rockets.scoutingapp.models.tbaresults.TeamEventKeysTbaResult;
import com.team34rockets.scoutingapp.models.tbaresults.TeamEventStatusResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamViewPresenter implements TeamViewContract.Presenter {
    private TeamViewContract.View view;
    private Competition competition;
    private MatchListAdapter matchListAdapter;
    private Team team;
    private ProgressBar progressBar;

    @Override
    public void onCreate() {
        progressBar = view.getActivity().findViewById(R.id.progressBar);
        competition = new Gson().fromJson(view.getTeamString(), Competition.class);
        team = competition.getTeam(view.getPosition());
        matchListAdapter = new MatchListAdapter(generateMatches(view.getPosition()),
                view.getContext());
        matchListAdapter.setItemClickListener(new MatchListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(TeamViewPresenter.this.view.getActivity(),
                        TeamMatchActivity.class);
                intent.putExtra("competition", new Gson().toJson(competition));
                intent.putExtra("position", position);
                intent.putExtra("teamNumber", team.getNumber());
                TeamViewPresenter.this.view.getActivity().startActivity(intent);
            }
        });
        new RetrieveInfo().execute();
        view.updateTeamData(competition.getTeam(view.getPosition()), matchListAdapter);
    }

    @Override
    public void matchSelect() {

    }

    private List<Match> generateMatches(int position) {
        List<Match> matches = new ArrayList<>();
        for (ScoutingReport report : competition.getTeam(position).getScoutingReports()) {
            matches.add(new Match(report));
        }

        return matches;
    }

    private class RetrieveInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                TeamEventKeysTbaResult eventKeysTbaResult =
                        TBAHandler.GetKeysData(team.getNumber());
                Log.d("", "");
                EventOprsTbaResult eventOprsTbaResult = TBAHandler.GetStatsData(eventKeysTbaResult
                        .getEvents().get(eventKeysTbaResult.getEvents().indexOf("2019scmb")));
                team.setCcwm(eventOprsTbaResult.getCcwms().get("frc".concat(String
                        .valueOf(team.getNumber()))));
                team.setOpr(eventOprsTbaResult.getOprs().get("frc".concat(String
                        .valueOf(team.getNumber()))));
                team.setDpr(eventOprsTbaResult.getDprs().get("frc".concat(String
                        .valueOf(team.getNumber()))));
                TeamEventStatusResult eventStatusResult = TBAHandler.GetStatusData(team.getNumber(),
                        "2019scmb");
                team.setRank(Integer.parseInt(eventStatusResult.getRank()));
                team.setNumTeams(eventStatusResult.getNum_teams());
                team.setRankingScore(eventStatusResult.getRankingScore());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.updateTeamData(competition.getTeam(view.getPosition()), matchListAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public Competition getCompetition() {
        return competition;
    }

    @Override
    public void attach(TeamViewContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }
}
