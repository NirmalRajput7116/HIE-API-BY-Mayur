package com.cellbeans.hspa.invsupplier;

import com.cellbeans.hspa.invitemtermsandcondition.InvItemTermsAndCondition;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.maccurrency.MacCurrency;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstcountry.MstCountry;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import com.cellbeans.hspa.mststate.MstState;
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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Inya Gaikwad
 * This Class is responsible for To select the supplier to who is providing item,Service
 * <p>
 * Class has
 * {@link ManyToOne} with
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inv_supplier")
public class InvSupplier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierId", unique = true, nullable = true)
    private long supplierId;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierName")
    private String supplierName;

    @JsonInclude(NON_NULL)
    @Column(name = "budgetAmount")
    private String budgetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierCode")
    private String supplierCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "supplierCountryId")
    private MstCountry supplierCountryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "supplierStateId")
    private MstState supplierStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "supplierCityId")
    private MstCity supplierCityId;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierZipcode")
    private String supplierZipcode;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierAddressLineOne")
    private String supplierAddressLineOne;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierAddressLineTwo")
    private String supplierAddressLineTwo;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierAddressLineThree")
    private String supplierAddressLineThree;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierImplantSupplier", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean supplierImplantSupplier = false;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierContactPersonOneName")
    private String supplierContactPersonOneName;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierContactPersonTwoName")
    private String supplierContactPersonTwoName;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierContactPersonOneCell")
    private String supplierContactPersonOneCell;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierContactPersonTwoCell")
    private String supplierContactPersonTwoCell;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierContactPersonOneEmail")
    private String supplierContactPersonOneEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierContactPersonTwoEmail")
    private String supplierContactPersonTwoEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierPhoneNumber")
    private String supplierPhoneNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierFax")
    private String supplierFax;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierAccountNo")
    private String supplierAccountNo;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierBankBrachName")
    private String supplierBankBrachName;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierBankName")
    private String supplierBankName;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierIfscCode")
    private String supplierIfscCode;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierAccountType")
    private String supplierAccountType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "supplierPmId")
    private MstPaymentMode supplierPmId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "supplierTaxId")
    private InvTax supplierTaxId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "supplierItacId")
    private InvItemTermsAndCondition supplierItacId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "supplierCurrencyId")
    private MacCurrency supplierCurrencyId;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierMstNumber")
    private String supplierMstNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierDrugLicenceNumber")
    private String supplierDrugLicenceNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierGstType")
    private String supplierGstType;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierCstNumber")
    private String supplierCstNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierServiceTaxNumber")
    private String supplierServiceTaxNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierVatNumber")
    private String supplierVatNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "supplierPanNumber")
    private String supplierPanNumber;

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
    @ManyToOne
    @JoinColumn(name = "supplierUnitId")
    private MstUnit supplierUnitId;

    public MstUnit getSupplierUnitId() {
        return supplierUnitId;
    }

    public void setSupplierUnitId(MstUnit supplierUnitId) {
        this.supplierUnitId = supplierUnitId;
    }

    public InvTax getSupplierTaxId() {
        return supplierTaxId;
    }

    public void setSupplierTaxId(InvTax supplierTaxId) {
        this.supplierTaxId = supplierTaxId;
    }

    public void setSupplierImplantSupplier(Boolean supplierImplantSupplier) {
        this.supplierImplantSupplier = supplierImplantSupplier;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }
//    public void setSupplierId(int supplierId) {
//        this.supplierId = supplierId;
//    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public MstCountry getSupplierCountryId() {
        return supplierCountryId;
    }

    public void setSupplierCountryId(MstCountry supplierCountryId) {
        this.supplierCountryId = supplierCountryId;
    }

    public MstState getSupplierStateId() {
        return supplierStateId;
    }

    public void setSupplierStateId(MstState supplierStateId) {
        this.supplierStateId = supplierStateId;
    }

    public MstCity getSupplierCityId() {
        return supplierCityId;
    }

    public void setSupplierCityId(MstCity supplierCityId) {
        this.supplierCityId = supplierCityId;
    }

    public String getSupplierZipcode() {
        return supplierZipcode;
    }

    public void setSupplierZipcode(String supplierZipcode) {
        this.supplierZipcode = supplierZipcode;
    }

    public String getSupplierAddressLineOne() {
        return supplierAddressLineOne;
    }

    public void setSupplierAddressLineOne(String supplierAddressLineOne) {
        this.supplierAddressLineOne = supplierAddressLineOne;
    }

    public String getSupplierAddressLineTwo() {
        return supplierAddressLineTwo;
    }

    public void setSupplierAddressLineTwo(String supplierAddressLineTwo) {
        this.supplierAddressLineTwo = supplierAddressLineTwo;
    }

    public String getSupplierAddressLineThree() {
        return supplierAddressLineThree;
    }

    public void setSupplierAddressLineThree(String supplierAddressLineThree) {
        this.supplierAddressLineThree = supplierAddressLineThree;
    }

    public boolean getSupplierImplantSupplier() {
        return supplierImplantSupplier;
    }

    public void setSupplierImplantSupplier(boolean supplierImplantSupplier) {
        this.supplierImplantSupplier = supplierImplantSupplier;
    }

    public String getSupplierContactPersonOneName() {
        return supplierContactPersonOneName;
    }

    public void setSupplierContactPersonOneName(String supplierContactPersonOneName) {
        this.supplierContactPersonOneName = supplierContactPersonOneName;
    }

    public String getSupplierContactPersonTwoName() {
        return supplierContactPersonTwoName;
    }

    public void setSupplierContactPersonTwoName(String supplierContactPersonTwoName) {
        this.supplierContactPersonTwoName = supplierContactPersonTwoName;
    }

    public String getSupplierContactPersonOneCell() {
        return supplierContactPersonOneCell;
    }

    public void setSupplierContactPersonOneCell(String supplierContactPersonOneCell) {
        this.supplierContactPersonOneCell = supplierContactPersonOneCell;
    }

    public String getSupplierContactPersonTwoCell() {
        return supplierContactPersonTwoCell;
    }

    public void setSupplierContactPersonTwoCell(String supplierContactPersonTwoCell) {
        this.supplierContactPersonTwoCell = supplierContactPersonTwoCell;
    }

    public String getSupplierContactPersonOneEmail() {
        return supplierContactPersonOneEmail;
    }

    public void setSupplierContactPersonOneEmail(String supplierContactPersonOneEmail) {
        this.supplierContactPersonOneEmail = supplierContactPersonOneEmail;
    }

    public String getSupplierContactPersonTwoEmail() {
        return supplierContactPersonTwoEmail;
    }

    public void setSupplierContactPersonTwoEmail(String supplierContactPersonTwoEmail) {
        this.supplierContactPersonTwoEmail = supplierContactPersonTwoEmail;
    }

    public String getSupplierPhoneNumber() {
        return supplierPhoneNumber;
    }

    public void setSupplierPhoneNumber(String supplierPhoneNumber) {
        this.supplierPhoneNumber = supplierPhoneNumber;
    }

    public String getSupplierFax() {
        return supplierFax;
    }

    public void setSupplierFax(String supplierFax) {
        this.supplierFax = supplierFax;
    }

    public MstPaymentMode getSupplierPmId() {
        return supplierPmId;
    }

    public void setSupplierPmId(MstPaymentMode supplierPmId) {
        this.supplierPmId = supplierPmId;
    }

    public InvItemTermsAndCondition getSupplierItacId() {
        return supplierItacId;
    }

    public void setSupplierItacId(InvItemTermsAndCondition supplierItacId) {
        this.supplierItacId = supplierItacId;
    }

    public void setSupplierTpId(InvItemTermsAndCondition supplierItacId) {
        this.supplierItacId = supplierItacId;
    }

    public String getSupplierMstNumber() {
        return supplierMstNumber;
    }

    public void setSupplierMstNumber(String supplierMstNumber) {
        this.supplierMstNumber = supplierMstNumber;
    }

    public String getSupplierDrugLicenceNumber() {
        return supplierDrugLicenceNumber;
    }

    public void setSupplierDrugLicenceNumber(String supplierDrugLicenceNumber) {
        this.supplierDrugLicenceNumber = supplierDrugLicenceNumber;
    }

    public String getSupplierGstType() {
        return supplierGstType;
    }

    public void setSupplierGstType(String supplierGstType) {
        this.supplierGstType = supplierGstType;
    }

    public String getSupplierCstNumber() {
        return supplierCstNumber;
    }

    public void setSupplierCstNumber(String supplierCstNumber) {
        this.supplierCstNumber = supplierCstNumber;
    }

    public String getSupplierServiceTaxNumber() {
        return supplierServiceTaxNumber;
    }

    public void setSupplierServiceTaxNumber(String supplierServiceTaxNumber) {
        this.supplierServiceTaxNumber = supplierServiceTaxNumber;
    }

    public String getSupplierVatNumber() {
        return supplierVatNumber;
    }

    public void setSupplierVatNumber(String supplierVatNumber) {
        this.supplierVatNumber = supplierVatNumber;
    }

    public String getSupplierPanNumber() {
        return supplierPanNumber;
    }

    public void setSupplierPanNumber(String supplierPanNumber) {
        this.supplierPanNumber = supplierPanNumber;
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

    public MacCurrency getSupplierCurrencyId() {
        return supplierCurrencyId;
    }

    public void setSupplierCurrencyId(MacCurrency supplierCurrencyId) {
        this.supplierCurrencyId = supplierCurrencyId;
    }

    public String getSupplierAccountNo() {
        return supplierAccountNo;
    }

    public void setSupplierAccountNo(String supplierAccountNo) {
        this.supplierAccountNo = supplierAccountNo;
    }

    public String getSupplierBankBrachName() {
        return supplierBankBrachName;
    }

    public void setSupplierBankBrachName(String suplierBankBrachName) {
        this.supplierBankBrachName = suplierBankBrachName;
    }

    public String getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(String suplierBankName) {
        this.supplierBankName = suplierBankName;
    }

    public String getSupplierIfscCode() {
        return supplierIfscCode;
    }

    public void setSupplierIfscCode(String suplierIfscCode) {
        this.supplierIfscCode = suplierIfscCode;
    }

    public String getSupplierAccountType() {
        return supplierAccountType;
    }

    public void setSupplierAccountType(String suplierAccountType) {
        this.supplierAccountType = suplierAccountType;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
}
