package com.cellbeans.hspa.staffconfiguration;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "staff_configuration")
public class StaffConfiguration {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scId", unique = true, nullable = true)
    private long scId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scStaffId")
    private MstStaff scStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scUnitId")
    private MstUnit scUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scDepartmentId")
    private MstDepartment scDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scSdId")
    private MstSubDepartment scSdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scCcId")
    private MstCashCounter scCcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scStoreId")
    private InvStore scStoreId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    public MstStaff getScStaffId() {
        return scStaffId;
    }

    public void setScStaffId(MstStaff scStaffId) {
        this.scStaffId = scStaffId;
    }

    public long getScId() {
        return scId;
    }

    public void setScId(long scId) {
        this.scId = scId;
    }

    public MstUnit getScUnitId() {
        return scUnitId;
    }

    public void setScUnitId(MstUnit scUnitId) {
        this.scUnitId = scUnitId;
    }

    public MstDepartment getScDepartmentId() {
        return scDepartmentId;
    }

    public void setScDepartmentId(MstDepartment scDepartmentId) {
        this.scDepartmentId = scDepartmentId;
    }

    public MstSubDepartment getScSdId() {
        return scSdId;
    }

    public void setScSdId(MstSubDepartment scSdId) {
        this.scSdId = scSdId;
    }

    public MstCashCounter getScCcId() {
        return scCcId;
    }

    public void setScCcId(MstCashCounter scCcId) {
        this.scCcId = scCcId;
    }

    public InvStore getScStoreId() {
        return scStoreId;
    }

    public void setScStoreId(InvStore scStoreId) {
        this.scStoreId = scStoreId;
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
}
