package com.team34rockets.scoutingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.team34rockets.scoutingapp.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Team implements Parcelable {

    private int number;
    private String name;

    private int rank;
    private String numTeams = "";
    private String rankingScore = "";
    private double apr;
    private double opr;
    private double dpr;
    private double ccwm;
    private String key;

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {

        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
    private List<ScoutingReport> scoutingReports;

    public Team(int number, String name) {
        this.number = number;
        this.name = name;
        scoutingReports = new ArrayList<>();
        Collections.sort(scoutingReports, new Comparator<ScoutingReport>() {
            @Override
            public int compare(ScoutingReport o1, ScoutingReport o2) {
                return o1.matchNumber - o2.matchNumber;
            }
        });
        this.key = "frc".concat(String.valueOf(number));
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Team(Parcel in) {
        number = in.readInt();
        name = in.readString();
        rank = in.readInt();
        apr = in.readDouble();
        opr = in.readDouble();
        dpr = in.readDouble();
        ccwm = in.readDouble();
    }

    public double getScaledApr(Competition competition) {
        double scaledApr = (calculateApr() / competition.getHighestApr()) * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return scaledApr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public double getOpr() {
        return opr;
    }

    public void setOpr(double opr) {
        this.opr = opr;
    }

    public double getDpr() {
        return dpr;
    }

    public void setDpr(double dpr) {
        this.dpr = dpr;
    }

    public double getCcwm() {
        return ccwm;
    }

    public void setCcwm(double ccwm) {
        this.ccwm = ccwm;
    }

    public List<ScoutingReport> getScoutingReports() {
        Collections.sort(scoutingReports, new Comparator<ScoutingReport>() {
            @Override
            public int compare(ScoutingReport o1, ScoutingReport o2) {
                return o1.matchNumber - o2.matchNumber;
            }
        });
        return scoutingReports;
    }

    public void setScoutingReports(List<ScoutingReport> scoutingReports) {
        this.scoutingReports = scoutingReports;
    }

    public void addScouingReport(ScoutingReport scoutingReport) {
        this.scoutingReports.add(scoutingReport);
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    double getApr() {
        return calculateApr();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumTeams() {
        return numTeams;
    }

    public void setNumTeams(String numTeams) {
        this.numTeams = numTeams;
    }

    public String getRankingScore() {
        return rankingScore;
    }

    public void setRankingScore(String rankingScore) {
        this.rankingScore = rankingScore;
    }

    private double calculateApr() {
        List<Double> aprList = new ArrayList<>();
        for (ScoutingReport report : scoutingReports) {
            double hatchScore = (report.canPlaceHatches ? 1 : 0) *
                    (report.hatchLayer + (report.canPickupHatches ? 1 : 0)) *
                    (report.hatchRating) * (report.hatchesScored);
            double cargoScore = (report.canPlaceCargo ? 1 : 0) *
                    (report.cargoLayer + (report.canPickupCargo ? 1 : 0)) *
                    (report.cargoRating) * (report.cargoScored);
            double extraScore = (report.climbLevel * 20) + (report.sandstormRating * 20);
            double total = hatchScore + cargoScore + extraScore;
            aprList.add(total * report.driverRating);
        }
        return Utils.calculateAverage(aprList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(name);
        dest.writeInt(rank);
        dest.writeDouble(apr);
        dest.writeDouble(opr);
        dest.writeDouble(dpr);
        dest.writeDouble(ccwm);
    }
}
