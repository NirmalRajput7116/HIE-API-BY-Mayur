
package com.cellbeans.hspa.nmstcampvisit;

import java.io.Serializable;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstdistrict.MstDistrict;
import com.cellbeans.hspa.mstlandmark.MstLandmark;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import com.cellbeans.hspa.nmstcamp.NmstCamp;
import com.cellbeans.hspa.nmstbus.NmstBus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nmst_camp_visit")
public class NmstCampVisit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cvId", unique = true, nullable = true)
    private long cvId;

    @ManyToOne
    @JoinColumn(name = "cvCampId")
    private NmstCamp cvCampId;

    @ManyToOne
    @JoinColumn(name = "cvBusId")
    private NmstBus cvBusId;

    @Column(name = "cvName")
    private String cvName;

    @ManyToOne
    @JoinColumn(name = "cvStoreId")
    private InvStore cvStoreId;

    @ManyToOne
    @JoinColumn(name = "cvDistrictId")
    private MstDistrict cvDistrictId;
    @ManyToOne
    @JoinColumn(name = "cvCityId")
    private MstCity cvCityId;
    @ManyToOne
    @JoinColumn(name = "cvLandmarkId")
    private MstLandmark cvLandmarkId;
    @ManyToMany
    @JoinColumn(name = "cvStaffId")
    private List<MstStaff> cvStaffId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cvFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cvToDate;


    @JsonInclude(NON_NULL)
    @Column(name = "cvStartTime")
    private String cvStartTime;

    @JsonInclude(NON_NULL)
    @Column(name = "cvEndTime")
    private String cvEndTime;


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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getCvId() {
        return this.cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }

    public NmstCamp getCvCampId() {
        return this.cvCampId;
    }

    public void setCvCampId(NmstCamp cvCampId) {
        this.cvCampId = cvCampId;
    }

    public NmstBus getCvBusId() {
        return this.cvBusId;
    }

    public void setCvBusId(NmstBus cvBusId) {
        this.cvBusId = cvBusId;
    }

    public String getCvName() {
        return this.cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public InvStore getCvStoreId() {
        return cvStoreId;
    }

    public void setCvStoreId(InvStore cvStoreId) {
        this.cvStoreId = cvStoreId;
    }

    public MstDistrict getCvDistrictId() {
        return cvDistrictId;
    }

    public void setCvDistrictId(MstDistrict cvDistrictId) {
        this.cvDistrictId = cvDistrictId;
    }

    public MstCity getCvCityId() {
        return cvCityId;
    }

    public void setCvCityId(MstCity cvCityId) {
        this.cvCityId = cvCityId;
    }

    public MstLandmark getCvLandmarkId() {
        return cvLandmarkId;
    }

    public void setCvLandmarkId(MstLandmark cvLandmarkId) {
        this.cvLandmarkId = cvLandmarkId;
    }

    public List<MstStaff> getCvStaffId() {
        return cvStaffId;
    }

    public void setCvStaffId(List<MstStaff> cvStaffId) {
        this.cvStaffId = cvStaffId;
    }

    public Date getCvFromDate() {
        return cvFromDate;
    }

    public void setCvFromDate(Date cvFromDate) {
        this.cvFromDate = cvFromDate;
    }

    public Date getCvToDate() {
        return cvToDate;
    }

    public void setCvToDate(Date cvToDate) {
        this.cvToDate = cvToDate;
    }

    public String getCvStartTime() {        return cvStartTime;    }

    public void setCvStartTime(String cvStartTime) {        this.cvStartTime = cvStartTime;    }

    public String getCvEndTime() {        return cvEndTime;    }

    public void setCvEndTime(String cvEndTime) {        this.cvEndTime = cvEndTime;    }
}
