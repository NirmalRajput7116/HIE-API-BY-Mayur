package com.cellbeans.hspa.mipdward;

import com.cellbeans.hspa.mipdfloor.MipdFloor;
import com.cellbeans.hspa.mstclass.MstClass;
import com.cellbeans.hspa.mstgender.MstGender;
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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mipd_ward")
public class MipdWard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wardId", unique = true, nullable = true)
    private long wardId;

    @JsonInclude(NON_NULL)
    @Column(name = "wardName")
    private String wardName;

    @JsonInclude(NON_NULL)
    @Column(name = "wardCode")
    private String wardCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "wardClassId")
    private MstClass wardClassId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "wardFloorId")
    private MipdFloor wardFloorId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "wardGenderId")
    private MstGender wardGenderId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "wardUnitId")
    private MstUnit wardUnitId;

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

    public long getWardId() {
        return wardId;
    }

    public void setWardId(long wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public MipdFloor getWardFloorId() {
        return wardFloorId;
    }

    public void setWardFloorId(MipdFloor wardFloorId) {
        this.wardFloorId = wardFloorId;
    }

    public MstGender getWardGenderId() {
        return wardGenderId;
    }

    public void setWardGenderId(MstGender wardGenderId) {
        this.wardGenderId = wardGenderId;
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

    public MstUnit getWardUnitId() {
        return wardUnitId;
    }

    public void setWardUnitId(MstUnit wardUnitId) {
        this.wardUnitId = wardUnitId;
    }

    public MstClass getWardClassId() {
        return wardClassId;
    }

    public void setWardClassId(MstClass wardClassId) {
        this.wardClassId = wardClassId;
    }

}
