package com.cellbeans.hspa.trnadmissionemergencycontact;

import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "trn_admission_emergency_contact")
public class TrnAdmissionEmergencyContact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aecId", unique = true, nullable = true)
    private long aecId;

    @JsonInclude(NON_NULL)
    @Column(name = "aecFirstname")
    private String aecFirstname;

    @JsonInclude(NON_NULL)
    @Column(name = "aecLastname")
    private String aecLastname;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "aecRelationId")
    private MstRelation aecRelationId;

    @JsonInclude(NON_NULL)
    @Column(name = "aecMobileNo")
    private String aecMobileNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "aecAdmissionId")
    private TrnAdmission aecAdmissionId;

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

    public long getAecId() {
        return aecId;
    }

    public void setAecId(int aecId) {
        this.aecId = aecId;
    }

    public String getAecFirstname() {
        return aecFirstname;
    }

    public void setAecFirstname(String aecFirstname) {
        this.aecFirstname = aecFirstname;
    }

    public String getAecLastname() {
        return aecLastname;
    }

    public void setAecLastname(String aecLastname) {
        this.aecLastname = aecLastname;
    }

    public MstRelation getAecRelationId() {
        return aecRelationId;
    }

    public void setAecRelationId(MstRelation aecRelationId) {
        this.aecRelationId = aecRelationId;
    }

    public String getAecMobileNo() {
        return aecMobileNo;
    }

    public void setAecMobileNo(String aecMobileNo) {
        this.aecMobileNo = aecMobileNo;
    }

    public TrnAdmission getAecAdmissionId() {
        return aecAdmissionId;
    }

    public void setAecAdmissionId(TrnAdmission aecAdmissionId) {
        this.aecAdmissionId = aecAdmissionId;
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
