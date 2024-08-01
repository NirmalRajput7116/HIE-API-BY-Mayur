package com.cellbeans.hspa.mstvisit;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;

public class VisitPatientDto {
    private Object createdDate;
    private Object userFullname;
    private Object unitName;
    private Object visitType;
    private Boolean isInPerson;
    private Object titleName;
    private Object userFirstname;
    private Object userMiddlename;
    private Object userLastname;
    private Object visitPatientId;


    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(Object userFullname) {
        this.userFullname = userFullname;
    }

    public Object getUnitName() {
        return unitName;
    }

    public void setUnitName(Object unitName) {
        this.unitName = unitName;
    }

    public Object getVisitType() {
        return visitType;
    }

    public void setVisitType(Object visitType) {
        this.visitType = visitType;
    }

    public Boolean getIsInPerson() {
        return isInPerson;
    }

    public void setIsInPerson(Boolean isInPerson) {
        this.isInPerson = isInPerson;
    }

    public Object getTitleName() {
        return titleName;
    }

    public void setTitleName(Object titleName) {
        this.titleName = titleName;
    }

    public Object getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(Object userFirstname) {
        this.userFirstname = userFirstname;
    }

    public Object getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(Object userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public Object getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(Object userLastname) {
        this.userLastname = userLastname;
    }

    public Object getVisitPatientId() {
        return visitPatientId;
    }

    public void setVisitPatientId(Object visitPatientId) {
        this.visitPatientId = visitPatientId;
    }
}
