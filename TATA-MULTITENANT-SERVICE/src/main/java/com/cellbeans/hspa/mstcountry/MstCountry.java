package com.cellbeans.hspa.mstcountry;

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
@Table(name = "mst_country")
public class MstCountry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countryId", unique = true, nullable = true)
    private long countryId;

    @JsonInclude(NON_NULL)
    @Column(name = "countryCode")
    private String countryCode;

    @JsonInclude(NON_NULL)
    @Column(name = "countryName")
    private String countryName;

    @JsonInclude(NON_NULL)
    @Column(name = "countryIsdCode")
    private String countryIsdCode;

    @JsonInclude(NON_NULL)
    @Column(name = "countryIsdCodeMLen", columnDefinition = "Integer(3) default 10", nullable = true)
    private Integer countryIsdCodeMLen = 10;

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

    public String getCountryIsdCode() {
        return countryIsdCode;
    }

    public void setCountryIsdCode(String countryIsdCode) {
        this.countryIsdCode = countryIsdCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public Integer getCountryIsdCodeMLen() {
        return countryIsdCodeMLen;
    }

    public void setCountryIsdCodeMLen(Integer countryIsdCodeMLen) {
        this.countryIsdCodeMLen = countryIsdCodeMLen;
    }
}
