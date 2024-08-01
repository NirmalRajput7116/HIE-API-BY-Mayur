package com.cellbeans.hspa.mpathagency;

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
@Table(name = "mpath_agency")
public class MpathAgency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agencyId", unique = true, nullable = true)
    private long agencyId;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyCode")
    private String agencyCode;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyName")
    private String agencyName;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyLegalName")
    private String agencyLegalName;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyBankAccountNo")
    private String agencyBankAccountNo;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyAddress")
    private String agencyAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyMobile")
    private String agencyMobile;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyPhone")
    private String agencyPhone;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyEmail")
    private String agencyEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyAdminFirstName")
    private String agencyAdminFirstName;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyAdminLastName")
    private String agencyAdminLastName;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyAdminMobileNo")
    private String agencyAdminMobileNo;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyAdminPhoneNo")
    private String agencyAdminPhoneNo;

    @JsonInclude(NON_NULL)
    @Column(name = "agencyAdminEmail")
    private String agencyAdminEmail;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    public long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyLegalName() {
        return agencyLegalName;
    }

    public void setAgencyLegalName(String agencyLegalName) {
        this.agencyLegalName = agencyLegalName;
    }

    public String getAgencyBankAccountNo() {
        return agencyBankAccountNo;
    }

    public void setAgencyBankAccountNo(String agencyBankAccountNo) {
        this.agencyBankAccountNo = agencyBankAccountNo;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    public String getAgencyMobile() {
        return agencyMobile;
    }

    public void setAgencyMobile(String agencyMobile) {
        this.agencyMobile = agencyMobile;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public String getAgencyEmail() {
        return agencyEmail;
    }

    public void setAgencyEmail(String agencyEmail) {
        this.agencyEmail = agencyEmail;
    }

    public String getAgencyAdminFirstName() {
        return agencyAdminFirstName;
    }

    public void setAgencyAdminFirstName(String agencyAdminFirstName) {
        this.agencyAdminFirstName = agencyAdminFirstName;
    }

    public String getAgencyAdminLastName() {
        return agencyAdminLastName;
    }

    public void setAgencyAdminLastName(String agencyAdminLastName) {
        this.agencyAdminLastName = agencyAdminLastName;
    }

    public String getAgencyAdminMobileNo() {
        return agencyAdminMobileNo;
    }

    public void setAgencyAdminMobileNo(String agencyAdminMobileNo) {
        this.agencyAdminMobileNo = agencyAdminMobileNo;
    }

    public String getAgencyAdminPhoneNo() {
        return agencyAdminPhoneNo;
    }

    public void setAgencyAdminPhoneNo(String agencyAdminPhoneNo) {
        this.agencyAdminPhoneNo = agencyAdminPhoneNo;
    }

    public String getAgencyAdminEmail() {
        return agencyAdminEmail;
    }

    public void setAgencyAdminEmail(String agencyAdminEmail) {
        this.agencyAdminEmail = agencyAdminEmail;
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

    @Override
    public String toString() {
        return "MpathAgency{" + "agencyId=" + agencyId + ", agencyCode='" + agencyCode + '\'' + ", agencyName='" + agencyName + '\'' + ", agencyLegalName='" + agencyLegalName + '\'' + ", agencyBankAccountNo='" + agencyBankAccountNo + '\'' + ", agencyAddress='" + agencyAddress + '\'' + ", agencyMobile='" + agencyMobile + '\'' + ", agencyPhone='" + agencyPhone + '\'' + ", agencyEmail='" + agencyEmail + '\'' + ", agencyAdminFirstName='" + agencyAdminFirstName + '\'' + ", agencyAdminLastName='" + agencyAdminLastName + '\'' + ", agencyAdminMobileNo='" + agencyAdminMobileNo + '\'' + ", agencyAdminPhoneNo='" + agencyAdminPhoneNo + '\'' + ", agencyAdminEmail='" + agencyAdminEmail + '\'' + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
