package com.cellbeans.hspa.tbillTariffReceipt;

import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;

@Entity
@Table(name = "tbill_t_r_payment_mode")
public class TbillTRPaymentMode {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpmId", unique = true, nullable = true)
    private Long tpmId;

    @ManyToOne
    @JoinColumn(name = "tpmPmId")
    private MstPaymentMode tpmPmId;

    @Column(name = "tpmAmountPaid")
    private double tpmAmountPaid;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @Column(name = "billNarration", columnDefinition = "TEXT")
    private String billNarration;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    public Long getTpmId() {
        return tpmId;
    }

    public void setTpmId(Long tpmId) {
        this.tpmId = tpmId;
    }

    public MstPaymentMode getTpmPmId() {
        return tpmPmId;
    }

    public void setTpmPmId(MstPaymentMode tpmPmId) {
        this.tpmPmId = tpmPmId;
    }

    public double getTpmAmountPaid() {
        return tpmAmountPaid;
    }

    public void setTpmAmountPaid(double tpmAmountPaid) {
        this.tpmAmountPaid = tpmAmountPaid;
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

}
