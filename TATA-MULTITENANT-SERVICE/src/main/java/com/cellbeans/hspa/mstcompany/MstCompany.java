package com.cellbeans.hspa.mstcompany;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstcompanytype.MstCompanyType;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_company")
public class MstCompany implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "companyId", unique = true, nullable = true)
    private long companyId;

    @JsonInclude(NON_NULL)
    @Column(name = "companyCode")
    private String companyCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "companyCtId")
    private MstCompanyType companyCtId;

    @JsonInclude(NON_NULL)
    @Column(name = "companyCreditLimit")
    private double companyCreditLimit;

    @JsonInclude(NON_NULL)
    @Column(name = "companyReportPath")
    private String companyReportPath;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinTable(name = "trnCompanyService")
    private List<MbillService> trnCompanyServiceId; // services added against which are qualified for this company

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "companyTariffId")
    private MbillTariff companyTariffId;

    @JsonInclude(NON_NULL)
    @Column(name = "companyAddressId")
    private String companyAddressId;

    @JsonInclude(NON_NULL)
    @Column(name = "companyCdId")
    private String companyCdId;

    @JsonInclude(NON_NULL)
    @Column(name = "companyName")
    private String companyName;

    @JsonInclude(NON_NULL)
    @Column(name = "companyShortName")
    private String companyShortName;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    //    @JsonInclude(NON_NULL)
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "companyPolicyName")
    private String companyPolicyName;

    @JsonInclude(NON_NULL)
    @Column(name = "companyPolicyCode")
    private String companyPolicyCode;

    @JsonInclude(NON_NULL)
    @Column(name = "companyPercentage")
    private String companyPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "companyEmail")
    private String companyEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "companyPhone")
    private String companyPhone;

    @JsonInclude(NON_NULL)
    @Column(name = "companyMobile")
    private String companyMobile;

    @JsonInclude(NON_NULL)
    @Column(name = "companyContactPerson")
    private String companyContactPerson;

    @JsonInclude(NON_NULL)
    @Column(name = "companyBillApproval", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean companyBillApproval = false;

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

    public long getCompanyId() {
        return companyId;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public MstCompanyType getCompanyCtId() {
        return companyCtId;
    }

    public void setCompanyCtId(MstCompanyType companyCtId) {
        this.companyCtId = companyCtId;
    }

    public double getCompanyCreditLimit() {
        return companyCreditLimit;
    }

    public void setCompanyCreditLimit(double companyCreditLimit) {
        this.companyCreditLimit = companyCreditLimit;
    }

    public String getCompanyReportPath() {
        return companyReportPath;
    }

    public void setCompanyReportPath(String companyReportPath) {
        this.companyReportPath = companyReportPath;
    }

    public String getCompanyAddressId() {
        return companyAddressId;
    }

    public void setCompanyAddressId(String companyAddressId) {
        this.companyAddressId = companyAddressId;
    }

    public String getCompanyCdId() {
        return companyCdId;
    }

    public void setCompanyCdId(String companyCdId) {
        this.companyCdId = companyCdId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCompanyPolicyName() {
        return companyPolicyName;
    }

    public void setCompanyPolicyName(String companyPolicyName) {
        this.companyPolicyName = companyPolicyName;
    }

    public String getCompanyPolicyCode() {
        return companyPolicyCode;
    }

    public void setCompanyPolicyCode(String companyPolicyCode) {
        this.companyPolicyCode = companyPolicyCode;
    }

    public String getCompanyPercentage() {
        return companyPercentage;
    }

    public void setCompanyPercentage(String companyPercentage) {
        this.companyPercentage = companyPercentage;
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

    public MbillTariff getCompanyTariffId() {
        return companyTariffId;
    }

    public void setCompanyTariffId(MbillTariff companyTariffId) {
        this.companyTariffId = companyTariffId;
    }

    public List<MbillService> getTrnCompanyServiceId() {
        return trnCompanyServiceId;
    }

    public void setTrnCompanyServiceId(List<MbillService> trnCompanyServiceId) {
        this.trnCompanyServiceId = trnCompanyServiceId;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getCompanyContactPerson() {
        return companyContactPerson;
    }

    public void setCompanyContactPerson(String companyContactPerson) {
        this.companyContactPerson = companyContactPerson;
    }

    public Boolean getCompanyBillApproval() {
        return companyBillApproval;
    }

    public void setCompanyBillApproval(Boolean companyBillApproval) {
        this.companyBillApproval = companyBillApproval;
    }
}
