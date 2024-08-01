package com.cellbeans.hspa.mbillipdbillcharges;

import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_ipd_bill_charges")
public class MbillIPDBillCharges implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipdbcId", unique = true, nullable = true)
    private long ipdbcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipdbcBedId")
    private MipdBed ipdbcBedId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipdbcAdminsionId")
    private TrnAdmission ipdbcAdminsionId;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ipdbcBedAllocationDateTime")
    private Date ipdbcBedAllocationDateTime;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ipdbcBedReleaseDateTime")
    private Date ipdbcBedReleaseDateTime;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ipdbcBedTransferDateTime")
    private Date ipdbcBedTransferDateTime;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ipdbcBedRoutineStartTime")
    private Date ipdbcBedRoutineStartTime;

    @JsonInclude(NON_NULL)
    @Column(name = "ipdbcChargesType")
    private String ipdbcChargesType;

    @JsonInclude(NON_NULL)
    @Column(name = "ipdbcBedBillRoutingFact")
    private String ipdbcBedBillRoutingFact;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    //    @JsonInclude(NON_NULL)
    @JsonInclude(NON_NULL)
    @Column(name = "isPulled", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isPulled = false;

    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    public Boolean getIsPulled() {
        return isPulled;
    }

    public void setIsPulled(Boolean isPulled) {
        this.isPulled = isPulled;
    }

    public long getIpdbcId() {
        return ipdbcId;
    }

    public void setIpdbcId(long ipdbcId) {
        this.ipdbcId = ipdbcId;
    }

    public MipdBed getIpdbcBedId() {
        return ipdbcBedId;
    }

    public void setIpdbcBedId(MipdBed ipdbcBedId) {
        this.ipdbcBedId = ipdbcBedId;
    }

    public TrnAdmission getIpdbcAdminsionId() {
        return ipdbcAdminsionId;
    }

    public void setIpdbcAdminsionId(TrnAdmission ipdbcAdminsionId) {
        this.ipdbcAdminsionId = ipdbcAdminsionId;
    }

    public Date getIpdbcBedAllocationDateTime() {
        return ipdbcBedAllocationDateTime;
    }

    public void setIpdbcBedAllocationDateTime(Date ipdbcBedAllocationDateTime) {
        this.ipdbcBedAllocationDateTime = ipdbcBedAllocationDateTime;
    }

    public Date getIpdbcBedReleaseDateTime() {
        return ipdbcBedReleaseDateTime;
    }

    public void setIpdbcBedReleaseDateTime(Date ipdbcBedReleaseDateTime) {
        this.ipdbcBedReleaseDateTime = ipdbcBedReleaseDateTime;
    }

    public Date getIpdbcBedTransferDateTime() {
        return ipdbcBedTransferDateTime;
    }

    public void setIpdbcBedTransferDateTime(Date ipdbcBedTransferDateTime) {
        this.ipdbcBedTransferDateTime = ipdbcBedTransferDateTime;
    }

    public Date getIpdbcBedRoutineStartTime() {
        return ipdbcBedRoutineStartTime;
    }

    public void setIpdbcBedRoutineStartTime(Date ipdbcBedRoutineStartTime) {
        this.ipdbcBedRoutineStartTime = ipdbcBedRoutineStartTime;
    }

    public String getIpdbcChargesType() {
        return ipdbcChargesType;
    }

    public void setIpdbcChargesType(String ipdbcChargesType) {
        this.ipdbcChargesType = ipdbcChargesType;
    }

    public String getIpdbcBedBillRoutingFact() {
        return ipdbcBedBillRoutingFact;
    }

    public void setIpdbcBedBillRoutingFact(String ipdbcBedBillRoutingFact) {
        this.ipdbcBedBillRoutingFact = ipdbcBedBillRoutingFact;
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
