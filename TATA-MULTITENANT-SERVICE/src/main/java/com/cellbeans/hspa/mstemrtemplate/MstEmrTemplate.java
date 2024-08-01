package com.cellbeans.hspa.mstemrtemplate;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
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
@Table(name = "mst_emr_template")
public class MstEmrTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etId", unique = true, nullable = true)
    private long etId;

    @JsonInclude(NON_NULL)
    @Column(name = "etName")
    private String etName;

    @JsonInclude(NON_NULL)
    @Column(name = "etFollowUpDay")
    private int etFollowUpDay;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "etDoctorAdvice")
    private String etDoctorAdvice;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etUnitId")
    private MstUnit etUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etStaffId")
    private MstStaff etStaffId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonInclude(NON_NULL)
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonInclude(NON_NULL)
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonInclude(NON_NULL)
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getEtId() {
        return etId;
    }

    public void setEtId(long etId) {
        this.etId = etId;
    }

    public String getEtName() {
        return etName;
    }

    public void setEtName(String etName) {
        this.etName = etName;
    }

    public int getEtFollowUpDay() {
        return etFollowUpDay;
    }

    public void setEtFollowUpDay(int etFollowUpDay) {
        this.etFollowUpDay = etFollowUpDay;
    }

    public String getEtDoctorAdvice() {
        return etDoctorAdvice;
    }

    public void setEtDoctorAdvice(String etDoctorAdvice) {
        this.etDoctorAdvice = etDoctorAdvice;
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

    public MstUnit getEtUnitId() {
        return etUnitId;
    }

    public void setEtUnitId(MstUnit etUnitId) {
        this.etUnitId = etUnitId;
    }

    public MstStaff getEtStaffId() {
        return etStaffId;
    }

    public void setEtStaffId(MstStaff etStaffId) {
        this.etStaffId = etStaffId;
    }
}
