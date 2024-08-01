package com.cellbeans.hspa.trnbedtransfer;

import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mstclass.MstClass;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_bed_transfer")
public class TrnBedTransfer {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transferId", unique = true, nullable = true)
    private long transferId;

    @JsonInclude(NON_NULL)
    @Column(name = "transferRemark")
    private String transferRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "transferAdmissionId")
    private TrnAdmission transferAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "transferClassId")
    private MstClass transferClassId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MipdBed> transferBedId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "transferpatientbedId")
    private MipdBed transferpatientbedId;

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

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public String getTransferRemark() {
        return transferRemark;
    }

    public void setTransferRemark(String transferRemark) {
        this.transferRemark = transferRemark;
    }

    public TrnAdmission getTransferAdmissionId() {
        return transferAdmissionId;
    }

    public void setTransferAdmissionId(TrnAdmission transferAdmissionId) {
        this.transferAdmissionId = transferAdmissionId;
    }

    public MstClass getTransferClassId() {
        return transferClassId;
    }

    public void setTransferClassId(MstClass transferClassId) {
        this.transferClassId = transferClassId;
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

    public List<MipdBed> getTransferBedId() {
        return transferBedId;
    }

    public void setTransferBedId(List<MipdBed> transferBedId) {
        this.transferBedId = transferBedId;
    }

    public MipdBed getTransferpatientbedId() {
        return transferpatientbedId;
    }

    public void setTransferpatientbedId(MipdBed transferpatientbedId) {
        this.transferpatientbedId = transferpatientbedId;
    }

}
