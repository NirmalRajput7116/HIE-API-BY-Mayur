package com.cellbeans.hspa.mipddischargetemplate;

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
@Table(name = "mipd_discharge_template")
public class MipdDischargeTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dtId", unique = true, nullable = true)
    private long dtId;

    @JsonInclude(NON_NULL)
    @Column(name = "dtName")
    private String dtName;

    @JsonInclude(NON_NULL)
    @Column(name = "dtTemplate")
    private String dtTemplate;

    @JsonInclude(NON_NULL)
    @Column(name = "dtFieldName")
    private String dtFieldName;

    @JsonInclude(NON_NULL)
    @Column(name = "dtCountrolName")
    private String dtCountrolName;

    @JsonInclude(NON_NULL)
    @Column(name = "dtParameterName")
    private String dtParameterName;

    @JsonInclude(NON_NULL)
    @Column(name = "dtCountrolvalue")
    private String dtCountrolvalue;
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

    public String getDtFieldName() {
        return dtFieldName;
    }

    public void setDtFieldName(String dtFieldName) {
        this.dtFieldName = dtFieldName;
    }

    public String getDtCountrolName() {
        return dtCountrolName;
    }

    public void setDtCountrolName(String dtCountrolName) {
        this.dtCountrolName = dtCountrolName;
    }

    public String getDtParameterName() {
        return dtParameterName;
    }

    public void setDtParameterName(String dtParameterName) {
        this.dtParameterName = dtParameterName;
    }

    public String getDtCountrolvalue() {
        return dtCountrolvalue;
    }

    public void setDtCountrolvalue(String dtCountrolvalue) {
        this.dtCountrolvalue = dtCountrolvalue;
    }

    public long getDtId() {
        return dtId;
    }

    public void setDtId(int dtId) {
        this.dtId = dtId;
    }

    public String getDtName() {
        return dtName;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    public String getDtTemplate() {
        return dtTemplate;
    }

    public void setDtTemplate(String dtTemplate) {
        this.dtTemplate = dtTemplate;
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

}            
