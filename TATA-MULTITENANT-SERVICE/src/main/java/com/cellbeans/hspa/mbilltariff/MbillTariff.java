package com.cellbeans.hspa.mbilltariff;

import com.cellbeans.hspa.mbilltariffService.MbillTariffService;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstunit.MstUnit;
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
@Table(name = "mbill_tariff")
public class MbillTariff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tariffId", unique = true, nullable = true)
    private long tariffId;

    @JsonInclude(NON_NULL)
    @Column(name = "tariffName")
    private String tariffName;

    @JsonInclude(NON_NULL)
    @Column(name = "tarrifType")
    private Integer tarrifType;

    @JsonInclude(NON_NULL)
    @Column(name = "tariffCode")
    private String tariffCode;

    @JsonInclude(NON_NULL)
    @Column(name = "tariffCoPay")
    private double tariffCoPay;

    @JsonInclude(NON_NULL)
    @Column(name = "tariffDiscount")
    private double tariffDiscount;

    @JsonInclude(NON_NULL)
    @Column(name = "coPayForPharmacy", columnDefinition = "decimal default 0", nullable = true)
    private double coPayForPharmacy = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "discountForPharmacy", columnDefinition = "decimal default 0", nullable = true)
    private double discountForPharmacy = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "patientPayFroPharmacy", columnDefinition = "decimal default 0", nullable = true)
    private double patientPayFroPharmacy = 0;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tariffServices")
    private List<MbillTariffService> tariffServices;

    @Transient
    private List<MbillTariffService> tariffServiceslist;

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

    @JsonInclude(NON_NULL)
    @ManyToMany
    //@JoinTable(name = "tariffGender")
    private List<MstGender> tarrifGenders;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tariffunitId")
    private MstUnit tariffunitId;

    @JsonInclude(NON_NULL)
    @Column(name = "tarrifagefrom")
    private String tarrifagefrom;

    @JsonInclude(NON_NULL)
    @Column(name = "tarrifageto")
    private String tarrifageto;

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
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

    public String getTariffCode() {
        return tariffCode;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public double getTariffCoPay() {
        return tariffCoPay;
    }

    public void setTariffCoPay(double tariffCoPay) {
        this.tariffCoPay = tariffCoPay;
    }

    public double getTariffDiscount() {
        return tariffDiscount;
    }

    public void setTariffDiscount(double tariffDiscount) {
        this.tariffDiscount = tariffDiscount;
    }

    public List<MbillTariffService> getTariffServices() {
        return tariffServices;
    }

    public void setTariffServices(List<MbillTariffService> tariffServices) {
        this.tariffServices = tariffServices;
    }

    public double getCoPayForPharmacy() {
        return coPayForPharmacy;
    }

    public void setCoPayForPharmacy(double coPayForPharmacy) {
        this.coPayForPharmacy = coPayForPharmacy;
    }

    public double getDiscountForPharmacy() {
        return discountForPharmacy;
    }

    public void setDiscountForPharmacy(double discountForPharmacy) {
        this.discountForPharmacy = discountForPharmacy;
    }

    public double getPatientPayFroPharmacy() {
        return patientPayFroPharmacy;
    }

    public void setPatientPayFroPharmacy(double patientPayFroPharmacy) {
        this.patientPayFroPharmacy = patientPayFroPharmacy;
    }

    public MstUnit getTariffunitId() {
        return tariffunitId;
    }

    public void setTariffunitId(MstUnit tariffunitId) {
        this.tariffunitId = tariffunitId;
    }

    public String getTarrifagefrom() {
        return tarrifagefrom;
    }

    public void setTarrifagefrom(String tarrifagefrom) {
        this.tarrifagefrom = tarrifagefrom;
    }

    public String getTarrifageto() {
        return tarrifageto;
    }

    public void setTarrifageto(String tarrifageto) {
        this.tarrifageto = tarrifageto;
    }

    public List<MstGender> getTarrifGenders() {
        return tarrifGenders;
    }

    public void setTarrifGenders(List<MstGender> tarrifGenders) {
        this.tarrifGenders = tarrifGenders;
    }

    public List<MbillTariffService> getTariffServiceslist() {
        return tariffServiceslist;
    }

    public void setTariffServiceslist(List<MbillTariffService> tariffServiceslist) {
        this.tariffServiceslist = tariffServiceslist;
    }

    public Integer getTarrifType() {
        return tarrifType;
    }

    public void setTarrifType(Integer tarrifType) {
        this.tarrifType = tarrifType;
    }
}