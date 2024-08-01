package com.cellbeans.hspa.mbilltariffService;

import com.cellbeans.hspa.mstclass.MstClass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mbill_tariff_service_class")
public class MBillTariffServiceClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tscId;

    @ManyToOne
    @JoinColumn(name = "tscClassId")
    private MstClass tscClassId;

    @Column(name = "tscClassRate", columnDefinition = "decimal default '0'", nullable = true)
    private double tscClassRate;

    @Column(name = "tscOriClassRate", columnDefinition = "decimal default '0'", nullable = true)
    private double tscOriClassRate;

    @Column(name = "companyPay", columnDefinition = "decimal default '0'", nullable = true)
    private double companyPay;

    @Column(name = "patientPay", columnDefinition = "decimal default '0'", nullable = true)
    private double patientPay;

    @Column(name = "tscCoPay", columnDefinition = "decimal default '0'", nullable = true)
    private double tscCoPay;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

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

    public Long getTscId() {
        return tscId;
    }

    public void setTscId(Long tscId) {
        this.tscId = tscId;
    }

    public MstClass getTscClassId() {
        return tscClassId;
    }

    public void setTscClassId(MstClass tscClassId) {
        this.tscClassId = tscClassId;
    }

    public double getTscClassRate() {
        return tscClassRate;
    }

    public void setTscClassRate(double tscClassRate) {
        this.tscClassRate = tscClassRate;
    }

    public double getTscOriClassRate() {
        return tscOriClassRate;
    }

    public void setTscOriClassRate(double tscOriClassRate) {
        this.tscOriClassRate = tscOriClassRate;
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

    public double getTscCoPay() {
        return tscCoPay;
    }

    public void setTscCoPay(double tscCoPay) {
        this.tscCoPay = tscCoPay;
    }

    public double getCompanyPay() {
        return companyPay;
    }

    public void setCompanyPay(double companyPay) {
        this.companyPay = companyPay;
    }

    public double getPatientPay() {
        return patientPay;
    }

    public void setPatientPay(double patientPay) {
        this.patientPay = patientPay;
    }

}