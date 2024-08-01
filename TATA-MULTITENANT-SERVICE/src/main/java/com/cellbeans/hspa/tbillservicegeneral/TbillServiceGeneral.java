package com.cellbeans.hspa.tbillservicegeneral;

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
@Table(name = "tbill_service_general")
public class TbillServiceGeneral implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bsgId", unique = true, nullable = true)
    private long bsgId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bsg_bs_id")
    private TbillBillService bsgBsId;

    @JsonInclude(NON_NULL)
    @Column(name = "bsgIsServiceDone", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsgIsServiceDone = false;

    @JsonInclude(NON_NULL)
    @Column(name = "bsgIsPaid", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsgIsPaid = false;

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

    public long getBsgId() {
        return bsgId;
    }

    public void setBsgId(int bsgId) {
        this.bsgId = bsgId;
    }

    public boolean getBsgIsServiceDone() {
        return bsgIsServiceDone;
    }

    public void setBsgIsServiceDone(boolean bsgIsServiceDone) {
        this.bsgIsServiceDone = bsgIsServiceDone;
    }

    public boolean getBsgIsPaid() {
        return bsgIsPaid;
    }

    public void setBsgIsPaid(boolean bsgIsPaid) {
        this.bsgIsPaid = bsgIsPaid;
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

    public TbillBillService getBsgBsId() {
        return bsgBsId;
    }

    public void setBsgBsId(TbillBillService bsgBsId) {
        this.bsgBsId = bsgBsId;
    }

}