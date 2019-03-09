package com.team34rockets.scoutingapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Utils {

    /**
     * Converts a boolean to an integer
     *
     * @return 0 for false; 1 for true
     */
    public static int bti(boolean b) {
        return b ? 1 : 0;
    }

    /**
     * calculates the average of a list
     *
     * @param marks list of DOUBLES
     * @return average as a DOUBLE
     */
    public static double calculateAverage(List<Double> marks) {
        Double sum = 0.0;
        if (!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }
            return sum / marks.size();
        }
        return sum;
    }

    public static List<String> sortSet(Set<String> items) {
        List<String> itemsList = new ArrayList<>();
        for (String t : items) {
            itemsList.add(t);
        }
        Collections.sort(itemsList);
        return itemsList;
    }


}
