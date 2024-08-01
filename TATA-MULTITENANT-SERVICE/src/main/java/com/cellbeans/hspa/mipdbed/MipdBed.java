package com.cellbeans.hspa.mipdbed;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mipdbedamenity.MipdBedAmenity;
import com.cellbeans.hspa.mipdroom.MipdRoom;
import com.cellbeans.hspa.mipdward.MipdWard;
import com.cellbeans.hspa.mstclass.MstClass;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mipd_bed")
public class MipdBed implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bedId", unique = true, nullable = true)
    private long bedId;

    @JsonInclude(NON_NULL)
    @Column(name = "bedName")
    private String bedName;

    @JsonInclude(NON_NULL)
    @Column(name = "bedCode")
    private String bedCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bedWardId")
    private MipdWard bedWardId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bedRoomId")
    private MipdRoom bedRoomId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bedBcId")
    private MstClass bedBcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bedUnitId")
    private MstUnit bedUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "bedIsNoncensus", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bedIsNoncensus = false;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MipdBedAmenity> mipdBedAmenityList = new ArrayList<>();

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MbillService> mipdBedServiceList = new ArrayList<>();

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "emegency", columnDefinition = "binary(1)")
    private Boolean emegency = false;

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

    public List<MipdBedAmenity> getMipdBedAmenityList() {
        return mipdBedAmenityList;
    }

    public void setMipdBedAmenityList(List<MipdBedAmenity> mipdBedAmenityList) {
        this.mipdBedAmenityList = mipdBedAmenityList;
    }

    public long getBedId() {
        return bedId;
    }

    public void setBedId(long bedId) {
        this.bedId = bedId;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public String getBedCode() {
        return bedCode;
    }

    public void setBedCode(String bedCode) {
        this.bedCode = bedCode;
    }

    public MipdWard getBedWardId() {
        return bedWardId;
    }

    public void setBedWardId(MipdWard bedWardId) {
        this.bedWardId = bedWardId;
    }

    public MipdRoom getBedRoomId() {
        return bedRoomId;
    }

    public void setBedRoomId(MipdRoom bedRoomId) {
        this.bedRoomId = bedRoomId;
    }

    public MstClass getBedBcId() {
        return bedBcId;
    }

    public void setBedBcId(MstClass bedBcId) {
        this.bedBcId = bedBcId;
    }

    public MstUnit getBedUnitId() {
        return bedUnitId;
    }

    public void setBedUnitId(MstUnit bedUnitId) {
        this.bedUnitId = bedUnitId;
    }

    public boolean getBedIsNoncensus() {
        return bedIsNoncensus;
    }

    public void setBedIsNoncensus(boolean bedIsNoncensus) {
        this.bedIsNoncensus = bedIsNoncensus;
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

    public List<MbillService> getMipdBedServiceList() {
        return mipdBedServiceList;
    }

    public void setMipdBedServiceList(List<MbillService> mipdBedServiceList) {
        this.mipdBedServiceList = mipdBedServiceList;
    }

    public Boolean getEmegency() {
        return emegency;
    }

    public void setEmegency(Boolean emegency) {
        this.emegency = emegency;
    }

}