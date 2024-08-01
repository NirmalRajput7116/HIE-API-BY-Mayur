package com.cellbeans.hspa.temrclinicalform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_clinical_form")
public class TemrClinicalForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tcfId", unique = true, nullable = true)
    private long tcfId;

    @JsonInclude(NON_NULL)
    @Column(name = "tcfQuestionName")
    private String tcfQuestionName;

    @JsonInclude(NON_NULL)
    @Column(name = "tcfQuestionValue")
    private String tcfQuestionValue;

    @JsonInclude(NON_NULL)
    @Column(name = "tcfPatientId")
    private String tcfPatientId;

    @JsonInclude(NON_NULL)
    @Column(name = "tcfFormName")
    private String tcfFormName;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "formdate")
    private String formdate;

    public long getTcfId() {
        return tcfId;
    }

    public void setTcfId(int tcfId) {
        this.tcfId = tcfId;
    }

    public String getTcfQuestionName() {
        return tcfQuestionName;
    }

    public void setTcfQuestionName(String tcfQuestionName) {
        this.tcfQuestionName = tcfQuestionName;
    }

    public String getTcfQuestionValue() {
        return tcfQuestionValue;
    }

    public void setTcfQuestionValue(String tcfQuestionValue) {
        this.tcfQuestionValue = tcfQuestionValue;
    }

    public String getTcfPatientId() {
        return tcfPatientId;
    }

    public void setTcfPatientId(String tcfPatientId) {
        this.tcfPatientId = tcfPatientId;
    }

    public String getTcfFormName() {
        return tcfFormName;
    }

    public void setTcfFormName(String tcfFormName) {
        this.tcfFormName = tcfFormName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getFormdate() {
        return formdate;
    }

    public void setFormdate(String formdate) {
        this.formdate = formdate;
    }

}
