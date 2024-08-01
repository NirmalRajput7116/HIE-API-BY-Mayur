package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.mis.misbillingreport.KeyValueDto;
import com.cellbeans.hspa.mstunit.MstUnit;

import java.util.List;

public class ListOfVisitListDTO {

    public String unitName;
    public String dateOfVisit;
    public String mrNo;
    public String patientName;
    public String age;
    public String gender;
    public String mobileNo;
    public String doctor;
    public String tariffName;
    public String chiefComplaint;
    public String userName;
    List<KeyValueDto> genderWiserCount;
    List<KeyValueDto> tariffWiseCount;

    public long unitId;
    public long count;   // total count

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public MstUnit objHeaderData;

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public List<KeyValueDto> getGenderWiserCount() {
        return genderWiserCount;
    }

    public void setGenderWiserCount(List<KeyValueDto> genderWiserCount) {
        this.genderWiserCount = genderWiserCount;
    }

    public List<KeyValueDto> getTariffWiseCount() {
        return tariffWiseCount;
    }

    public void setTariffWiseCount(List<KeyValueDto> tariffWiseCount) {
        this.tariffWiseCount = tariffWiseCount;
    }

    @Override
    public String toString() {
        return "ListOfVisitListDTO{" +
                "count='" + count + '\'' +
                "unitName='" + unitName + '\'' +
                ", dateOfVisit='" + dateOfVisit + '\'' +
                ", mrNo='" + mrNo + '\'' +
                ", patientName='" + patientName + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", doctor='" + doctor + '\'' +
                ", tariffName='" + tariffName + '\'' +
                ", chiefComplaint='" + chiefComplaint + '\'' +
                ", userName='" + userName + '\'' +
                ", unitId=" + unitId +
                ", objHeaderData=" + objHeaderData +
                '}';
    }
}
