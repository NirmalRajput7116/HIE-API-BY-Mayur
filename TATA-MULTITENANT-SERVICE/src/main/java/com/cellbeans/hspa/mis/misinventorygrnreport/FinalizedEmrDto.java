package com.cellbeans.hspa.mis.misinventorygrnreport;

import java.util.List;

public class FinalizedEmrDto {

    public List<String> GenderList;
    public String MrNo;
    public String PatientName;

    public List<String> getGenderList() {
        return GenderList;
    }

    public void setGenderList(List<String> genderList) {
        GenderList = genderList;
    }

    public String getMrNo() {
        return MrNo;
    }

    public void setMrNo(String mrNo) {
        MrNo = mrNo;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }
}
