package com.cellbeans.hspa.mstpincode;

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
@Table(name = "mst_pin_code")
public class MstPinCode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pinCodeId", unique = true, nullable = true)
    private long pinCodeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pinCodeCityId")
    private MstCity pinCodeCityId;

    @JsonInclude(NON_NULL)
    @Column(name = "pinCode")
    private String pinCode;

    @JsonInclude(NON_NULL)
    @Column(name = "pinCodeArea")
    private String pinCodeArea;

    @JsonInclude(NON_NULL)
    @Column(name = "pinCodePostOffice")
    private String pinCodePostOffice;

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

    public long getPinCodeId() {
        return pinCodeId;
    }

    public void setPinCodeId(long pinCodeId) {
        this.pinCodeId = pinCodeId;
    }

    public MstCity getPinCodeCityId() {
        return pinCodeCityId;
    }

    public void setPinCodeCityId(MstCity pinCodeCityId) {
        this.pinCodeCityId = pinCodeCityId;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPinCodeArea() {
        return pinCodeArea;
    }

    public void setPinCodeArea(String pinCodeArea) {
        this.pinCodeArea = pinCodeArea;
    }

    public String getPinCodePostOffice() {
        return pinCodePostOffice;
    }

    public void setPinCodePostOffice(String pinCodePostOffice) {
        this.pinCodePostOffice = pinCodePostOffice;
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
