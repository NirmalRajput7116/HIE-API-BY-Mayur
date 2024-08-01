package com.cellbeans.hspa.maccurrency;

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
@Table(name = "mac_currency")
public class MacCurrency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currencyId", unique = true, nullable = true)
    private long currencyId;

    @JsonInclude(NON_NULL)
    @Column(name = "currencyName")
    private String currencyName;

    @JsonInclude(NON_NULL)
    @Column(name = "currencyCode")
    private String currencyCode;

    @JsonInclude(NON_NULL)
    @Column(name = "currencyHigherDenomination")
    private String currencyHigherDenomination;

    @JsonInclude(NON_NULL)
    @Column(name = "currencyLowerDenomination")
    private String currencyLowerDenomination;

    @JsonInclude(NON_NULL)
    @Column(name = "currencySymbol")
    private String currencySymbol;

    @JsonInclude(NON_NULL)
    @Column(name = "currencyConversionFactor")
    private String currencyConversionFactor;

    @JsonInclude(NON_NULL)
    @Column(name = "isCurrencyDefault", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isCurrencyDefault = false;

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

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyHigherDenomination() {
        return currencyHigherDenomination;
    }

    public void setCurrencyHigherDenomination(String currencyHigherDenomination) {
        this.currencyHigherDenomination = currencyHigherDenomination;
    }

    public String getCurrencyLowerDenomination() {
        return currencyLowerDenomination;
    }

    public void setCurrencyLowerDenomination(String currencyLowerDenomination) {
        this.currencyLowerDenomination = currencyLowerDenomination;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyConversionFactor() {
        return currencyConversionFactor;
    }

    public void setCurrencyConversionFactor(String currencyConversionFactor) {
        this.currencyConversionFactor = currencyConversionFactor;
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

    public Boolean getIsCurrencyDefault() {
        return isCurrencyDefault;
    }

    public void setIsCurrencyDefault(Boolean isCurrencyDefault) {
        this.isCurrencyDefault = isCurrencyDefault;
    }

}
