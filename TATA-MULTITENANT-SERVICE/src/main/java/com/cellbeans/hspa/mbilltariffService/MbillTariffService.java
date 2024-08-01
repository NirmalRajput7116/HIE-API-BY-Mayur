package com.cellbeans.hspa.mbilltariffService;

import com.cellbeans.hspa.mbillservice.MbillService;
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
@Table(name = "mbill_tariff_service")
public class MbillTariffService implements Serializable {

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tsTariffServiceClass")
    List<MBillTariffServiceClass> tsTariffServiceClassList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tsId;
    @JsonInclude(NON_NULL)
    @Column(name = "tsTariffId", nullable = true)
    private String tsTariffId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tsServiceId")
    private MbillService tsServiceId;
    @JsonInclude(NON_NULL)
    @Column(name = "tsServiceBaseRate", columnDefinition = "decimal default '0'", nullable = true)
    private double tsServiceBaseRate;

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

    public MbillTariffService() {
    }

    public MbillTariffService(MbillService tsServiceId, Boolean isActive, Boolean isDeleted, String createdBy, String lastModifiedBy, Date createdDate, Date lastModifiedDate) {
        this.tsServiceId = tsServiceId;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getTsId() {
        return tsId;
    }

    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public MbillService getTsServiceId() {
        return tsServiceId;
    }

    public void setTsServiceId(MbillService tsServiceId) {
        this.tsServiceId = tsServiceId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
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

    public List<MBillTariffServiceClass> getTsTariffServiceClassList() {
        return tsTariffServiceClassList;
    }

    public void setTsTariffServiceClassList(List<MBillTariffServiceClass> tsTariffServiceClassList) {
        this.tsTariffServiceClassList = tsTariffServiceClassList;
    }

    public String getTsTariffId() {
        return tsTariffId;
    }

    public void setTsTariffId(String tsTariffId) {
        this.tsTariffId = tsTariffId;
    }

    public double getTsServiceBaseRate() {
        return tsServiceBaseRate;
    }

    public void setTsServiceBaseRate(double tsServiceBaseRate) {
        this.tsServiceBaseRate = tsServiceBaseRate;
    }

}