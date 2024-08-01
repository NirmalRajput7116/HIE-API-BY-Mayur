package com.cellbeans.hspa.tbillrecieptbillservice;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.tbillreciept.TbillReciept;
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
@Table(name = "tbill_reciept_bill_service")
public class TbillRecieptBillService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rbsId", unique = true, nullable = true)
    private long rbsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rbsServiceId")
    private MbillService rbsServiceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rbsTbrId")
    private TbillReciept rbsTbrId;

    @JsonInclude(NON_NULL)
    @Column(name = "rbsAmount")
    private double rbsAmount;

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

    public long getRbsId() {
        return rbsId;
    }

    public void setRbsId(int rbsId) {
        this.rbsId = rbsId;
    }

    public MbillService getRbsServiceId() {
        return rbsServiceId;
    }

    public void setRbsServiceId(MbillService rbsServiceId) {
        this.rbsServiceId = rbsServiceId;
    }

    public TbillReciept getRbsTbrId() {
        return rbsTbrId;
    }

    public void setRbsTbrId(TbillReciept rbsTbrId) {
        this.rbsTbrId = rbsTbrId;
    }

    public double getRbsAmount() {
        return rbsAmount;
    }

    public void setRbsAmount(double rbsAmount) {
        this.rbsAmount = rbsAmount;
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

}            
