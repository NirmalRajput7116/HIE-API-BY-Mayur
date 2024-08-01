package com.cellbeans.hspa.trnbloodbank;

import com.cellbeans.hspa.mstbloodgroup.MstBloodgroup;
import com.cellbeans.hspa.mstpatient.MstPatient;
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
@Table(name = "trn_blood_bank")
public class TrnBloodBank implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bbId", unique = true, nullable = true)
    private long bbId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bbPatientId")
    private MstPatient bbPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bbBloodgroupId")
    private MstBloodgroup bbBloodgroupId;

    @JsonInclude(NON_NULL)
    @Column(name = "bbQuantity")
    private int bbQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "bbUnit")
    private String bbUnit;

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

    public long getBbId() {
        return bbId;
    }

    public void setBbId(int bbId) {
        this.bbId = bbId;
    }

    public MstPatient getBbPatientId() {
        return bbPatientId;
    }

    public void setBbPatientId(MstPatient bbPatientId) {
        this.bbPatientId = bbPatientId;
    }

    public MstBloodgroup getBbBloodgroupId() {
        return bbBloodgroupId;
    }

    public void setBbBloodgroupId(MstBloodgroup bbBloodgroupId) {
        this.bbBloodgroupId = bbBloodgroupId;
    }

    public int getBbQuantity() {
        return bbQuantity;
    }

    public void setBbQuantity(int bbQuantity) {
        this.bbQuantity = bbQuantity;
    }

    public String getBbUnit() {
        return bbUnit;
    }

    public void setBbUnit(String bbUnit) {
        this.bbUnit = bbUnit;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
