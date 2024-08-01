package com.cellbeans.hspa.macledger;

import com.cellbeans.hspa.maccurrency.MacCurrency;
import com.cellbeans.hspa.mactaxtype.MacTaxType;
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
@Table(name = "mac_ledger")
public class MacLedger implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ledgerId", unique = true, nullable = true)
    private long ledgerId;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerName")
    private String ledgerName;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerCode")
    private String ledgerCode;

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
    @Column(name = "createdDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDatetime;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerOrderNumber")
    private String ledgerOrderNumber;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ledgerCurrencyId")
    private MacCurrency ledgerCurrencyId;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerParticipationVoucherType")
    private String ledgerParticipationVoucherType;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerOpeningBalance")
    private String ledgerOpeningBalance;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerDrCrStatus", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ledgerDrCrStatus = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerMaintainBalanceBillByBill", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ledgerMaintainBalanceBillByBill = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerDefaultCreditPeriod")
    private String ledgerDefaultCreditPeriod;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerIsTaxLedger", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ledgerIsTaxLedger = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ledgerTtId")
    private MacTaxType ledgerTtId;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerDueDate")
    private String ledgerDueDate;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerBankLedger", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ledgerBankLedger = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date ledgerDate;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerEffectiveReconcilationDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date ledgerEffectiveReconcilationDate;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerAmount")
    private double ledgerAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerCrDr", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ledgerCrDr = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerMailingDetails", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ledgerMailingDetails = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerAddress")
    private String ledgerAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerMailId")
    private String ledgerMailId;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerMstNumber")
    private String ledgerMstNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerCstNumber")
    private String ledgerCstNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerTinNumber")
    private String ledgerTinNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerPanNumber")
    private String ledgerPanNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "ledgerDlNumber")
    private String ledgerDlNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "updatedDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedDatetime;

    public long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(int ledgerId) {
        this.ledgerId = ledgerId;
    }

    public void setLedgerId(long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getLedgerCode() {
        return ledgerCode;
    }

    public void setLedgerCode(String ledgerCode) {
        this.ledgerCode = ledgerCode;
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

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getLedgerOrderNumber() {
        return ledgerOrderNumber;
    }

    public void setLedgerOrderNumber(String ledgerOrderNumber) {
        this.ledgerOrderNumber = ledgerOrderNumber;
    }

    public MacCurrency getLedgerCurrencyId() {
        return ledgerCurrencyId;
    }

    public void setLedgerCurrencyId(MacCurrency ledgerCurrencyId) {
        this.ledgerCurrencyId = ledgerCurrencyId;
    }

    public String getLedgerParticipationVoucherType() {
        return ledgerParticipationVoucherType;
    }

    public void setLedgerParticipationVoucherType(String ledgerParticipationVoucherType) {
        this.ledgerParticipationVoucherType = ledgerParticipationVoucherType;
    }

    public String getLedgerOpeningBalance() {
        return ledgerOpeningBalance;
    }

    public void setLedgerOpeningBalance(String ledgerOpeningBalance) {
        this.ledgerOpeningBalance = ledgerOpeningBalance;
    }

    public boolean getLedgerDrCrStatus() {
        return ledgerDrCrStatus;
    }

    public void setLedgerDrCrStatus(boolean ledgerDrCrStatus) {
        this.ledgerDrCrStatus = ledgerDrCrStatus;
    }

    public void setLedgerDrCrStatus(Boolean ledgerDrCrStatus) {
        this.ledgerDrCrStatus = ledgerDrCrStatus;
    }

    public boolean getLedgerMaintainBalanceBillByBill() {
        return ledgerMaintainBalanceBillByBill;
    }

    public void setLedgerMaintainBalanceBillByBill(boolean ledgerMaintainBalanceBillByBill) {
        this.ledgerMaintainBalanceBillByBill = ledgerMaintainBalanceBillByBill;
    }

    public void setLedgerMaintainBalanceBillByBill(Boolean ledgerMaintainBalanceBillByBill) {
        this.ledgerMaintainBalanceBillByBill = ledgerMaintainBalanceBillByBill;
    }

    public String getLedgerDefaultCreditPeriod() {
        return ledgerDefaultCreditPeriod;
    }

    public void setLedgerDefaultCreditPeriod(String ledgerDefaultCreditPeriod) {
        this.ledgerDefaultCreditPeriod = ledgerDefaultCreditPeriod;
    }

    public boolean getLedgerIsTaxLedger() {
        return ledgerIsTaxLedger;
    }

    public void setLedgerIsTaxLedger(boolean ledgerIsTaxLedger) {
        this.ledgerIsTaxLedger = ledgerIsTaxLedger;
    }

    public void setLedgerIsTaxLedger(Boolean ledgerIsTaxLedger) {
        this.ledgerIsTaxLedger = ledgerIsTaxLedger;
    }

    public MacTaxType getLedgerTtId() {
        return ledgerTtId;
    }

    public void setLedgerTtId(MacTaxType ledgerTtId) {
        this.ledgerTtId = ledgerTtId;
    }

    public String getLedgerDueDate() {
        return ledgerDueDate;
    }

    public void setLedgerDueDate(String ledgerDueDate) {
        this.ledgerDueDate = ledgerDueDate;
    }

    public boolean getLedgerBankLedger() {
        return ledgerBankLedger;
    }

    public void setLedgerBankLedger(boolean ledgerBankLedger) {
        this.ledgerBankLedger = ledgerBankLedger;
    }

    public void setLedgerBankLedger(Boolean ledgerBankLedger) {
        this.ledgerBankLedger = ledgerBankLedger;
    }

    public Date getLedgerDate() {
        return ledgerDate;
    }

    public void setLedgerDate(Date ledgerDate) {
        this.ledgerDate = ledgerDate;
    }

    public Date getLedgerEffectiveReconcilationDate() {
        return ledgerEffectiveReconcilationDate;
    }

    public void setLedgerEffectiveReconcilationDate(Date ledgerEffectiveReconcilationDate) {
        this.ledgerEffectiveReconcilationDate = ledgerEffectiveReconcilationDate;
    }

    public double getLedgerAmount() {
        return ledgerAmount;
    }

    public void setLedgerAmount(double ledgerAmount) {
        this.ledgerAmount = ledgerAmount;
    }

    public boolean getLedgerCrDr() {
        return ledgerCrDr;
    }

    public void setLedgerCrDr(boolean ledgerCrDr) {
        this.ledgerCrDr = ledgerCrDr;
    }

    public void setLedgerCrDr(Boolean ledgerCrDr) {
        this.ledgerCrDr = ledgerCrDr;
    }

    public boolean getLedgerMailingDetails() {
        return ledgerMailingDetails;
    }

    public void setLedgerMailingDetails(boolean ledgerMailingDetails) {
        this.ledgerMailingDetails = ledgerMailingDetails;
    }

    public void setLedgerMailingDetails(Boolean ledgerMailingDetails) {
        this.ledgerMailingDetails = ledgerMailingDetails;
    }

    public String getLedgerAddress() {
        return ledgerAddress;
    }

    public void setLedgerAddress(String ledgerAddress) {
        this.ledgerAddress = ledgerAddress;
    }

    public String getLedgerMailId() {
        return ledgerMailId;
    }

    public void setLedgerMailId(String ledgerMailId) {
        this.ledgerMailId = ledgerMailId;
    }

    public String getLedgerMstNumber() {
        return ledgerMstNumber;
    }

    public void setLedgerMstNumber(String ledgerMstNumber) {
        this.ledgerMstNumber = ledgerMstNumber;
    }

    public String getLedgerCstNumber() {
        return ledgerCstNumber;
    }

    public void setLedgerCstNumber(String ledgerCstNumber) {
        this.ledgerCstNumber = ledgerCstNumber;
    }

    public String getLedgerTinNumber() {
        return ledgerTinNumber;
    }

    public void setLedgerTinNumber(String ledgerTinNumber) {
        this.ledgerTinNumber = ledgerTinNumber;
    }

    public String getLedgerPanNumber() {
        return ledgerPanNumber;
    }

    public void setLedgerPanNumber(String ledgerPanNumber) {
        this.ledgerPanNumber = ledgerPanNumber;
    }

    public String getLedgerDlNumber() {
        return ledgerDlNumber;
    }

    public void setLedgerDlNumber(String ledgerDlNumber) {
        this.ledgerDlNumber = ledgerDlNumber;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
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

}
