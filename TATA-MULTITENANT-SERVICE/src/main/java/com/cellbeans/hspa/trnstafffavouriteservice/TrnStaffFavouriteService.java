package com.cellbeans.hspa.trnstafffavouriteservice;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_staff_favourite_service")
public class TrnStaffFavouriteService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tsfsId", unique = true, nullable = true)
    private long tsfsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tsfsStaffId")
    private MstStaff tsfsStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tsfsServiceId")
    private MbillService tsfsServiceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tsfsUnitId")
    private MstUnit tsfsUnitId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    public long getTsfsId() {
        return tsfsId;
    }

    public void setTsfsId(long tsfsId) {
        this.tsfsId = tsfsId;
    }

    public MstStaff getTsfsStaffId() {
        return tsfsStaffId;
    }

    public void setTsfsStaffId(MstStaff tsfsStaffId) {
        this.tsfsStaffId = tsfsStaffId;
    }

    public MbillService getTsfsServiceId() {
        return tsfsServiceId;
    }

    public void setTsfsServiceId(MbillService tsfsServiceId) {
        this.tsfsServiceId = tsfsServiceId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public MstUnit getTsfsUnitId() {
        return tsfsUnitId;
    }

    public void setTsfsUnitId(MstUnit tsfsUnitId) {
        this.tsfsUnitId = tsfsUnitId;
    }

}
