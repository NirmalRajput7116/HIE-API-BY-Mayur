package com.cellbeans.hspa.nstnursingstationdefination;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mipdfloor.MipdFloor;
import com.cellbeans.hspa.mipdward.MipdWard;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nst_nursing_station_defination")
public class NstNursingStationDefination implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nsdId", unique = true, nullable = true)
    private long nsdId;

    @JsonInclude(NON_NULL)
    @Column(name = "nsdName")
    private String nsdName;

    @JsonInclude(NON_NULL)
    @Column(name = "nsdCode")
    private String nsdCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "nsdUnitId")
    private MstUnit nsdUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "nsdStoreId")
    private InvStore nsdStoreId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "nsdFloorId")
    private MipdFloor nsdFloorId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "nsdWardId")
    private MipdWard nsdWardId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinTable(name = "trn_nsd_bed")
    private List<MipdBed> nstBedId;

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

    public long getNsdId() {
        return nsdId;
    }

    public void setNsdId(long nsdId) {
        this.nsdId = nsdId;
    }

    public String getNsdName() {
        return nsdName;
    }

    public void setNsdName(String nsdName) {
        this.nsdName = nsdName;
    }

    public String getNsdCode() {
        return nsdCode;
    }

    public void setNsdCode(String nsdCode) {
        this.nsdCode = nsdCode;
    }

    public MstUnit getNsdUnitId() {
        return nsdUnitId;
    }

    public void setNsdUnitId(MstUnit nsdUnitId) {
        this.nsdUnitId = nsdUnitId;
    }

    public MipdFloor getNsdFloorId() {
        return nsdFloorId;
    }

    public void setNsdFloorId(MipdFloor nsdFloorId) {
        this.nsdFloorId = nsdFloorId;
    }

    public MipdWard getNsdWardId() {
        return nsdWardId;
    }

    public void setNsdWardId(MipdWard nsdWardId) {
        this.nsdWardId = nsdWardId;
    }

    public List<MipdBed> getNstBedId() {
        return nstBedId;
    }

    public void setNstBedId(List<MipdBed> nstBedId) {
        this.nstBedId = nstBedId;
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

    @Override
    public String toString() {
        return "NstNursingStationDefination{" + "nsdId=" + nsdId + ", nsdName='" + nsdName + '\'' + ", nsdCode='" + nsdCode + '\'' + ", nsdUnitId=" + nsdUnitId + ", nsdFloorId=" + nsdFloorId + ", nsdWardId=" + nsdWardId + ", nstBedId=" + nstBedId + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

    public InvStore getNsdStoreId() {
        return nsdStoreId;
    }

    public void setNsdStoreId(InvStore nsdStoreId) {
        this.nsdStoreId = nsdStoreId;
    }

}
