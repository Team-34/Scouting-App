package com.team34rockets.scoutingapp.models;

import java.util.ArrayList;
import java.util.List;

public class Sheet {

    private List<List<Object>> data;

    public Sheet(List<List<Object>> data) {
        this.data = data;
    }

    public Object getCell(int row, int column) {
        return data.get(row).get(column);
    }

    Object getCellByColumnName(int row, String column) {
        List<Object> oNames = data.get(0);
        List<String> names = new ArrayList<>();
        for (Object iColumn : oNames) {
            names.add(String.valueOf(iColumn));
        }
        for (String name : names) {
            if (name.equalsIgnoreCase(column)) {
                try {
                    return data.get(row).get(names.indexOf(name));
                } catch (IndexOutOfBoundsException e) {
                    return "";
                }

            }
        }
        return null;
    }
}
