package com.cellbeans.hspa.tinvpharmacybillrecepit;

import com.cellbeans.hspa.mstbank.MstBank;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
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
@Table(name = "tinv_pharmacy_bill_recepit")
public class PharmacyBillRecepit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pbrId", unique = true, nullable = true)
    private long pbrId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrPmId")
    private MstPaymentMode pbrPmId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrBankId")
    private MstBank pbrBankId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrPsId")
    private TinvPharmacySale pbrPsId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<TinvPharmacySale> pbrPsIds;

    @JsonInclude(NON_NULL)
    private String transcationId;

    @JsonInclude(NON_NULL)
    private double cash;

    @JsonInclude(NON_NULL)
    private double returnAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrUnitId")
    private MstUnit pbrUnitId;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public MstUnit getPbrUnitId() {
        return pbrUnitId;
    }

    public void setPbrUnitId(MstUnit pbrUnitId) {
        this.pbrUnitId = pbrUnitId;
    }

    public long getPbrId() {
        return pbrId;
    }

    public void setPbrId(long pbrId) {
        this.pbrId = pbrId;
    }

    public MstPaymentMode getPbrPmId() {
        return pbrPmId;
    }

    public void setPbrPmId(MstPaymentMode pbrPmId) {
        this.pbrPmId = pbrPmId;
    }

    public MstBank getPbrBankId() {
        return pbrBankId;
    }

    public void setPbrBankId(MstBank pbrBankId) {
        this.pbrBankId = pbrBankId;
    }

    public String getTranscationId() {
        return transcationId;
    }

    public void setTranscationId(String transcationId) {
        this.transcationId = transcationId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public TinvPharmacySale getPbrPsId() {
        return pbrPsId;
    }

    public void setPbrPsId(TinvPharmacySale pbrPsId) {
        this.pbrPsId = pbrPsId;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public List<TinvPharmacySale> getPbrPsIds() {
        return pbrPsIds;
    }

    public void setPbrPsIds(List<TinvPharmacySale> pbrPsIds) {
        this.pbrPsIds = pbrPsIds;
    }
}
