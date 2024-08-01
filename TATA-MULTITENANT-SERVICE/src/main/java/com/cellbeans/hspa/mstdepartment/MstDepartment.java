package com.cellbeans.hspa.mstdepartment;

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
@Table(name = "mst_department")
public class MstDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentId", unique = true, nullable = true)
    private long departmentId;

    @JsonInclude(NON_NULL)
    @Column(name = "departmentName")
    private String departmentName;

    @JsonInclude(NON_NULL)
    @Column(name = "isDefault", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDefault = false;
//    @ManyToMany(mappedBy = "staffDepartmentId")
//    private Set<MstStaff> departments = new HashSet<>();

    @JsonInclude(NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    //  @JoinTable(name = "rel_department_unit", joinColumns = @JoinColumn(name = "departmentId", referencedColumnName = "departmentId"), inverseJoinColumns = @JoinColumn(name = "unitId", referencedColumnName = "unitId"))
    private List<MstUnit> departmentUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "departmentHead")
    private String departmentHead;

    @JsonInclude(NON_NULL)
    @Column(name = "isMedicaldepartment", columnDefinition = "binary(1)", nullable = true)
    private Boolean isMedicaldepartment = false;

    @JsonInclude(NON_NULL)
    @Column(name = "departmentIsAutorized", columnDefinition = "binary(1)", nullable = true)
    private Boolean departmentIsAutorized = false;

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

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public List<MstUnit> getDepartmentUnitId() {
        return departmentUnitId;
    }

    public void setDepartmentUnitId(List<MstUnit> departmentUnitId) {
        this.departmentUnitId = departmentUnitId;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Boolean getIsMedicaldepartment() {
        return isMedicaldepartment;
    }

    public void setIsMedicaldepartment(Boolean isMedicaldepartment) {
        this.isMedicaldepartment = isMedicaldepartment;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
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

    public Boolean getDepartmentIsAutorized() {
        return departmentIsAutorized;
    }

    public void setDepartmentIsAutorized(Boolean departmentIsAutorized) {
        this.departmentIsAutorized = departmentIsAutorized;
    }

    @Override
    public String toString() {
        return "MstDepartment{" + "departmentId=" + departmentId + ", departmentName='" + departmentName + '\'' + ", isDefault=" + isDefault + ", departmentUnitId=" + departmentUnitId + ", departmentHead='" + departmentHead + '\'' + ", isMedicaldepartment=" + isMedicaldepartment + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
