package com.cellbeans.hspa.mstunit;

import com.cellbeans.hspa.MstCluster.MstCluster;
import com.cellbeans.hspa.mstUnitType.MstUnitType;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstorg.MstOrg;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "mst_unit")
public class MstUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unitId", unique = true, nullable = true)
    private long unitId;

    @JsonInclude(NON_NULL)
    @Column(name = "unitName")
    private String unitName;

    @JsonInclude(NON_NULL)
    @Column(name = "unitpostfix")
    private String unitpostfix;

    @JsonInclude(NON_NULL)
    @Column(name = "unitcenter")
    private String unitcenter;

    @JsonInclude(NON_NULL)
    @Column(name = "unitAddress")
    private String unitAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "unitEmail")
    private String unitEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "unitMobile")
    private String unitMobile;

    @JsonInclude(NON_NULL)
    @Column(name = "unitPhone")
    private String unitPhone;

    @JsonInclude(NON_NULL)
    @Column(name = "unitContactPerson")
    private String unitContactPerson;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "unitOrgId")
    private MstOrg unitOrgId;

    @JsonInclude(NON_NULL)
    @Column(name = "unitCode")
    private String unitCode;

    @JsonInclude(NON_NULL)
    @Column(name = "unitClinicContactNo")
    private String unitClinicContactNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitPharmacyLicenseNo")
    private String unitPharmacyLicenseNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitPharmacyStoreName")
    private String unitPharmacyStoreName;

    @JsonInclude(NON_NULL)
    @Column(name = "unitPharmacyGstNo")
    private String unitPharmacyGstNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitClinicRegistrationNo")
    private String unitClinicRegistrationNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitShopAndEstablishmentNo")
    private String unitShopAndEstablishmentNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitTradeNo")
    private String unitTradeNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitServer")
    private String unitServer;

    @JsonInclude(NON_NULL)
    @Column(name = "unitDatabase")
    private String unitDatabase;

    @JsonInclude(NON_NULL)
    @Column(name = "unitFaxNo")
    private String unitFaxNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitWebSite")
    private String unitWebSite;

    @JsonInclude(NON_NULL)
    @ManyToOne
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "unitCityId")
    private MstCity unitCityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "unitClusterId")
    private MstCluster unitClusterId;

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "unitClusterIdList")
    private List<MstCluster> unitClusterIdList;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "unitUnittypeId")
    private MstUnitType unitUnittypeId;

    @JsonInclude(NON_NULL)
    @Column(name = "unitAddressZip")
    private String unitAddressZip;

    @JsonInclude(NON_NULL)
    @Column(name = "unitAddressArea")
    private String unitAddressArea;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "unitParentId")
    private Long unitParentId;

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

    public MstUnitType getUnitUnittypeId() {
        return unitUnittypeId;
    }

    public void setUnitUnittypeId(MstUnitType unitUnittypeId) {
        this.unitUnittypeId = unitUnittypeId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitAddressArea() {
        return unitAddressArea;
    }

    public void setUnitAddressArea(String unitAddressArea) {
        this.unitAddressArea = unitAddressArea;
    }

    public String getUnitAddressZip() {
        return unitAddressZip;
    }

    public void setUnitAddressZip(String unitAddressZip) {
        this.unitAddressZip = unitAddressZip;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public MstCity getUnitCityId() {
        return unitCityId;
    }

    public void setUnitCityId(MstCity unitCityId) {
        this.unitCityId = unitCityId;
    }

    public String getUnitEmail() {
        return unitEmail;
    }

    public void setUnitEmail(String unitEmail) {
        this.unitEmail = unitEmail;
    }

    public String getUnitMobile() {
        return unitMobile;
    }

    public void setUnitMobile(String unitMobile) {
        this.unitMobile = unitMobile;
    }

    public String getUnitPhone() {
        return unitPhone;
    }

    public void setUnitPhone(String unitPhone) {
        this.unitPhone = unitPhone;
    }

    public String getUnitContactPerson() {
        return unitContactPerson;
    }

    public void setUnitContactPerson(String unitContactPerson) {
        this.unitContactPerson = unitContactPerson;
    }

    public MstOrg getUnitOrgId() {
        return unitOrgId;
    }

    public void setUnitOrgId(MstOrg unitOrgId) {
        this.unitOrgId = unitOrgId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitClinicContactNo() {
        return unitClinicContactNo;
    }

    public void setUnitClinicContactNo(String unitClinicContactNo) {
        this.unitClinicContactNo = unitClinicContactNo;
    }

    public String getUnitPharmacyLicenseNo() {
        return unitPharmacyLicenseNo;
    }

    public void setUnitPharmacyLicenseNo(String unitPharmacyLicenseNo) {
        this.unitPharmacyLicenseNo = unitPharmacyLicenseNo;
    }

    public String getUnitClinicRegistrationNo() {
        return unitClinicRegistrationNo;
    }

    public void setUnitClinicRegistrationNo(String unitClinicRegistrationNo) {
        this.unitClinicRegistrationNo = unitClinicRegistrationNo;
    }

    public String getUnitShopAndEstablishmentNo() {
        return unitShopAndEstablishmentNo;
    }

    public void setUnitShopAndEstablishmentNo(String unitShopAndEstablishmentNo) {
        this.unitShopAndEstablishmentNo = unitShopAndEstablishmentNo;
    }

    public String getUnitTradeNo() {
        return unitTradeNo;
    }

    public void setUnitTradeNo(String unitTradeNo) {
        this.unitTradeNo = unitTradeNo;
    }

    public String getUnitServer() {
        return unitServer;
    }

    public void setUnitServer(String unitServer) {
        this.unitServer = unitServer;
    }

    public String getUnitDatabase() {
        return unitDatabase;
    }

    public void setUnitDatabase(String unitDatabase) {
        this.unitDatabase = unitDatabase;
    }

    public String getUnitFaxNo() {
        return unitFaxNo;
    }

    public void setUnitFaxNo(String unitFaxNo) {
        this.unitFaxNo = unitFaxNo;
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

    public String getUnitWebSite() {
        return unitWebSite;
    }

    public void setUnitWebSite(String unitWebSite) {
        this.unitWebSite = unitWebSite;
    }

    public String getUnitpostfix() {
        return unitpostfix;
    }

    public void setUnitpostfix(String unitpostfix) {
        this.unitpostfix = unitpostfix;
    }

    public String getUnitcenter() {
        return unitcenter;
    }

    public void setUnitcenter(String unitcenter) {
        this.unitcenter = unitcenter;
    }

    public String getUnitPharmacyStoreName() {
        return unitPharmacyStoreName;
    }

    public void setUnitPharmacyStoreName(String unitPharmacyStoreName) {
        this.unitPharmacyStoreName = unitPharmacyStoreName;
    }

    public String getUnitPharmacyGstNo() {
        return unitPharmacyGstNo;
    }

    public void setUnitPharmacyGstNo(String unitPharmacyGstNo) {
        this.unitPharmacyGstNo = unitPharmacyGstNo;
    }

    public MstCluster getUnitClusterId() {
        return unitClusterId;
    }

    public void setUnitClusterId(MstCluster unitClusterId) {
        this.unitClusterId = unitClusterId;
    }

    public Long getUnitParentId() {
        return unitParentId;
    }

    public void setUnitParentId(Long unitParentId) {
        this.unitParentId = unitParentId;
    }

    public List<MstCluster> getUnitClusterIdList() {
        return unitClusterIdList;
    }

    public void setUnitClusterIdList(List<MstCluster> unitClusterIdList) {
        this.unitClusterIdList = unitClusterIdList;
    }
}
