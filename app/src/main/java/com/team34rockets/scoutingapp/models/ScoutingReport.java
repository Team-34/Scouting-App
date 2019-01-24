package com.team34rockets.scoutingapp.models;

import com.team34rockets.scoutingapp.handlers.SheetsHandler;

import java.util.List;

public class ScoutingReport {

    public int teamNumber;
    public int matchNumber;
    public int hatchesScored;
    public int cargoScored;
    public boolean canPlaceHatches;
    public int hatchLayer;
    public boolean canPlaceCargo;
    public int cargoLayer;
    public int climbLevel;
    public boolean canPickupHatches;
    public boolean canPickupCargo;
    public boolean hasCamera;
    public boolean autoSandstorm;
    public boolean hasHumanPlayer;
    public boolean offenceBot;
    public int driverRating;
    public int hatchRating;
    public int cargoRating;
    public int offenseRating;
    public int defenseRating;
    public int sandstormRating;
    public int rating;
    public String details;
    public int redScore;
    public int blueScore;
    public String alliance;


    public static final String[] questionNames = {
            "Team Number",
            "Match Number",
            "Hatches Scored",
            "Cargo Scored",
            "Can Place Hatches",
            "Hatch Place Level",
            "Can Shoot Cargo",
            "Cargo Shoot Level",
            "Climbing Level",
            "Can Pickup Hatches",
            "Can Pickup Cargo",
            "Has a Camera",
            "Has a Sandstorm",
            "Has a Human Player",
            "Is Offense Bot",
            "Driver Rating",
            "Hatch Score Rating",
            "Cargo Score Rating",
            "Offense Rating",
            "Defense Rating",
            "Sandstorm Rating",
            "Overall Rating",
            };

    public static class ScoutingReportBuilder {

        private Sheet sheet;
        private int index;

        public ScoutingReport build(SheetsHandler sheetsHandler, int index) {
            this.index = index;
            List<List<Object>> data = sheetsHandler.getData();
            ScoutingReport report = new ScoutingReport();
            sheet = new Sheet(data);
            report.teamNumber = getInteger("Team Number");
            report.matchNumber = getInteger("Match Number");
            report.hatchesScored = getInteger("How many hatches did they score?");
            report.cargoScored = getInteger("How many cargo did they score?");
            report.canPlaceHatches = getBoolean("Can they place hatches?");
            report.hatchLayer = getLayer("If yes, which level of the rocket?");
            report.canPlaceCargo = getBoolean("Can they shoot cargo?");
            report.cargoLayer = getLayer("If yes, which layer of the rocket can it " +
                    "reach?");
            report.climbLevel = getClimbLevel();
            report.canPickupHatches = getBoolean("Can they pickup a hatch from the " +
                    "floor?");
            report.canPickupCargo = getBoolean("Can they grab cargo from ground?");
            report.hasCamera = getBoolean("Camera?");
            report.autoSandstorm = autoSandstorm();
            report.hasHumanPlayer = getBoolean("Do they have a human player?");
            report.offenceBot = offenceBot();
            report.driverRating = getInteger("How good is their driver?");
            report.hatchRating = getInteger("How well do they place hatches?");
            report.cargoRating = getInteger("How well to they place Cargo?");
            report.offenseRating = getInteger("How well do they play offence?");
            report.defenseRating = getInteger("Defense?");
            report.sandstormRating = getInteger("How good is their sandstorm period?");
            report.rating = getInteger("Overall score?");
            report.details = getString("Important Details");
            return report;
        }


        private Integer getInteger(String columnName) {
            try {
                return Integer.valueOf(getString(columnName));
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        private String getString(String columnName) {
            return String.valueOf(sheet.getCellByColumnName(index, columnName));
        }

        private Boolean getBoolean(String columnName) {
            String result = getString(columnName);
            if (result.equalsIgnoreCase("Yes")) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }

        private int getLayer(String columnName) {
            String result = getString(columnName);
            if (result.contains("ird")) {
                return 3;
            } else if (result.contains("st")) {
                return 1;
            } else if (result.contains("nd")) {
                return 2;
            } else {
                return 0;
            }
        }

        private int getClimbLevel() {
            String result = getString("Did they get back onto the HAB platform at " +
                    "the end of the game?");
            if (result.contains("s")) {
                if (result.contains("2")) {
                    return 2;
                } else if (result.contains("1")) {
                    return 1;
                } else {
                    return 3;
                }
            } else {
                return 0;
            }
        }

        private boolean autoSandstorm() {
            String result = getString("Use Auto or manual in sandstorm");
            return result.contains("o");
        }

        private boolean offenceBot() {
            String result = getString("Defense or Offence based bot?");
            return result.contains("O");
        }
    }

}
