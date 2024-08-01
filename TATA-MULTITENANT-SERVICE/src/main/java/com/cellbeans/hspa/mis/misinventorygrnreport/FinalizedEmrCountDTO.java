package com.cellbeans.hspa.mis.misinventorygrnreport;

import com.cellbeans.hspa.mis.misbillingreport.KeyValueDto;
import com.cellbeans.hspa.mstunit.MstUnit;

import java.util.List;

public class FinalizedEmrCountDTO {

    public long timeline_patient_id;
    public String unit_name;
    public String visit_date;
    public String patient_mr_no;
    public String Patient_Name;
    public String user_age;
    public String gender_name;
    public String tariff_name;
    public String Finalized_By;
    public String doctor_name;
    public String user_fullname;
    public String finalized_count;
    public String grandtotalfinalised;
    public long count;
    public boolean print;
    List<KeyValueDto> genderWiserCount;
    public MstUnit objHeaderData;

    public String getGrandtotalfinalised() {
        return grandtotalfinalised;
    }

    public void setGrandtotalfinalised(String grandtotalfinalised) {
        this.grandtotalfinalised = grandtotalfinalised;
    }

    public boolean getPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public List<KeyValueDto> getGenderWiserCount() {
        return genderWiserCount;
    }

    public void setGenderWiserCount(List<KeyValueDto> genderWiserCount) {
        this.genderWiserCount = genderWiserCount;
    }

    public long getTimeline_patient_id() {
        return timeline_patient_id;
    }

    public void setTimeline_patient_id(long timeline_patient_id) {
        this.timeline_patient_id = timeline_patient_id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getPatient_mr_no() {
        return patient_mr_no;
    }

    public void setPatient_mr_no(String patient_mr_no) {
        this.patient_mr_no = patient_mr_no;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String patient_Name) {
        Patient_Name = patient_Name;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }

    public String getTariff_name() {
        return tariff_name;
    }

    public void setTariff_name(String tariff_name) {
        this.tariff_name = tariff_name;
    }

    public String getFinalized_By() {
        return this.Finalized_By;
    }

    public void setFinalized_By(String finalized_By) {
        this.Finalized_By = finalized_By;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getFinalized_count() {
        return finalized_count;
    }

    public void setFinalized_count(String finalized_count) {
        this.finalized_count = finalized_count;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public boolean setPrint() {
        return print;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }
}
