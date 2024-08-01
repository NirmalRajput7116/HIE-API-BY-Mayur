package com.cellbeans.hspa.trnappointment;

import com.cellbeans.hspa.mstCitizenIdProof.MstCitizenIdProof;
import com.cellbeans.hspa.mstbloodgroup.MstBloodgroup;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstgpdepartment.MstGpDepartment;
import com.cellbeans.hspa.mstgpdesignation.MstGpDesignation;
import com.cellbeans.hspa.mstgpset.MstGpSet;
import com.cellbeans.hspa.mstmaritalstatus.MstMaritalStatus;
import com.cellbeans.hspa.mstnationality.MstNationality;
import com.cellbeans.hspa.msttitle.MstTitle;
import com.cellbeans.hspa.mstuser.MstUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "trn_appointment_portal")
public class TrnPortalAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentPortalId", unique = true, nullable = true)
    private long appointmentPortalId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentUserImage")
    private String appointmentUserImage;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentNationalityId")
    private MstNationality appointmentNationalityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentCipId")
    private MstCitizenIdProof appointmentCipId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentUserUid")
    private String appointmentUserUid;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentUserMobileCode")
    private String appointmentUserMobileCode;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentMobile")
    private String appointmentMobile;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentUserStdCode")
    private String appointmentUserStdCode;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentPhone")
    private String appointmentPhone;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentPrefixId")
    private MstTitle appointmentPrefixId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentFname")
    private String appointmentFname;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentMname")
    private String appointmentMname;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentLname")
    private String appointmentLname;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentDob")
    private String appointmentDob;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentAge")
    private String appointmentAge;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentEmail")
    private String appointmentEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentAddress")
    private String appointmentAddress;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentCityId")
    private MstCity appointmentCityId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentPinCode")
    private String appointmentPinCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentBloodGroup")
    private MstBloodgroup appointmentBloodGroup;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentGpDesignationId")
    private MstGpDesignation appointmentGpDesignationId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentGpDepartmentId")
    private MstGpDepartment appointmentGpDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentGpSetId")
    private MstGpSet appointmentGpSetId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentUserMARNo")
    private String appointmentUserMARNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentMsId")
    private MstMaritalStatus appointmentMsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentGenderId")
    private MstGender appointmentGenderId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentPudIdId")
    private String appointmentPudIdId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentAppointmentDate")
    private String appointmentAppointmentDate;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "appointmentUserId")
    private MstUser appointmentUserId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentRemark")
    private String appointmentRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsCancelled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean appointmentIsCancelled = false;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentCancelReason")
    private String appointmentCancelReason;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsConfirmed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean appointmentIsConfirmed = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getAppointmentPortalId() {
        return appointmentPortalId;
    }

    public void setAppointmentPortalId(long appointmentPortalId) {
        this.appointmentPortalId = appointmentPortalId;
    }

    public MstGender getAppointmentGenderId() {
        return appointmentGenderId;
    }

    public void setAppointmentGenderId(MstGender appointmentGenderId) {
        this.appointmentGenderId = appointmentGenderId;
    }

    public String getAppointmentPinCode() {
        return appointmentPinCode;
    }

    public void setAppointmentPinCode(String appointmentPinCode) {
        this.appointmentPinCode = appointmentPinCode;
    }

    public String getAppointmentUserImage() {
        return appointmentUserImage;
    }

    public void setAppointmentUserImage(String appointmentUserImage) {
        this.appointmentUserImage = appointmentUserImage;
    }

    public MstNationality getAppointmentNationalityId() {
        return appointmentNationalityId;
    }

    public void setAppointmentNationalityId(MstNationality appointmentNationalityId) {
        this.appointmentNationalityId = appointmentNationalityId;
    }

    public MstCitizenIdProof getAppointmentCipId() {
        return appointmentCipId;
    }

    public void setAppointmentCipId(MstCitizenIdProof appointmentCipId) {
        this.appointmentCipId = appointmentCipId;
    }

    public String getAppointmentUserUid() {
        return appointmentUserUid;
    }

    public void setAppointmentUserUid(String appointmentUserUid) {
        this.appointmentUserUid = appointmentUserUid;
    }

    public String getAppointmentUserMobileCode() {
        return appointmentUserMobileCode;
    }

    public void setAppointmentUserMobileCode(String appointmentUserMobileCode) {
        this.appointmentUserMobileCode = appointmentUserMobileCode;
    }

    public String getAppointmentMobile() {
        return appointmentMobile;
    }

    public void setAppointmentMobile(String appointmentMobile) {
        this.appointmentMobile = appointmentMobile;
    }

    public String getAppointmentUserStdCode() {
        return appointmentUserStdCode;
    }

    public void setAppointmentUserStdCode(String appointmentUserStdCode) {
        this.appointmentUserStdCode = appointmentUserStdCode;
    }

    public String getAppointmentPhone() {
        return appointmentPhone;
    }

    public void setAppointmentPhone(String appointmentPhone) {
        this.appointmentPhone = appointmentPhone;
    }

    public MstTitle getAppointmentPrefixId() {
        return appointmentPrefixId;
    }

    public void setAppointmentPrefixId(MstTitle appointmentPrefixId) {
        this.appointmentPrefixId = appointmentPrefixId;
    }

    public String getAppointmentFname() {
        return appointmentFname;
    }

    public void setAppointmentFname(String appointmentFname) {
        this.appointmentFname = appointmentFname;
    }

    public String getAppointmentMname() {
        return appointmentMname;
    }

    public void setAppointmentMname(String appointmentMname) {
        this.appointmentMname = appointmentMname;
    }

    public String getAppointmentLname() {
        return appointmentLname;
    }

    public void setAppointmentLname(String appointmentLname) {
        this.appointmentLname = appointmentLname;
    }

    public String getAppointmentDob() {
        return appointmentDob;
    }

    public void setAppointmentDob(String appointmentDob) {
        this.appointmentDob = appointmentDob;
    }

    public String getAppointmentAge() {
        return appointmentAge;
    }

    public void setAppointmentAge(String appointmentAge) {
        this.appointmentAge = appointmentAge;
    }

    public String getAppointmentEmail() {
        return appointmentEmail;
    }

    public void setAppointmentEmail(String appointmentEmail) {
        this.appointmentEmail = appointmentEmail;
    }

    public String getAppointmentAddress() {
        return appointmentAddress;
    }

    public void setAppointmentAddress(String appointmentAddress) {
        this.appointmentAddress = appointmentAddress;
    }

    public MstCity getAppointmentCityId() {
        return appointmentCityId;
    }

    public void setAppointmentCityId(MstCity appointmentCityId) {
        this.appointmentCityId = appointmentCityId;
    }

    public MstBloodgroup getAppointmentBloodGroup() {
        return appointmentBloodGroup;
    }

    public void setAppointmentBloodGroup(MstBloodgroup appointmentBloodGroup) {
        this.appointmentBloodGroup = appointmentBloodGroup;
    }

    public MstGpDesignation getAppointmentGpDesignationId() {
        return appointmentGpDesignationId;
    }

    public void setAppointmentGpDesignationId(MstGpDesignation appointmentGpDesignationId) {
        this.appointmentGpDesignationId = appointmentGpDesignationId;
    }

    public MstGpDepartment getAppointmentGpDepartmentId() {
        return appointmentGpDepartmentId;
    }

    public void setAppointmentGpDepartmentId(MstGpDepartment appointmentGpDepartmentId) {
        this.appointmentGpDepartmentId = appointmentGpDepartmentId;
    }

    public MstGpSet getAppointmentGpSetId() {
        return appointmentGpSetId;
    }

    public void setAppointmentGpSetId(MstGpSet appointmentGpSetId) {
        this.appointmentGpSetId = appointmentGpSetId;
    }

    public String getAppointmentUserMARNo() {
        return appointmentUserMARNo;
    }

    public void setAppointmentUserMARNo(String appointmentUserMARNo) {
        this.appointmentUserMARNo = appointmentUserMARNo;
    }

    public MstMaritalStatus getAppointmentMsId() {
        return appointmentMsId;
    }

    public void setAppointmentMsId(MstMaritalStatus appointmentMsId) {
        this.appointmentMsId = appointmentMsId;
    }

    public String getAppointmentPudIdId() {
        return appointmentPudIdId;
    }

    public void setAppointmentPudIdId(String appointmentPudIdId) {
        this.appointmentPudIdId = appointmentPudIdId;
    }

    public String getAppointmentAppointmentDate() {
        return appointmentAppointmentDate;
    }

    public void setAppointmentAppointmentDate(String appointmentAppointmentDate) {
        this.appointmentAppointmentDate = appointmentAppointmentDate;
    }

    public MstUser getAppointmentUserId() {
        return appointmentUserId;
    }

    public void setAppointmentUserId(MstUser appointmentUserId) {
        this.appointmentUserId = appointmentUserId;
    }

    public String getAppointmentRemark() {
        return appointmentRemark;
    }

    public void setAppointmentRemark(String appointmentRemark) {
        this.appointmentRemark = appointmentRemark;
    }

    public Boolean getAppointmentIsCancelled() {
        return appointmentIsCancelled;
    }

    public void setAppointmentIsCancelled(Boolean appointmentIsCancelled) {
        this.appointmentIsCancelled = appointmentIsCancelled;
    }

    public String getAppointmentCancelReason() {
        return appointmentCancelReason;
    }

    public void setAppointmentCancelReason(String appointmentCancelReason) {
        this.appointmentCancelReason = appointmentCancelReason;
    }

    public Boolean getAppointmentIsConfirmed() {
        return appointmentIsConfirmed;
    }

    public void setAppointmentIsConfirmed(Boolean appointmentIsConfirmed) {
        this.appointmentIsConfirmed = appointmentIsConfirmed;
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
