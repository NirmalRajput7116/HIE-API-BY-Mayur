package com.cellbeans.hspa.tnstdrugadmin;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "tnst_drug_admin")
public class TnstDrugAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daId", unique = true, nullable = true)
    private long daId;

    @JsonInclude(NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @Column(name = "daDate")
    private Date daDate;

    @JsonInclude(NON_NULL)
    @Column(name = "daPersianDate")
    private String daPersianDate;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.TIME)
    @Column(name = "daTime")
    private Date daTime;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "daRemarks")
    private String daRemarks;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "daAdmissionId")
    private TrnAdmission daAdmissionId;

    @JsonInclude(NON_NULL)
    @Column(name = "daQuantity")
    private String daQuantity;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "daInvItemId")
    private InvItem daInvItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "daStaffId")
    private MstStaff daStaffId;

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

    public long getDaId() {
        return daId;
    }

    public void setDaId(long daId) {
        this.daId = daId;
    }

    public Date getDaDate() {
        return daDate;
    }

    public void setDaDate(Date daDate) {
        this.daDate = daDate;
    }

    public Date getDaTime() {
        return daTime;
    }

    public void setDaTime(Date daTime) {
        this.daTime = daTime;
    }

    public String getDaRemarks() {
        return daRemarks;
    }

    public void setDaRemarks(String daRemarks) {
        this.daRemarks = daRemarks;
    }

    public TrnAdmission getDaAdmissionId() {
        return daAdmissionId;
    }

    public void setDaAdmissionId(TrnAdmission daAdmissionId) {
        this.daAdmissionId = daAdmissionId;
    }

    public String getDaQuantity() {
        return daQuantity;
    }

    public void setDaQuantity(String daQuantity) {
        this.daQuantity = daQuantity;
    }

    public InvItem getDaInvItemId() {
        return daInvItemId;
    }

    public void setDaInvItemId(InvItem daInvItemId) {
        this.daInvItemId = daInvItemId;
    }

    public MstStaff getDaStaffId() {
        return daStaffId;
    }

    public void setDaStaffId(MstStaff daStaffId) {
        this.daStaffId = daStaffId;
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

    public String getDaPersianDate() {
        return daPersianDate;
    }

    public void setDaPersianDate(String daPersianDate) {
        this.daPersianDate = daPersianDate;
    }

}
