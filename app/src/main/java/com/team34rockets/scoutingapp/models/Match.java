package com.team34rockets.scoutingapp.models;

public class Match {

    private ScoutingReport scoutingReport;
    private ALLIANCE alliance;
    private int blueScore;
    private int redScore;

    public Match(ScoutingReport scoutingReport) {
        this.scoutingReport = scoutingReport;
        blueScore = scoutingReport.blueScore;
        redScore = scoutingReport.redScore;
    }

    private void assignAlliance() {
        if (scoutingReport.alliance.contains("u")) {
            this.alliance = ALLIANCE.BLUE;
        } else {
            this.alliance = ALLIANCE.RED;
        }
    }

    /**
     * gets the result of a match for a specific team
     *
     * @return 0 for loss, 1 for win, 2 for tie
     */
    public int getResult() {
        ALLIANCE winner;
        if (blueScore > redScore) {
            winner = ALLIANCE.BLUE;
        } else if (blueScore < redScore) {
            winner = ALLIANCE.RED;
        } else {
            return 2;
        }
        if (alliance == winner) {
            return 1;
        } else {
            return 0;
        }
    }

    public double matchApr() {
        ScoutingReport report = scoutingReport;
        double hatchScore = (report.canPlaceHatches ? 1 : 0) *
                (report.hatchLayer + (report.canPickupHatches ? 1 : 0)) *
                (report.hatchRating) * (report.hatchesScored);
        double cargoScore = (report.canPlaceCargo ? 1 : 0) *
                (report.cargoLayer + (report.canPickupCargo ? 1 : 0)) *
                (report.cargoRating) * (report.cargoScored);
        double extraScore = (report.climbLevel * 20) + (report.sandstormRating * 20);
        double total = hatchScore + cargoScore + extraScore;
        return total * report.driverRating;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getRedScore() {
        return redScore;
    }

    private enum ALLIANCE {
        RED,
        BLUE
    }
}
