package com.cellbeans.hspa.mstcity;

import com.cellbeans.hspa.mstdistrict.MstDistrict;
import com.cellbeans.hspa.mststate.MstState;
import com.cellbeans.hspa.msttaluka.MstTaluka;
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
@Table(name = "mst_city")
public class MstCity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cityId", unique = true, nullable = true)
    private long cityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cityStateId")
    private MstState cityStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cityDistrictId")
    private MstDistrict cityDistrictId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cityTalukaId")
    private MstTaluka cityTalukaId;

    @JsonInclude(NON_NULL)
    @Column(name = "cityName")
    private String cityName;

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

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public MstState getCityStateId() {
        return cityStateId;
    }

    public void setCityStateId(MstState cityStateId) {
        this.cityStateId = cityStateId;
    }

    public MstDistrict getCityDistrictId() {
        return cityDistrictId;
    }

    public void setCityDistrictId(MstDistrict cityDistrictId) {
        this.cityDistrictId = cityDistrictId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public MstTaluka getCityTalukaId() {
        return cityTalukaId;
    }

    public void setCityTalukaId(MstTaluka cityTalukaId) {
        this.cityTalukaId = cityTalukaId;
    }
}
