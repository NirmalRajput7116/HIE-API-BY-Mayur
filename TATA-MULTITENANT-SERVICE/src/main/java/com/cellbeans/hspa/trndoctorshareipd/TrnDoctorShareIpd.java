package com.cellbeans.hspa.trndoctorshareipd;

import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_doctor_share_ipd")
public class TrnDoctorShareIpd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsiId", unique = true, nullable = true)
    private long dsiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsiBillId")
    private TBillBill dsiBillId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsiConsDocId1")
    private MstStaff dsiConsDocId1;

    @JsonInclude(NON_NULL)
    @Column(name = "dsiDocShareAmt1")
    private double dsiDocShareAmt1;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsiConsDocId2")
    private MstStaff dsiConsDocId2;

    @JsonInclude(NON_NULL)
    @Column(name = "dsiDocShareAmt2")
    private double dsiDocShareAmt2;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsiConsDocId3")
    private MstStaff dsiConsDocId3;

    @JsonInclude(NON_NULL)
    @Column(name = "dsiDocShareAmt3")
    private double dsiDocShareAmt3;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsiReId1")
    private MstReferringEntity dsiReId1;

    @JsonInclude(NON_NULL)
    @Column(name = "dsiDocShareAmt4")
    private double dsiDocShareAmt4;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsiReId2")
    private MstReferringEntity dsiReId2;

    @JsonInclude(NON_NULL)
    @Column(name = "dsiDocShareAmt5")
    private double dsiDocShareAmt5;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getDsiId() {
        return dsiId;
    }

    public void setDsiId(long dsiId) {
        this.dsiId = dsiId;
    }

    public TBillBill getDsiBillId() {
        return dsiBillId;
    }

    public void setDsiBillId(TBillBill dsiBillId) {
        this.dsiBillId = dsiBillId;
    }

    public MstStaff getDsiConsDocId1() {
        return dsiConsDocId1;
    }

    public void setDsiConsDocId1(MstStaff dsiConsDocId1) {
        this.dsiConsDocId1 = dsiConsDocId1;
    }

    public double getDsiDocShareAmt1() {
        return dsiDocShareAmt1;
    }

    public void setDsiDocShareAmt1(double dsiDocShareAmt1) {
        this.dsiDocShareAmt1 = dsiDocShareAmt1;
    }

    public MstStaff getDsiConsDocId2() {
        return dsiConsDocId2;
    }

    public void setDsiConsDocId2(MstStaff dsiConsDocId2) {
        this.dsiConsDocId2 = dsiConsDocId2;
    }

    public double getDsiDocShareAmt2() {
        return dsiDocShareAmt2;
    }

    public void setDsiDocShareAmt2(double dsiDocShareAmt2) {
        this.dsiDocShareAmt2 = dsiDocShareAmt2;
    }

    public MstStaff getDsiConsDocId3() {
        return dsiConsDocId3;
    }

    public void setDsiConsDocId3(MstStaff dsiConsDocId3) {
        this.dsiConsDocId3 = dsiConsDocId3;
    }

    public double getDsiDocShareAmt3() {
        return dsiDocShareAmt3;
    }

    public void setDsiDocShareAmt3(double dsiDocShareAmt3) {
        this.dsiDocShareAmt3 = dsiDocShareAmt3;
    }

    public MstReferringEntity getDsiReId1() {
        return dsiReId1;
    }

    public void setDsiReId1(MstReferringEntity dsiReId1) {
        this.dsiReId1 = dsiReId1;
    }

    public double getDsiDocShareAmt4() {
        return dsiDocShareAmt4;
    }

    public void setDsiDocShareAmt4(double dsiDocShareAmt4) {
        this.dsiDocShareAmt4 = dsiDocShareAmt4;
    }

    public MstReferringEntity getDsiReId2() {
        return dsiReId2;
    }

    public void setDsiReId2(MstReferringEntity dsiReId2) {
        this.dsiReId2 = dsiReId2;
    }

    public double getDsiDocShareAmt5() {
        return dsiDocShareAmt5;
    }

    public void setDsiDocShareAmt5(double dsiDocShareAmt5) {
        this.dsiDocShareAmt5 = dsiDocShareAmt5;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
