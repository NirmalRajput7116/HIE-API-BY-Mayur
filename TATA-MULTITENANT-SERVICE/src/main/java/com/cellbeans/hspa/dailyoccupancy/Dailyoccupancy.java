package com.cellbeans.hspa.dailyoccupancy;

import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombination;

import java.io.Serializable;
import java.util.List;

public class Dailyoccupancy implements Serializable {

    private TrnAdmission admissionDetails;
    private Object outstandingAmount;
    private Object balanceAmount;
    private String traffname;
    private List<TrnSponsorCombination> company;
    private Float noOfDay;

    public TrnAdmission getAdmissionDetails() {
        return admissionDetails;
    }

    public void setAdmissionDetails(TrnAdmission admissionDetails) {
        this.admissionDetails = admissionDetails;
    }

    public Object getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Object outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Object getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Object balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Float getNoOfDay() {
        return noOfDay;
    }

    public void setNoOfDay(Float noOfDay) {
        this.noOfDay = noOfDay;
    }

    public List<TrnSponsorCombination> getCompany() {
        return company;
    }

    public void setCompany(List<TrnSponsorCombination> company) {
        this.company = company;
    }

    public String getTraffname() {
        return traffname;
    }

    public void setTraffname(String traffname) {
        this.traffname = traffname;
    }

}
