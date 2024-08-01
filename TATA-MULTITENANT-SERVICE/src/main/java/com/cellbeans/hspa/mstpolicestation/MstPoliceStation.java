package com.cellbeans.hspa.mstpolicestation;

import com.cellbeans.hspa.mstcity.MstCity;
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
@Table(name = "mst_police_station")
public class MstPoliceStation implements Serializable {
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "policeStationCityId")
    MstCity policeStationCityId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policeStationId", unique = true, nullable = true)
    private long policeStationId;
    @JsonInclude(NON_NULL)
    @Column(name = "policeStationName")
    private String policeStationName;
    @JsonInclude(NON_NULL)
    @Column(name = "policeStationCode")
    private String policeStationCode;
    @JsonInclude(NON_NULL)
    @Column(name = "policeStationContact")
    private String policeStationContact;
    @JsonInclude(NON_NULL)
    @Column(name = "address")
    private String address;

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

    public MstCity getPoliceStationCityId() {
        return policeStationCityId;
    }

    public void setPoliceStationCityId(MstCity policeStationCityId) {
        this.policeStationCityId = policeStationCityId;
    }

    public long getPoliceStationId() {
        return policeStationId;
    }

    public void setPoliceStationId(long policeStationId) {
        this.policeStationId = policeStationId;
    }

    public String getPoliceStationName() {
        return policeStationName;
    }

    public void setPoliceStationName(String policeStationName) {
        this.policeStationName = policeStationName;
    }

    public String getPoliceStationCode() {
        return policeStationCode;
    }

    public void setPoliceStationCode(String policeStationCode) {
        this.policeStationCode = policeStationCode;
    }

    public String getPoliceStationContact() {
        return policeStationContact;
    }

    public void setPoliceStationContact(String policeStationContact) {
        this.policeStationContact = policeStationContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
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

}
