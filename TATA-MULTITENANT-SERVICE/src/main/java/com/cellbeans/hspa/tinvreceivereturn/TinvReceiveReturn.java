package com.cellbeans.hspa.tinvreceivereturn;

import com.cellbeans.hspa.invstore.InvStore;
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
@Table(name = "tinv_receive_return")
public class TinvReceiveReturn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rrId", unique = true, nullable = true)
    private long rrId;

    @JsonInclude(NON_NULL)
    @Column(name = "rrNumber")
    private String rrNumber;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rrFromStore")
    private InvStore rrFromStore;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rrToStore")
    private InvStore rrToStore;

    @JsonInclude(NON_NULL)
    @Column(name = "rrNumberOfItems")
    private int rrNumberOfItems;

    @JsonInclude(NON_NULL)
    @Column(name = "rrTotalAmount")
    private double rrTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "rrTotalMrp")
    private double rrTotalMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "rrRemark")
    private String rrRemark;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = false;

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

    public long getRrId() {
        return rrId;
    }

    public void setRrId(int rrId) {
        this.rrId = rrId;
    }

    public String getRrNumber() {
        return rrNumber;
    }

    public void setRrNumber(String rrNumber) {
        this.rrNumber = rrNumber;
    }

    public InvStore getRrFromStore() {
        return rrFromStore;
    }

    public void setRrFromStore(InvStore rrFromStore) {
        this.rrFromStore = rrFromStore;
    }

    public InvStore getRrToStore() {
        return rrToStore;
    }

    public void setRrToStore(InvStore rrToStore) {
        this.rrToStore = rrToStore;
    }

    public int getRrNumberOfItems() {
        return rrNumberOfItems;
    }

    public void setRrNumberOfItems(int rrNumberOfItems) {
        this.rrNumberOfItems = rrNumberOfItems;
    }

    public double getRrTotalAmount() {
        return rrTotalAmount;
    }

    public void setRrTotalAmount(double rrTotalAmount) {
        this.rrTotalAmount = rrTotalAmount;
    }

    public double getRrTotalMrp() {
        return rrTotalMrp;
    }

    public void setRrTotalMrp(double rrTotalMrp) {
        this.rrTotalMrp = rrTotalMrp;
    }

    public String getRrRemark() {
        return rrRemark;
    }

    public void setRrRemark(String rrRemark) {
        this.rrRemark = rrRemark;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
