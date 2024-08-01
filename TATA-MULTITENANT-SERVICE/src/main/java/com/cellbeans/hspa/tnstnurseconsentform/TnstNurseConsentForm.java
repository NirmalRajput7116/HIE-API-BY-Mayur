package com.cellbeans.hspa.tnstnurseconsentform;

import com.cellbeans.hspa.mstconsent.MstConsent;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tnst_nurse_consent_form")
public class TnstNurseConsentForm {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ncfId", unique = true, nullable = true)
    private long ncfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ncfcId")
    private MstConsent ncfcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ncfStaffId")
    private MstStaff ncfStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ncfAdmissionId")
    private TrnAdmission ncfAdmissionId;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getNcfId() {
        return ncfId;
    }

    public void setNcfId(long ncfId) {
        this.ncfId = ncfId;
    }

    public MstConsent getNcfcId() {
        return ncfcId;
    }

    public void setNcfcId(MstConsent ncfcId) {
        this.ncfcId = ncfcId;
    }

    public MstStaff getNcfStaffId() {
        return ncfStaffId;
    }

    public void setNcfStaffId(MstStaff ncfStaffId) {
        this.ncfStaffId = ncfStaffId;
    }

    public TrnAdmission getNcfAdmissionId() {
        return ncfAdmissionId;
    }

    public void setNcfAdmissionId(TrnAdmission ncfAdmissionId) {
        this.ncfAdmissionId = ncfAdmissionId;
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

