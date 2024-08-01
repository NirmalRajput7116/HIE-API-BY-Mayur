package com.cellbeans.hspa.mststaff;
//import com.cellbeans.hspa.MstCluster.MstCluster;

import com.cellbeans.hspa.MstCluster.MstCluster;
import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstdesignation.MstDesignation;
import com.cellbeans.hspa.mstrole.MstRole;
import com.cellbeans.hspa.mstspeciality.MstSpeciality;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.mstsuperspeciality.MstSuperSpeciality;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstuser.MstUser;
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
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_staff")
public class MstStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    long count;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staffId", unique = true, nullable = true)
    private long staffId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "staffUserId")
    private MstUser staffUserId;

    @JsonInclude(NON_NULL)
    @Column(name = "staffErNo")
    private String staffErNo;






    @Column(name = "UUID")
    private String UUID;

    @JsonInclude(NON_NULL)
    @Column(name = "staffExpiryDate")
    private Date staffExpiryDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "staffClusterId")
    private MstCluster staffClusterId;

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "staffClusterIdList")
    private List<MstCluster> staffClusterIdList;

    @JsonInclude(NON_NULL)
    @Column(name = "staffMinDuration")
    private String staffMinDuration;
    @JsonInclude(NON_NULL)
    @Column(name = "staffSignName")
    private String staffSignName;
    @JsonInclude(NON_NULL)
    @ManyToMany
    //@JoinColumn(name = "staffDepartmentId")
    private List<MstDepartment> staffDepartmentId;
    @JsonInclude(NON_NULL)
    @ManyToMany
    //@JoinColumn(name = "staffDepartmentId")
    private List<MstUnit> staffUnit;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "staffRole")
    private MstRole staffRole;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "staffDesignationId")
    private MstDesignation staffDesignationId;
    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MstSubDepartment> staffSdId;
    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MstCashCounter> staffCashCounter;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "staffSpecialityId")
    private MstSpeciality staffSpecialityId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "staffStoreId")
    private InvStore staffStoreId;

    // new field
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "staffSsId")
    private MstSuperSpeciality staffSsId;
    @JsonInclude(NON_NULL)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "staffServiceId")
    private List<MstStaffServices> staffServiceId;
    @JsonInclude(NON_NULL)
    @Column(name = "staffexprience")
    private String staffexprience;
    @JsonInclude(NON_NULL)
    @Column(name = "staffDoj")
    private String staffDoj;

    @JsonInclude(NON_NULL)
    @Column(name = "staffEducation")
    private String staffEducation;

    @JsonIgnore
    @Column(name = "staffnmcno")
    private String staffnmcno;

    @JsonInclude(NON_NULL)
    @Column(name = "staffAcn")
    private String staffAcn;
    @JsonInclude(NON_NULL)
    @Column(name = "isDefault", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDefault = false;
    @JsonInclude(NON_NULL)
    @Column(name = "isMedicaldepartment", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isMedicaldepartment = false;
    // new field
    @JsonInclude(NON_NULL)
    @Column(name = "isConcessionAuthority", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isConcessionAuthority = false;
    @JsonInclude(NON_NULL)
    @Column(name = "doctorShair", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean doctorShair = false;
    // new field
    @JsonInclude(NON_NULL)
    @Column(name = "staffType")
    private Integer staffType;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isVirtual", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isVirtual = false;

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
    @ManyToMany
    private List<InvStore> staffStore;

    @Column(name = "isDoctorOnline", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDoctorOnline = false;

    public String getStaffnmcno() {
        return staffnmcno;
    }

    public void setStaffnmcno(String staffnmcno) {
        this.staffnmcno = staffnmcno;
    }

    public List<InvStore> getStaffStore() {
        return staffStore;
    }

    public void setStaffStore(List<InvStore> staffStore) {
        this.staffStore = staffStore;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    public Boolean getDoctorShair() {
        return doctorShair;
    }

    public void setDoctorShair(Boolean doctorShair) {
        this.doctorShair = doctorShair;
    }

    public String getStaffSignName() {
        return staffSignName;
    }

    public void setStaffSignName(String staffSignName) {
        this.staffSignName = staffSignName;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public MstUser getStaffUserId() {
        return staffUserId;
    }

    public void setStaffUserId(MstUser staffUserId) {
        this.staffUserId = staffUserId;
    }

    public String getStaffErNo() {
        return staffErNo;
    }

    public void setStaffErNo(String staffErNo) {
        this.staffErNo = staffErNo;
    }

    public Date getStaffExpiryDate() {
        return staffExpiryDate;
    }

    public void setStaffExpiryDate(Date staffExpiryDate) {
        this.staffExpiryDate = staffExpiryDate;
    }

    public MstDesignation getStaffDesignationId() {
        return staffDesignationId;
    }

    public void setStaffDesignationId(MstDesignation staffDesignationId) {
        this.staffDesignationId = staffDesignationId;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        isDefault = isDefault;
    }

    public MstSpeciality getStaffSpecialityId() {
        return staffSpecialityId;
    }

    public void setStaffSpecialityId(MstSpeciality staffSpecialityId) {
        this.staffSpecialityId = staffSpecialityId;
    }

    public MstSuperSpeciality getStaffSsId() {
        return staffSsId;
    }

    public void setStaffSsId(MstSuperSpeciality staffSsId) {
        this.staffSsId = staffSsId;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getStaffMinDuration() {
        return staffMinDuration;
    }

    public void setStaffMinDuration(String staffMinDuration) {
        this.staffMinDuration = staffMinDuration;
    }

    public String getStaffexprience() {
        return staffexprience;
    }

    public void setStaffexprience(String staffexprience) {
        this.staffexprience = staffexprience;
    }

    public String getStaffDoj() {
        return staffDoj;
    }

    public void setStaffDoj(String staffDoj) {
        this.staffDoj = staffDoj;
    }

    public String getStaffEducation() {
        return staffEducation;
    }

    public void setStaffEducation(String staffEducation) {
        this.staffEducation = staffEducation;
    }

    public String getStaffAcn() {
        return staffAcn;
    }

    public void setStaffAcn(String staffAcn) {
        this.staffAcn = staffAcn;
    }

    public Boolean getIsMedicaldepartment() {
        return isMedicaldepartment;
    }

    public void setIsMedicaldepartment(Boolean isMedicaldepartment) {
        this.isMedicaldepartment = isMedicaldepartment;
    }

    public List<MstStaffServices> getStaffServiceId() {
        return staffServiceId;
    }

    public void setStaffServiceId(List<MstStaffServices> staffServiceId) {
        this.staffServiceId = staffServiceId;
    }

    public MstRole getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(MstRole staffRole) {
        this.staffRole = staffRole;
    }
//
//    public Boolean getMedicaldepartment() {
//        return isMedicaldepartment;
//    }
//
//    public void setMedicaldepartment(Boolean medicaldepartment) {
//        isMedicaldepartment = medicaldepartment;
//    }

    public Boolean getConcessionAuthority() {
        return isConcessionAuthority;
    }

    public void setConcessionAuthority(Boolean concessionAuthority) {
        isConcessionAuthority = concessionAuthority;
    }

    public Integer getStaffType() {
        return staffType;
    }

    public void setStaffType(Integer staffType) {
        this.staffType = staffType;
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

    public List<MstDepartment> getStaffDepartmentId() {
        return staffDepartmentId;
    }

    public void setStaffDepartmentId(List<MstDepartment> staffDepartmentId) {
        this.staffDepartmentId = staffDepartmentId;
    }

    public List<MstUnit> getStaffUnit() {
        return staffUnit;
    }

    public void setStaffUnit(List<MstUnit> staffUnit) {
        this.staffUnit = staffUnit;
    }

    public List<MstSubDepartment> getStaffSdId() {
        return staffSdId;
    }

    public void setStaffSdId(List<MstSubDepartment> staffSdId) {
        this.staffSdId = staffSdId;
    }

    public List<MstCashCounter> getStaffCashCounter() {
        return staffCashCounter;
    }

    public void setStaffCashCounter(List<MstCashCounter> staffCashCounter) {
        this.staffCashCounter = staffCashCounter;
    }

    public InvStore getStaffStoreId() {
        return staffStoreId;
    }

    public void setStaffStoreId(InvStore staffStoreId) {
        this.staffStoreId = staffStoreId;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public MstCluster getStaffClusterId() {
        return staffClusterId;
    }

    public void setStaffClusterId(MstCluster staffClusterId) {
        this.staffClusterId = staffClusterId;
    }

    public Boolean getIsDoctorOnline() {
        return isDoctorOnline;
    }

    public void setIsDoctorOnline(Boolean doctorOnline) {
        isDoctorOnline = doctorOnline;
    }

    public List<MstCluster> getStaffClusterIdList() {
        return staffClusterIdList;
    }

    public void setStaffClusterIdList(List<MstCluster> staffClusterIdList) {
        this.staffClusterIdList = staffClusterIdList;
    }



    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MstStaff mstStaff = (MstStaff) o;
        return count == mstStaff.count &&
                staffId == mstStaff.staffId;
    }




    @Override
    public int hashCode() {
        return Objects.hash(staffId);
    }




}