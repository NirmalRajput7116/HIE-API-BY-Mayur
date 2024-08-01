package com.cellbeans.hspa.tbillserviceradiology;

import com.cellbeans.hspa.mbillipdcharges.IPDChargesService;
import com.cellbeans.hspa.tbillbillservice.TbillBillService;
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
@Table(name = "tbill_service_radiology")
public class TbillServiceRadiology implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bsrId", unique = true, nullable = true)
    private long bsrId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bsrBsId")
    private TbillBillService bsrBsId;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrIsServiceDone", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIsServiceDone = false;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrIsPaid", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIsPaid = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrIsModalityRequest", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIsModalityRequest = false;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrIsReported", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIsReported = false;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrIsCancel", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIsCancel = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ipdBill", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIpdBill;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrReportPath")
    private String bsrReportPath;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrReportId", columnDefinition = "int default 0")
    private int bsrReportId;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrCancelRemark")
    private String bsrCancelRemark;

    @JsonIgnore
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

    @JsonInclude(NON_NULL)
    @Column(name = "bsrIsCall", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIsCall = false;

    @JsonInclude(NON_NULL)
    @Column(name = "bsrIsScanCompleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsrIsScanCompleted = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bsrCsId")
    private IPDChargesService bsrCsId;
    @Transient
    private long count;

    public long getBsrId() {
        return bsrId;
    }

    public void setBsrId(int bsrId) {
        this.bsrId = bsrId;
    }

    public boolean getBsrIsServiceDone() {
        return bsrIsServiceDone;
    }

    public void setBsrIsServiceDone(boolean bsrIsServiceDone) {
        this.bsrIsServiceDone = bsrIsServiceDone;
    }

    public boolean getBsrIsPaid() {
        return bsrIsPaid;
    }

    public void setBsrIsPaid(boolean bsrIsPaid) {
        this.bsrIsPaid = bsrIsPaid;
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

    public Boolean getBsrIsCall() {
        return bsrIsCall;
    }

    public void setBsrIsCall(Boolean bsrIsCall) {
        this.bsrIsCall = bsrIsCall;
    }

    public Boolean getBsrIsScanCompleted() {
        return bsrIsScanCompleted;
    }

    public void setBsrIsScanCompleted(Boolean bsrIsScanCompleted) {
        this.bsrIsScanCompleted = bsrIsScanCompleted;
    }

    public boolean getBsrIsModalityRequest() {
        return bsrIsModalityRequest;
    }

    public void setBsrIsModalityRequest(boolean bsrIsModalityRequest) {
        this.bsrIsModalityRequest = bsrIsModalityRequest;
    }

    public boolean getBsrIsReported() {
        return bsrIsReported;
    }

    public void setBsrIsReported(boolean bsrIsReported) {
        this.bsrIsReported = bsrIsReported;
    }

    public String getBsrReportPath() {
        return bsrReportPath;
    }

    public void setBsrReportPath(String bsrReportPath) {
        this.bsrReportPath = bsrReportPath;
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

    public TbillBillService getBsrBsId() {
        return bsrBsId;
    }

    public void setBsrBsId(TbillBillService bsrBsId) {
        this.bsrBsId = bsrBsId;
    }

    public Boolean getBsrIsCancel() {
        return bsrIsCancel;
    }

    public void setBsrIsCancel(Boolean bsrIsCancel) {
        this.bsrIsCancel = bsrIsCancel;
    }

    public int getBsrReportId() {
        return bsrReportId;
    }

    public void setBsrReportId(int bsrReportId) {
        this.bsrReportId = bsrReportId;
    }

    public String getBsrCancelRemark() {
        return bsrCancelRemark;
    }

    public void setBsrCancelRemark(String bsrCancelRemark) {
        this.bsrCancelRemark = bsrCancelRemark;
    }

    public Boolean getBsrIpdBill() {
        return bsrIpdBill;
    }

    public void setBsrIpdBill(Boolean bsrIpdBill) {
        this.bsrIpdBill = bsrIpdBill;
    }

    public IPDChargesService getBsrCsId() {
        return bsrCsId;
    }

    public void setBsrCsId(IPDChargesService bsrCsId) {
        this.bsrCsId = bsrCsId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}