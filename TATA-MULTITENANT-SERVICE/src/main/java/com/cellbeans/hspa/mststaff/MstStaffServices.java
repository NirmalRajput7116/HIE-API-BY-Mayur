package com.cellbeans.hspa.mststaff;

import com.cellbeans.hspa.mbillservice.MbillService;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_staff_services")
public class MstStaffServices {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssId", unique = true, nullable = true)
    private long ssId;

    @ManyToOne
    @JoinColumn(name = "ssServiceId")
    private MbillService ssServiceId;

    @Column(name = "ssBaseRate", columnDefinition = "decimal default 0", nullable = true)
    private double ssBaseRate;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    public long getSsId() {
        return ssId;
    }

    public void setSsId(long ssId) {
        this.ssId = ssId;
    }

    public MbillService getSsServiceId() {
        return ssServiceId;
    }

    public void setSsServiceId(MbillService ssServiceId) {
        this.ssServiceId = ssServiceId;
    }

    public double getSsBaseRate() {
        return ssBaseRate;
    }

    public void setSsBaseRate(double ssBaseRate) {
        this.ssBaseRate = ssBaseRate;
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

}
