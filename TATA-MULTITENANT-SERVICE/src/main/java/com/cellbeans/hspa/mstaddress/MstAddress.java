package com.cellbeans.hspa.mstaddress;

import com.cellbeans.hspa.mstaddresstype.MstAddressType;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstcountry.MstCountry;
import com.cellbeans.hspa.mststate.MstState;
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
@Table(name = "mst_address")
public class MstAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId", unique = true, nullable = true)
    private long addressId;

    @JsonInclude(NON_NULL)
    @Column(name = "addressAddress")
    private String addressAddress;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "addressCountryId")
    private MstCountry addressCountryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "addressStateId")
    private MstState addressStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "addressCityId")
    private MstCity addressCityId;

    @JsonInclude(NON_NULL)
    @Column(name = "addressArea")
    private String addressArea;

    @JsonInclude(NON_NULL)
    @Column(name = "addressZip")
    private String addressZip;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "addressAtId")
    private MstAddressType addressAtId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getAddressAddress() {
        return addressAddress;
    }

    public void setAddressAddress(String addressAddress) {
        this.addressAddress = addressAddress;
    }

    public MstCountry getAddressCountryId() {
        return addressCountryId;
    }

    public void setAddressCountryId(MstCountry addressCountryId) {
        this.addressCountryId = addressCountryId;
    }

    public MstState getAddressStateId() {
        return addressStateId;
    }

    public void setAddressStateId(MstState addressStateId) {
        this.addressStateId = addressStateId;
    }

    public MstCity getAddressCityId() {
        return addressCityId;
    }

    public void setAddressCityId(MstCity addressCityId) {
        this.addressCityId = addressCityId;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public MstAddressType getAddressAtId() {
        return addressAtId;
    }

    public void setAddressAtId(MstAddressType addressAtId) {
        this.addressAtId = addressAtId;
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
