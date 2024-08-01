package com.cellbeans.hspa.mstpatientsponserdetail;

import com.cellbeans.hspa.mbillplan.MbillPlan;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstassociatedcompany.MstAssociatedCompany;
import com.cellbeans.hspa.mstcompany.MstCompany;
import com.cellbeans.hspa.mstcompanytype.MstCompanyType;
import com.cellbeans.hspa.mstdesignation.MstDesignation;
import com.cellbeans.hspa.mstpatientcategory.MstPatientCategory;
import com.cellbeans.hspa.mstpriority.MstPriority;
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
@Table(name = "mst_patient_sponser_detail")
public class MstPatientSponserDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psdId", unique = true, nullable = true)
    private long psdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdCtId")
    private MstCompanyType psdCtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdPcId")
    private MstPatientCategory psdPcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdPriorityId")
    private MstPriority psdPriorityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdCompanyId")
    private MstCompany psdCompanyId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdAcId")
    private MstAssociatedCompany psdAcId;

    @JsonInclude(NON_NULL)
    @Column(name = "psdPolicyNo")
    private String psdPolicyNo;

    @JsonInclude(NON_NULL)
    @Column(name = "psdCreditLimit")
    private double psdCreditLimit;

    @JsonInclude(NON_NULL)
    @Column(name = "psdStartDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date psdStartDate;

    @JsonInclude(NON_NULL)
    @Column(name = "psdExpiryDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date psdExpiryDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdTariffId")
    private MbillTariff psdTariffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdPlanId")
    private MbillPlan psdPlanId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psdDesignationId")
    private MstDesignation psdDesignationId;

    @JsonInclude(NON_NULL)
    @Column(name = "psdRemarks")
    private String psdRemarks;

    @JsonInclude(NON_NULL)
    @Column(name = "psdTotalAdvance")
    private double psdTotalAdvance;

    @JsonInclude(NON_NULL)
    @Column(name = "psdBalance")
    private double psdBalance;

    @JsonInclude(NON_NULL)
    @Column(name = "psdUsed")
    private String psdUsed;

    @JsonInclude(NON_NULL)
    @Column(name = "psdInsurer")
    private String psdInsurer;

    @JsonInclude(NON_NULL)
    @Column(name = "psdPrimaryMember")
    private String psdPrimaryMember;

    @JsonInclude(NON_NULL)
    @Column(name = "psdMemberId")
    private String psdMemberId;

    @JsonInclude(NON_NULL)
    @Column(name = "psdOrganization")
    private String psdOrganization;

    @JsonInclude(NON_NULL)
    @Column(name = "psdStaffNo")
    private String psdStaffNo;

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

    public long getPsdId() {
        return psdId;
    }

    public void setPsdId(int psdId) {
        this.psdId = psdId;
    }

    public MstCompanyType getPsdCtId() {
        return psdCtId;
    }

    public void setPsdCtId(MstCompanyType psdCtId) {
        this.psdCtId = psdCtId;
    }

    public MstPatientCategory getPsdPcId() {
        return psdPcId;
    }

    public void setPsdPcId(MstPatientCategory psdPcId) {
        this.psdPcId = psdPcId;
    }

    public MstPriority getPsdPriorityId() {
        return psdPriorityId;
    }

    public void setPsdPriorityId(MstPriority psdPriorityId) {
        this.psdPriorityId = psdPriorityId;
    }

    public MstCompany getPsdCompanyId() {
        return psdCompanyId;
    }

    public void setPsdCompanyId(MstCompany psdCompanyId) {
        this.psdCompanyId = psdCompanyId;
    }

    public MstAssociatedCompany getPsdAcId() {
        return psdAcId;
    }

    public void setPsdAcId(MstAssociatedCompany psdAcId) {
        this.psdAcId = psdAcId;
    }

    public String getPsdPolicyNo() {
        return psdPolicyNo;
    }

    public void setPsdPolicyNo(String psdPolicyNo) {
        this.psdPolicyNo = psdPolicyNo;
    }

    public double getPsdCreditLimit() {
        return psdCreditLimit;
    }

    public void setPsdCreditLimit(double psdCreditLimit) {
        this.psdCreditLimit = psdCreditLimit;
    }

    public Date getPsdStartDate() {
        return psdStartDate;
    }

    public void setPsdStartDate(Date psdStartDate) {
        this.psdStartDate = psdStartDate;
    }

    public Date getPsdExpiryDate() {
        return psdExpiryDate;
    }

    public void setPsdExpiryDate(Date psdExpiryDate) {
        this.psdExpiryDate = psdExpiryDate;
    }

    public MbillTariff getPsdTariffId() {
        return psdTariffId;
    }

    public void setPsdTariffId(MbillTariff psdTariffId) {
        this.psdTariffId = psdTariffId;
    }

    public MbillPlan getPsdPlanId() {
        return psdPlanId;
    }

    public void setPsdPlanId(MbillPlan psdPlanId) {
        this.psdPlanId = psdPlanId;
    }

    public MstDesignation getPsdDesignationId() {
        return psdDesignationId;
    }

    public void setPsdDesignationId(MstDesignation psdDesignationId) {
        this.psdDesignationId = psdDesignationId;
    }

    public String getPsdRemarks() {
        return psdRemarks;
    }

    public void setPsdRemarks(String psdRemarks) {
        this.psdRemarks = psdRemarks;
    }

    public double getPsdTotalAdvance() {
        return psdTotalAdvance;
    }

    public void setPsdTotalAdvance(double psdTotalAdvance) {
        this.psdTotalAdvance = psdTotalAdvance;
    }

    public double getPsdBalance() {
        return psdBalance;
    }

    public void setPsdBalance(double psdBalance) {
        this.psdBalance = psdBalance;
    }

    public String getPsdUsed() {
        return psdUsed;
    }

    public void setPsdUsed(String psdUsed) {
        this.psdUsed = psdUsed;
    }

    public String getPsdInsurer() {
        return psdInsurer;
    }

    public void setPsdInsurer(String psdInsurer) {
        this.psdInsurer = psdInsurer;
    }

    public String getPsdPrimaryMember() {
        return psdPrimaryMember;
    }

    public void setPsdPrimaryMember(String psdPrimaryMember) {
        this.psdPrimaryMember = psdPrimaryMember;
    }

    public String getPsdMemberId() {
        return psdMemberId;
    }

    public void setPsdMemberId(String psdMemberId) {
        this.psdMemberId = psdMemberId;
    }

    public String getPsdOrganization() {
        return psdOrganization;
    }

    public void setPsdOrganization(String psdOrganization) {
        this.psdOrganization = psdOrganization;
    }

    public String getPsdStaffNo() {
        return psdStaffNo;
    }

    public void setPsdStaffNo(String psdStaffNo) {
        this.psdStaffNo = psdStaffNo;
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
