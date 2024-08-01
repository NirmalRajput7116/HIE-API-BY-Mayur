package com.cellbeans.hspa.mbillpackage;

import com.cellbeans.hspa.mbillpackageservice.MbillPackageService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "billing_package")
public class MbillPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId", unique = true, nullable = true)
    private long packageId;

    @JsonInclude(NON_NULL)
    @Column(name = "packageName")
    private String packageName;

    @JsonInclude(NON_NULL)
    @Column(name = "packageCode")
    private String packageCode;

    @JsonInclude(NON_NULL)
    @Column(name = "packageType")
    private Integer packageType;

    @JsonInclude(NON_NULL)
    @Column(name = "tariifId")
    private Integer tariifId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @Transient
    public List<MbillPackageService> mbillPkg;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }

    public Integer getTariifId() {
        return tariifId;
    }

    public void setTariifId(Integer tariifId) {
        this.tariifId = tariifId;
    }

    public List<MbillPackageService> getMbillPkg() {
        return mbillPkg;
    }

    public void setMbillPkg(List<MbillPackageService> mbillPkg) {
        this.mbillPkg = mbillPkg;
    }
}