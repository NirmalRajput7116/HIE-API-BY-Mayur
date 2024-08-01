package com.cellbeans.hspa.advancesearch;

import com.cellbeans.hspa.mstvisit.MstVisit;

import java.util.List;

public interface AdvanceSearchDao {

    public List<?> findDetails(AdvanceSearch objMstPatientSearch);

    public List<MstVisit> findAllByPatientId(long patientId);

}
