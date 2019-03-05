package com.team34rockets.scoutingapp.models;

import com.team34rockets.scoutingapp.handlers.SheetsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public static final Map<String, Integer> questionNames = new HashMap<String, Integer>() {
        {
            put("Team Number", 25);
            put("Match Number", 19);
            put("Hatches Scored", 18);
            put("Cargo Scored", 9);
            put("Can Place Hatches", 6);
            put("Hatch Place Level", 16);
            put("Can Shoot Cargo", 5);
            put("Cargo Shoot Level", 7);
            put("Climbing Level", 10);
            put("Can Pickup Hatches", 4);
            put("Can Pickup Cargo", 3);
            put("Has a Camera", 14);
            put("Has a Sandstorm", 1);
            put("Has a Human Player", 15);
            put("Is Offense Bot", 20);
            put("Driver Rating", 13);
            put("Hatch Score Rating", 17);
            put("Cargo Score Rating", 8);
            put("Offense Rating", 21);
            put("Defense Rating", 11);
            put("Sandstorm Rating", 24);
            put("Overall Rating", 22);
        }
    };

    public final List<String> questions = new ArrayList<String>() {
        {
            add("Team Number:");
            add("Match Number:");
            add("Autonomous Sandstorm?");
            add("Sandstorm Rating");
            add("Has A Camera");
            add("Can Place Hatches?");
            add("Can Pickup Hatches From the Floor?");
            add("Hatch Rating:");
            add("Hatch Place Level:");
            add("Hatches Scored:");
            add("Can Shoot Cargo?");
            add("Can Pickup Cargo From Floor?");
            add("Cargo Rating:");
            add("Cargo Place Level");
            add("Cargo Scored");
            add("Is Offense Bot?");
            add("Offense Rating");
            add("Defense Rating");
            add("Climbing Level:");
            add("Has A Human Player");
            add("Driver Rating");
            add("Overall Rating");
        }
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
            report.blueScore = getInteger("Blue Score");
            report.redScore = getInteger("Red Score");
            report.alliance = getString("Alliance");
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
