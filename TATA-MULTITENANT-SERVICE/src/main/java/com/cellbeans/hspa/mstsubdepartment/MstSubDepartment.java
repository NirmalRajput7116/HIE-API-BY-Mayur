package com.cellbeans.hspa.mstsubdepartment;

import com.cellbeans.hspa.mstdepartment.MstDepartment;
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
@Table(name = "mst_sub_department")
public class MstSubDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sdId", unique = true, nullable = true)
    private long sdId;

    @JsonInclude(NON_NULL)
    @Column(name = "sdName")
    private String sdName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sdDepartmentId")
    private MstDepartment sdDepartmentId;
//    @ManyToOne
//    @JoinColumn(name = "sdUnitId")
//    private MstUnit sdUnitId;

    @JsonInclude(NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "rel_sub_department_unit", joinColumns = @JoinColumn(name = "sdId", referencedColumnName = "sdId"), inverseJoinColumns = @JoinColumn(name = "unitId", referencedColumnName = "unitId"))
    private List<MstUnit> sdDepartmentUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "sdHead")
    private String sdHead;

    @JsonInclude(NON_NULL)
    @Column(name = "sdCode")
    private String sdCode;

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

    public long getSdId() {
        return sdId;
    }

    public void setSdId(long sdId) {
        this.sdId = sdId;
    }

    public String getSdName() {
        return sdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }

    public MstDepartment getSdDepartmentId() {
        return sdDepartmentId;
    }

    public void setSdDepartmentId(MstDepartment sdDepartmentId) {
        this.sdDepartmentId = sdDepartmentId;
    }
    //   public MstUnit getSdUnitId() {
//        return sdUnitId;
//    }
//    public void setSdUnitId(MstUnit sdUnitId) {
//        this.sdUnitId = sdUnitId;
//    }

    public List<MstUnit> getSdDepartmentUnitId() {
        return sdDepartmentUnitId;
    }

    public void setSdDepartmentUnitId(List<MstUnit> sdDepartmentUnitId) {
        this.sdDepartmentUnitId = sdDepartmentUnitId;
    }

    public String getSdHead() {
        return sdHead;
    }

    public void setSdHead(String sdHead) {
        this.sdHead = sdHead;
    }

    public String getSdCode() {
        return sdCode;
    }

    public void setSdCode(String sdCode) {
        this.sdCode = sdCode;
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

    @Override
    public String toString() {
        return "MstSubDepartment{" + "sdId=" + sdId + ", sdName='" + sdName + '\'' + ", sdDepartmentId=" + sdDepartmentId + ", sdDepartmentUnitId=" + sdDepartmentUnitId + ", sdHead='" + sdHead + '\'' + ", sdCode='" + sdCode + '\'' + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
