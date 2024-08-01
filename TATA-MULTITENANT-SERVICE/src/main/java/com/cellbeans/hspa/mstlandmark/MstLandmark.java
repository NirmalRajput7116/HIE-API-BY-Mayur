package com.cellbeans.hspa.mstlandmark;

import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstdistrict.MstDistrict;
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
@Table(name = "mst_landmark")
public class MstLandmark implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "landmarkId", unique = true, nullable = true)
    private long landmarkId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "landmarkCityId")
    private MstCity landmarkCityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "landmarkDistrictId")
    private MstDistrict landmarkDistrictId;

   /* @ManyToOne
    @JoinColumn(name="cityTalukaId")
    private MstTaluka cityTalukaId;*/

    @JsonInclude(NON_NULL)
    @Column(name = "landmarkName")
    private String landmarkName;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public long getLandmarkId() {
        return landmarkId;
    }

    public void setLandmarkId(long landmarkId) {
        this.landmarkId = landmarkId;
    }

    public MstCity getLandmarkCityId() {
        return landmarkCityId;
    }

    public void setLandmarkCityId(MstCity landmarkCityId) {
        this.landmarkCityId = landmarkCityId;
    }

    public String getLandmarkName() {
        return landmarkName;
    }

    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
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

    public MstDistrict getLandmarkDistrictId() {
        return landmarkDistrictId;
    }

    public void setLandmarkDistrictId(MstDistrict landmarkDistrictId) {
        this.landmarkDistrictId = landmarkDistrictId;
    }
}
