package com.cellbeans.hspa.mstdoctorroaster;

import com.cellbeans.hspa.mstdoctormaster.MstDoctorMaster;
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
@Table(name = "mst_doctor_roaster")
public class MstDoctorRoaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drId", unique = true, nullable = true)
    private long drId;

    @JsonInclude(NON_NULL)
    @Column(name = "drInTime")
    private String drInTime;

    @JsonInclude(NON_NULL)
    @Column(name = "drOutTime")
    private String drOutTime;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "drDate")
    private Date drDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "drDmId")
    private MstDoctorMaster drDmId;

    @JsonInclude(NON_NULL)
    @Column(name = "drIsPresent", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean drIsPresent = false;

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

    public long getDrId() {
        return drId;
    }

    public void setDrId(long drId) {
        this.drId = drId;
    }

    public String getDrInTime() {
        return drInTime;
    }

    public void setDrInTime(String drInTime) {
        this.drInTime = drInTime;
    }

    public String getDrOutTime() {
        return drOutTime;
    }

    public void setDrOutTime(String drOutTime) {
        this.drOutTime = drOutTime;
    }

    public Date getDrDate() {
        return drDate;
    }

    public void setDrDate(Date drDate) {
        this.drDate = drDate;
    }

    public MstDoctorMaster getDrDmId() {
        return drDmId;
    }

    public void setDrDmId(MstDoctorMaster drDmId) {
        this.drDmId = drDmId;
    }

    public Boolean getDrIsPresent() {
        return drIsPresent;
    }

    public void setDrIsPresent(Boolean drIPresent) {
        this.drIsPresent = drIsPresent;
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
