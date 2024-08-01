package com.cellbeans.hspa.CreateReport;

public class ReportData {
    String srno;
    String date;
    String mrno;
    String patientname;
    String age;
    String gender;
    String patienttype;
    String patientsource;
    String uhd;
    String billno;
    String insurance;
    String companytype;
    String category;
    String servicename;
    String unitname;
    String username;
    String rate;
    String discount;
    String refer;
    //    String doctor;
    String userName;

    public ReportData(String srno, String date, String mrno, String patientname, String age, String gender,
                      String patienttype, String patientsource, String uhd, String billno, String insurance, String companytype,
                      String category, String servicename, String unitname, String username, String rate, String discount, String refer, String userName) {
        super();
        this.srno = srno;
        this.date = date;
        this.mrno = mrno;
        this.patientname = patientname;
        this.age = age;
        this.gender = gender;
        this.patienttype = patienttype;
        this.patientsource = patientsource;
        this.uhd = uhd;
        this.billno = billno;
        this.insurance = insurance;
        this.companytype = companytype;
        this.category = category;
        this.servicename = servicename;
        this.unitname = unitname;
        this.username = username;
        this.rate = rate;
        this.discount = discount;
        this.refer = refer;
        this.userName = userName;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMrno() {
        return mrno;
    }

    public void setMrno(String mrno) {
        this.mrno = mrno;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPatienttype() {
        return patienttype;
    }

    public void setPatienttype(String patienttype) {
        this.patienttype = patienttype;
    }

    public String getPatientsource() {
        return patientsource;
    }

    public void setPatientsource(String patientsource) {
        this.patientsource = patientsource;
    }

    public String getUhd() {
        return uhd;
    }

    public void setUhd(String uhd) {
        this.uhd = uhd;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getCompanytype() {
        return companytype;
    }

    public void setCompanytype(String companytype) {
        this.companytype = companytype;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }
//    public String getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(String doctor) {
//        this.doctor = doctor;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
