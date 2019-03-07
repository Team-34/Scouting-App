package com.team34rockets.scoutingapp.models.tbaresults;

import java.util.Map;

public class EventOprsTbaResult {
    private Map<String, Double> ccwms;
    private Map<String, Double> dprs;
    private Map<String, Double> oprs;

    public Map<String, Double> getCcwms() {
        return ccwms;
    }

    public void setCcwms(Map<String, Double> ccwms) {
        this.ccwms = ccwms;
    }

    public Map<String, Double> getDprs() {
        return dprs;
    }

    public void setDprs(Map<String, Double> dprs) {
        this.dprs = dprs;
    }

    public Map<String, Double> getOprs() {
        return oprs;
    }

    public void setOprs(Map<String, Double> oprs) {
        this.oprs = oprs;
    }
}
