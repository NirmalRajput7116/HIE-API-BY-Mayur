package com.cellbeans.hspa.tbillTariffReceipt;

import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbill_tariff_receipt")
public class TbillTariffReceipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trId", unique = true, nullable = true)
    private Long trId;

    @JsonInclude(NON_NULL)
    @Column(name = "trReceiptNumber")
    private String trReceiptNumber;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinTable(name = "trBillSet")
    private List<TBillBill> trBillSet;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "trTariffId")
    private MbillTariff trTariffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "trCcId")
    private MstCashCounter trCcId;

    @JsonInclude(NON_NULL)
    @Column(name = "trAmountPaid", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double trAmountPaid;

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "trPaymentMode")
    private List<TbillTRPaymentMode> trPaymentMode;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "billNarration", columnDefinition = "TEXT")
    private String billNarration;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    public List<TBillBill> getTrBillSet() {
        return trBillSet;
    }

    public void setTrBillSet(List<TBillBill> trBillSet) {
        this.trBillSet = trBillSet;
    }

    public MbillTariff getTrTariffId() {
        return trTariffId;
    }

    public void setTrTariffId(MbillTariff trTariffId) {
        this.trTariffId = trTariffId;
    }

    public double getTrAmountPaid() {
        return trAmountPaid;
    }

    public void setTrAmountPaid(double trAmountPaid) {
        this.trAmountPaid = trAmountPaid;
    }

    public List<TbillTRPaymentMode> getTrPaymentMode() {
        return trPaymentMode;
    }

    public void setTrPaymentMode(List<TbillTRPaymentMode> trPaymentMode) {
        this.trPaymentMode = trPaymentMode;
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

    public String getBillNarration() {
        return billNarration;
    }

    public void setBillNarration(String billNarration) {
        this.billNarration = billNarration;
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

    public Long getTrId() {
        return trId;
    }

    public void setTrId(Long trId) {
        this.trId = trId;
    }

    public String getTrReceiptNumber() {
        return trReceiptNumber;
    }

    public void setTrReceiptNumber(String trReceiptNumber) {
        this.trReceiptNumber = trReceiptNumber;
    }

    public MstCashCounter getTrCcId() {
        return trCcId;
    }

    public void setTrCcId(MstCashCounter trCcId) {
        this.trCcId = trCcId;
    }

}