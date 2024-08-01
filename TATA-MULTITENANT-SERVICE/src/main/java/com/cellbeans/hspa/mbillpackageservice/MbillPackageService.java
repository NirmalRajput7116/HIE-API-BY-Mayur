package com.cellbeans.hspa.mbillpackageservice;

import com.cellbeans.hspa.mbillpackage.MbillPackage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "package_service")
public class MbillPackageService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psId", unique = true, nullable = true)
    private long psId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pkgId")
    private MbillPackage pkgId;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceId")
    private Integer serviceId;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceClassId")
    private Integer serviceClassId;

    @JsonInclude(NON_NULL)
    @Column(name = "pkgQty", columnDefinition = "decimal default 0", nullable = true)
    private Integer pkgQty;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceBaseRate", columnDefinition = "decimal default 0", nullable = true)
    private double serviceBaseRate;

    @JsonInclude(NON_NULL)
    @Column(name = "servicePkgRate", columnDefinition = "decimal default 0", nullable = true)
    private double servicePkgRate;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    public long getPsId() {
        return psId;
    }

    public void setPsId(long psId) {
        this.psId = psId;
    }

    public MbillPackage getPkgId() {
        return pkgId;
    }

    public void setPkgId(MbillPackage pkgId) {
        this.pkgId = pkgId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getPkgQty() {
        return pkgQty;
    }

    public void setPkgQty(Integer pkgQty) {
        this.pkgQty = pkgQty;
    }

    public double getServiceBaseRate() {
        return serviceBaseRate;
    }

    public void setServiceBaseRate(double serviceBaseRate) {
        this.serviceBaseRate = serviceBaseRate;
    }

    public double getServicePkgRate() {
        return servicePkgRate;
    }

    public void setServicePkgRate(double servicePkgRate) {
        this.servicePkgRate = servicePkgRate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }
}