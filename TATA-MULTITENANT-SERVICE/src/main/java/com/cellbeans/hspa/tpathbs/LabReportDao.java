package com.cellbeans.hspa.tpathbs;

import java.util.List;

public class LabReportDao {

    String patientId;
    String patientName;
    String referal;
    String organization;
    List<PatientTest> patientTest;

    public LabReportDao() {
    }

    public LabReportDao(String patientName, List<PatientTest> patientTest) {
        super();
        this.patientName = patientName;
        this.patientTest = patientTest;
    }

    public LabReportDao(String patientId, String patientName, String referal, String organization,
                        List<PatientTest> patientTest) {
        super();
        this.patientId = patientId;
        this.patientName = patientName;
        this.referal = referal;
        this.organization = organization;
        this.patientTest = patientTest;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getReferal() {
        return referal;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public List<PatientTest> getPatientTest() {
        return patientTest;
    }

    public void setPatientTest(List<PatientTest> patientTest) {
        this.patientTest = patientTest;
    }

}
