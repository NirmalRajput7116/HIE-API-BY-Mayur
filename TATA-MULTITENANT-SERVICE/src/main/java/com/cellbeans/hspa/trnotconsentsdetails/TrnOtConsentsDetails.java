package com.cellbeans.hspa.trnotconsentsdetails;

import com.cellbeans.hspa.mstconsent.MstConsent;
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
@Table(name = "trn_ot_consents_details")
public class TrnOtConsentsDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ocdId", unique = true, nullable = true)
    private long ocdId;

    @JsonInclude(NON_NULL)
    @Column(name = "ocdOpsId")
    private long ocdOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "ocdOpdId")
    private long ocdOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "ocdProcedureId")
    private long ocdProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ocdConsentId")
    private MstConsent ocdConsentId;

    @JsonInclude(NON_NULL)
    @Column(name = "ocdIsAccept", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ocdIsAccept = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ocdAcceptBy")
    private String ocdAcceptBy;

    @JsonInclude(NON_NULL)
    @Column(name = "ocdConsentFile")
    private String ocdConsentFile;

    @JsonInclude(NON_NULL)
    @Column(name = "ocdRemark")
    private String ocdRemark;

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

    public long getOcdId() {
        return ocdId;
    }

    public void setOcdId(long ocdId) {
        this.ocdId = ocdId;
    }

    public long getOcdOpsId() {
        return ocdOpsId;
    }

    public void setOcdOpsId(long ocdOpsId) {
        this.ocdOpsId = ocdOpsId;
    }

    public long getOcdOpdId() {
        return ocdOpdId;
    }

    public void setOcdOpdId(long ocdOpdId) {
        this.ocdOpdId = ocdOpdId;
    }

    public long getOcdProcedureId() {
        return ocdProcedureId;
    }

    public void setOcdProcedureId(long ocdProcedureId) {
        this.ocdProcedureId = ocdProcedureId;
    }

    public MstConsent getOcdConsentId() {
        return ocdConsentId;
    }

    public void setOcdConsentId(MstConsent ocdConsentId) {
        this.ocdConsentId = ocdConsentId;
    }

    public Boolean getOcdIsAccept() {
        return ocdIsAccept;
    }

    public void setOcdIsAccept(Boolean ocdIsAccept) {
        this.ocdIsAccept = ocdIsAccept;
    }

    public String getOcdAcceptBy() {
        return ocdAcceptBy;
    }

    public void setOcdAcceptBy(String ocdAcceptBy) {
        this.ocdAcceptBy = ocdAcceptBy;
    }

    public String getOcdConsentFile() {
        return ocdConsentFile;
    }

    public void setOcdConsentFile(String ocdConsentFile) {
        this.ocdConsentFile = ocdConsentFile;
    }

    public String getOcdRemark() {
        return ocdRemark;
    }

    public void setOcdRemark(String ocdRemark) {
        this.ocdRemark = ocdRemark;
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
