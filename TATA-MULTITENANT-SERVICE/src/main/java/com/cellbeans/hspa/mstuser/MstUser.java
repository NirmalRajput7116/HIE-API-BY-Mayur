package com.cellbeans.hspa.mstuser;

import com.cellbeans.hspa.mstCitizenIdProof.MstCitizenIdProof;
import com.cellbeans.hspa.mstbloodgroup.MstBloodgroup;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstemploymentstatus.MstEmploymentStatus;
import com.cellbeans.hspa.mstethnicity.MstEthnicity;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstgpdepartment.MstGpDepartment;
import com.cellbeans.hspa.mstgpdesignation.MstGpDesignation;
import com.cellbeans.hspa.mstgpset.MstGpSet;
import com.cellbeans.hspa.mstincomecategory.MstIncomeCategory;
import com.cellbeans.hspa.mstlandmark.MstLandmark;
import com.cellbeans.hspa.mstlanguage.MstLanguage;
import com.cellbeans.hspa.mstmaritalstatus.MstMaritalStatus;
import com.cellbeans.hspa.mstnationality.MstNationality;
import com.cellbeans.hspa.mstpriority.MstPriority;
import com.cellbeans.hspa.mstrace.MstRace;
import com.cellbeans.hspa.mstsocialstatus.MstSocialStatus;
import com.cellbeans.hspa.msttitle.MstTitle;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_user")
public class MstUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = true, nullable = true)
    private long userId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userTitleId")
    private MstTitle userTitleId;
//    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
//	private List<sponsorcombination> scCompanyTypeId;

    @JsonInclude(NON_NULL)
    @Column(name = "userName")
    private String userName;

    @JsonInclude(NON_NULL)
    @Column(name = "password")
    private String password;

    @JsonInclude(NON_NULL)
    @Column(name = "userFirstname")
    private String userFirstname;

    @JsonInclude(NON_NULL)
    @Column(name = "userPincode")
    private String userPincode;

    @JsonInclude(NON_NULL)
    @Column(name = "userMiddlename")
    private String userMiddlename;

    @JsonInclude(NON_NULL)
    @Column(name = "userLastname")
    private String userLastname;

    @JsonInclude(NON_NULL)
    @Column(name = "userFullname")
    private String userFullname;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userBloodgroupId")
    private MstBloodgroup userBloodgroupId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userGenderId")
    private MstGender userGenderId;

    @JsonInclude(NON_NULL)
    @Column(name = "userDob")
    private String userDob;

    @JsonInclude(NON_NULL)
    @Column(name = "userAge")
    private String userAge;

    @JsonInclude(NON_NULL)
    @Column(name = "userImage")
    private String userImage;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userNationalityId")
    private MstNationality userNationalityId;

    @JsonInclude(NON_NULL)
    @Column(name = "userUid")
    private String userUid;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userLanguageId")
    private MstLanguage userLanguageId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userEthnicityId")
    private MstEthnicity userEthnicityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userRaceId")
    private MstRace userRaceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userIcId")
    private MstIncomeCategory userIcId;

    @JsonInclude(NON_NULL)
    @Column(name = "userPassportNo")
    private String userPassportNo;

    @JsonInclude(NON_NULL)
    @Column(name = "userArea")
    private String userArea;

    @JsonInclude(NON_NULL)
    @Column(name = "postOffice")
    private String postOffice;

    @JsonInclude(NON_NULL)
    @Column(name = "userDrivingNo")
    private String userDrivingNo;

    @JsonInclude(NON_NULL)
    @Column(name = "userEmail")
    private String userEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "userResidencePhone")
    private String userResidencePhone;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userMsId")
    private MstMaritalStatus userMsId;

    @JsonInclude(NON_NULL)
    @Column(name = "userDoa")
    private String userDoa;

    @JsonInclude(NON_NULL)
    @Column(name = "userMonth")
    private String userMonth;

    @JsonInclude(NON_NULL)
    @Column(name = "userDay")
    private String userDay;

    @JsonIgnore
    @JsonInclude(NON_NULL)
    @Column(name = "userOfficePhoneIsd")
    private String userOfficePhoneIsd;

    @JsonIgnore
    @JsonInclude(NON_NULL)
    @Column(name = "userOfficePhoneCode")
    private String userOfficePhoneCode;

    @JsonInclude(NON_NULL)
    @Column(name = "userOfficePhone")
    private String userOfficePhone;

    @JsonInclude(NON_NULL)
    @Column(name = "userMobileCode")
    private String userMobileCode;

    @JsonInclude(NON_NULL)
    @Column(name = "userMemo")
    private String userMemo;

    @JsonInclude(NON_NULL)
    @Column(name = "userMobile")
    private String userMobile;

    @JsonInclude(NON_NULL)
    @Column(name = "userAltContact")
    private String userAltContact;

    @JsonIgnore
    @Column(name = "userAltPhone")
    private String userAltPhone;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userEsId")
    private MstEmploymentStatus userEsId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userPriorityId")
    private MstPriority userPriorityId;

    //@ManyToOne(fetch = FetchType.LAZY)(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JoinColumn(name = "userAddressId")
    @JsonInclude(NON_NULL)
    @Column(name = "userAddress")
    private String userAddress;

//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "userCityId")
//    private MstCity cityId;

//    @JoinColumn(name = "userCityId")
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    //private MstCity userCityId;
//    private MstCity userCityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userCityId")
    private MstCity userCityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userLandmarkId")
    private MstLandmark userLandmarkId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userUnitId")
    private MstUnit userUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "userPanNo")
    private String userPanNo;

    @JsonInclude(NON_NULL)
    @Column(name = "userPfNo")
    private String userPfNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userCitizenId")
    private MstCitizenIdProof userCitizenId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    //    @CreatedBy
    @Column(nullable = true)
    private String createdBy;

    //    @JsonIgnore
//    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonIgnore
    @Column(name = "euserFirstname")
    private String euserFirstname;
    @JsonIgnore
    @Column(name = "euserLastname")
    private String euserLastname;
    @JsonIgnore
    @Column(name = "euserDob")
    private String euserDob;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "euserGenderId")
    private MstGender euserGenderId;
    @JsonIgnore
    @Column(name = "euserAge")
    private String euserAge;

    @JsonInclude(NON_NULL)
    @Column(name = "isEnglish", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isEnglish = false;

    @JsonInclude(NON_NULL)
    @Column(name = "userIdDocImage")
    private String userIdDocImage;

    @JsonInclude(NON_NULL)
    @Column(name = "userMotherMaidenName")
    private String userMotherMaidenName;

    @JsonInclude(NON_NULL)
    @Column(name = "userAbbreviationName")
    private String userAbbreviationName;

    @JsonInclude(NON_NULL)
    @Column(name = "userAbbreviation")
    private String userAbbreviation;

    @JsonInclude(NON_NULL)
    @Column(name = "userEmergencyContact")
    private String userEmergencyContact;

    @JsonInclude(NON_NULL)
    @Column(name = "userEmergencyContactNo")
    private String userEmergencyContactNo;

    @JsonInclude(NON_NULL)
    @Column(name = "userSpouseName")
    private String userSpouseName;

    @JsonInclude(NON_NULL)
    @Column(name = "userOccupation")
    private String userOccupation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userSocialStatusId")
    private MstSocialStatus userSocialStatusId;

    @JsonInclude(NON_NULL)
    @Column(name = "userPermanentArea")
    private String userPermanentArea;

    @JsonInclude(NON_NULL)
    @Column(name = "userPermanentPostOffice")
    private String userPermanentPostOffice;

    @JsonInclude(NON_NULL)
    @Column(name = "userPermanentPinCode")
    private String userPermanentPinCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userPermanentCityId")
    private MstCity userPermanentCityId;


//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "userPermanentCityId")
//    private MstCity userPermanentCityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userPermanentLandmarkId")
    private MstLandmark userPermanentLandmarkId;

    @JsonInclude(NON_NULL)
    @Column(name = "userPermanentAddress")
    private String userPermanentAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "userIsDobReal", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean userIsDobReal = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userGpSet")
    private MstGpSet userGpSet;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userGpDesignation")
    private MstGpDesignation userGpDesignation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userGpDepartment")
    private MstGpDepartment userGpDepartment;

    @JsonIgnore
    @JsonInclude(NON_NULL)
    @Column(name = "userGpMarNo")
    private String userGpMarNo;


    @Column(name = "userOtp")
    private String userOtp;


    @Column(name = "userNabhAbdmId")
    private String userNabhAbdmId;

    @Column(name = "userHealthId")
    private String userHealthId;

    @Column(name = "userHealthIdNumber")
    private String userHealthIdNumber;

    @Column(name = "userAbhaPassword")
    private String userAbhaPassword;

    @JsonInclude(NON_NULL)
    @Column(name = "userDistrictName")
    private String userDistrictName;

    @JsonInclude(NON_NULL)
    @Column(name = "userStateName")
    private String userStateName;

    @ManyToOne
    @JoinColumn(name = "euserTitleId")
    private MstTitle euserTitleId;

    @Column(name = "userXRequestId")
    private String userXRequestId;


    @JsonInclude(NON_NULL)
    @Column(name = "userAgeDetails")
    private String userAgeDetails;


//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "userVillageId")
//    private MstVillage userVillageId;

    public String getUserIdDocImage() {
        return userIdDocImage;
    }

    public void setUserIdDocImage(String userIdDocImage) {
        this.userIdDocImage = userIdDocImage;
    }

    public String getUserMotherMaidenName() {
        return userMotherMaidenName;
    }

    public void setUserMotherMaidenName(String userMotherMaidenName) {
        this.userMotherMaidenName = userMotherMaidenName;
    }

    public String getUserAbbreviationName() {
        return userAbbreviationName;
    }

    public void setUserAbbreviationName(String userAbbreviationName) {
        this.userAbbreviationName = userAbbreviationName;
    }

    public String getUserAbbreviation() {
        return userAbbreviation;
    }

    public void setUserAbbreviation(String userAbbreviation) {
        this.userAbbreviation = userAbbreviation;
    }

    public String getUserEmergencyContact() {
        return userEmergencyContact;
    }

    public void setUserEmergencyContact(String userEmergencyContact) {
        this.userEmergencyContact = userEmergencyContact;
    }

    public String getUserEmergencyContactNo() {
        return userEmergencyContactNo;
    }

    public void setUserEmergencyContactNo(String userEmergencyContactNo) {
        this.userEmergencyContactNo = userEmergencyContactNo;
    }

    public String getUserSpouseName() {
        return userSpouseName;
    }

    public void setUserSpouseName(String userSpouseName) {
        this.userSpouseName = userSpouseName;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public MstSocialStatus getUserSocialStatusId() {
        return userSocialStatusId;
    }

    public void setUserSocialStatusId(MstSocialStatus userSocialStatusId) {
        this.userSocialStatusId = userSocialStatusId;
    }

    public String getUserPermanentArea() {
        return userPermanentArea;
    }

    public void setUserPermanentArea(String userPermanentArea) {
        this.userPermanentArea = userPermanentArea;
    }

    public String getUserPermanentPostOffice() {
        return userPermanentPostOffice;
    }

    public void setUserPermanentPostOffice(String userPermanentPostOffice) {
        this.userPermanentPostOffice = userPermanentPostOffice;
    }

    public String getUserPermanentPinCode() {
        return userPermanentPinCode;
    }

    public void setUserPermanentPinCode(String userPermanentPinCode) {
        this.userPermanentPinCode = userPermanentPinCode;
    }

    public MstCity getUserPermanentCityId() {
        return userPermanentCityId;
    }

    public void setUserPermanentCityId(MstCity userPermanentCityId) {
        this.userPermanentCityId = userPermanentCityId;
    }

    public String getUserPermanentAddress() {
        return userPermanentAddress;
    }

    public void setUserPermanentAddress(String userPermanentAddress) {
        this.userPermanentAddress = userPermanentAddress;
    }

    public MstCity getUserCityId() {
        return userCityId;
    }

    public void setUserCityId(MstCity userCityId) {
        this.userCityId = userCityId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public MstTitle getUserTitleId() {
        return userTitleId;
    }

    public void setUserTitleId(MstTitle userTitleId) {
        this.userTitleId = userTitleId;
    }

    public String getUserDay() {
        return userDay;
    }

    public void setuserMonth(String userMonth) {
        this.userMonth = userMonth;
    }

    public String getuserMonth() {
        return userMonth;
    }

    public void setuserDay(String userDay) {
        this.userDay = userDay;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getuserPincode() {
        return userPincode;
    }

    public void setuserPincode(String userPincode) {
        this.userPincode = userPincode;
    }

    public String getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(String userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public MstBloodgroup getUserBloodgroupId() {
        return userBloodgroupId;
    }

    public void setUserBloodgroupId(MstBloodgroup userBloodgroupId) {
        this.userBloodgroupId = userBloodgroupId;
    }

    public MstGender getUserGenderId() {
        return userGenderId;
    }

    public void setUserGenderId(MstGender userGenderId) {
        this.userGenderId = userGenderId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public MstNationality getUserNationalityId() {
        return userNationalityId;
    }

    public void setUserNationalityId(MstNationality userNationalityId) {
        this.userNationalityId = userNationalityId;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public MstLanguage getUserLanguageId() {
        return userLanguageId;
    }

    public void setUserLanguageId(MstLanguage userLanguageId) {
        this.userLanguageId = userLanguageId;
    }

    public MstEthnicity getUserEthnicityId() {
        return userEthnicityId;
    }

    public void setUserEthnicityId(MstEthnicity userEthnicityId) {
        this.userEthnicityId = userEthnicityId;
    }

    public MstRace getUserRaceId() {
        return userRaceId;
    }

    public void setUserRaceId(MstRace userRaceId) {
        this.userRaceId = userRaceId;
    }

    public MstIncomeCategory getUserIcId() {
        return userIcId;
    }

    public void setUserIcId(MstIncomeCategory userIcId) {
        this.userIcId = userIcId;
    }

    public String getUserPassportNo() {
        return userPassportNo;
    }

    public void setUserPassportNo(String userPassportNo) {
        this.userPassportNo = userPassportNo;
    }

    public String getUserDrivingNo() {
        return userDrivingNo;
    }

    public void setUserDrivingNo(String userDrivingNo) {
        this.userDrivingNo = userDrivingNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserResidencePhone() {
        return userResidencePhone;
    }

    public void setUserResidencePhone(String userResidencePhone) {
        this.userResidencePhone = userResidencePhone;
    }

    public MstMaritalStatus getUserMsId() {
        return userMsId;
    }

    public void setUserMsId(MstMaritalStatus userMsId) {
        this.userMsId = userMsId;
    }

    public String getUserDoa() {
        return userDoa;
    }

    public void setUserDoa(String userDoa) {
        this.userDoa = userDoa;
    }

    public String getUserOfficePhone() {
        return userOfficePhone;
    }

    public void setUserOfficePhone(String userOfficePhone) {
        this.userOfficePhone = userOfficePhone;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public MstEmploymentStatus getUserEsId() {
        return userEsId;
    }

    public void setUserEsId(MstEmploymentStatus userEsId) {
        this.userEsId = userEsId;
    }

    public MstPriority getUserPriorityId() {
        return userPriorityId;
    }

    public void setUserPriorityId(MstPriority userPriorityId) {
        this.userPriorityId = userPriorityId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public MstUnit getUserUnitId() {
        return userUnitId;
    }

    public void setUserUnitId(MstUnit userUnitId) {
        this.userUnitId = userUnitId;
    }

    public String getUserPanNo() {
        return userPanNo;
    }

    public void setUserPanNo(String userPanNo) {
        this.userPanNo = userPanNo;
    }

    public String getUserPfNo() {
        return userPfNo;
    }

    public void setUserPfNo(String userPfNo) {
        this.userPfNo = userPfNo;
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

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserOfficePhoneIsd() {
        return userOfficePhoneIsd;
    }

    public void setUserOfficePhoneIsd(String userOfficePhoneIsd) {
        this.userOfficePhoneIsd = userOfficePhoneIsd;
    }

    public String getUserOfficePhoneCode() {
        return userOfficePhoneCode;
    }

    public void setUserOfficePhoneCode(String userOfficePhoneCode) {
        this.userOfficePhoneCode = userOfficePhoneCode;
    }

    public String getUserMobileCode() {
        return userMobileCode;
    }

    public void setUserMobileCode(String userMobileCode) {
        this.userMobileCode = userMobileCode;
    }
//	public List<sponsorcombination> getScCompanyTypeId() {
//		return scCompanyTypeId;
//	}
//
//	public void setScCompanyTypeId(List<sponsorcombination> scCompanyTypeId) {
//		this.scCompanyTypeId = scCompanyTypeId;
//	}

    public String getEuserFirstname() {
        return euserFirstname;
    }

    public void setEuserFirstname(String euserFirstname) {
        this.euserFirstname = euserFirstname;
    }

    public String getEuserLastname() {
        return euserLastname;
    }

    public void setEuserLastname(String euserLastname) {
        this.euserLastname = euserLastname;
    }

    public String getEuserDob() {
        return euserDob;
    }

    public void setEuserDob(String euserDob) {
        this.euserDob = euserDob;
    }

    public MstGender getEuserGenderId() {
        return euserGenderId;
    }

    public void setEuserGenderId(MstGender euserGenderId) {
        this.euserGenderId = euserGenderId;
    }

    public String getEuserAge() {
        return euserAge;
    }

    public void setEuserAge(String euserAge) {
        this.euserAge = euserAge;
    }

    public Boolean getEnglish() {
        return isEnglish;
    }

    public void setEnglish(Boolean english) {
        isEnglish = english;
    }

    public MstCitizenIdProof getUserCitizenId() {
        return userCitizenId;
    }

    public void setUserCitizenId(MstCitizenIdProof userCitizenId) {
        this.userCitizenId = userCitizenId;
    }

    public String getUserAltContact() {
        return userAltContact;
    }

    public void setUserAltContact(String userAltContact) {
        this.userAltContact = userAltContact;
    }

    public String getUserAltPhone() {
        return userAltPhone;
    }

    public void setUserAltPhone(String userAltPhone) {
        this.userAltPhone = userAltPhone;
    }

    public MstLandmark getUserLandmarkId() {
        return userLandmarkId;
    }

    public void setUserLandmarkId(MstLandmark userLandmarkId) {
        this.userLandmarkId = userLandmarkId;
    }

    public MstLandmark getUserPermanentLandmarkId() {
        return userPermanentLandmarkId;
    }

    public void setUserPermanentLandmarkId(MstLandmark userPermanentLandmarkId) {
        this.userPermanentLandmarkId = userPermanentLandmarkId;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    public Boolean getUserIsDobReal() {
        return userIsDobReal;
    }

    public void setUserIsDobReal(Boolean userIsDobReal) {
        this.userIsDobReal = userIsDobReal;
    }

    public MstGpSet getUserGpSet() {
        return userGpSet;
    }

    public void setUserGpSet(MstGpSet userGpSet) {
        this.userGpSet = userGpSet;
    }

    public MstGpDesignation getUserGpDesignation() {
        return userGpDesignation;
    }

    public void setUserGpDesignation(MstGpDesignation userGpDesignation) {
        this.userGpDesignation = userGpDesignation;
    }

    public String getUserGpMarNo() {
        return userGpMarNo;
    }

    public void setUserGpMarNo(String userGpMarNo) {
        this.userGpMarNo = userGpMarNo;
    }

    public String getUserOtp() {
        return userOtp;
    }

    public void setUserOtp(String userOtp) {
        this.userOtp = userOtp;
    }

    public MstGpDepartment getUserGpDepartment() {
        return userGpDepartment;
    }

    public void setUserGpDepartment(MstGpDepartment userGpDepartment) {
        this.userGpDepartment = userGpDepartment;
    }
    public String getUserNabhAbdmId() {
        return userNabhAbdmId;
    }

    public void setUserNabhAbdmId(String userNabhAbdmId) {
        this.userNabhAbdmId = userNabhAbdmId;
    }

    public String getUserHealthId() {
        return userHealthId;
    }

    public void setUserHealthId(String userHealthId) {
        this.userHealthId = userHealthId;
    }

    public String getUserHealthIdNumber() {
        return userHealthIdNumber;
    }

    public void setUserHealthIdNumber(String userHealthIdNumber) {
        this.userHealthIdNumber = userHealthIdNumber;
    }

    public String getUserAbhaPassword() {
        return userAbhaPassword;
    }

    public void setUserAbhaPassword(String userAbhaPassword) {
        this.userAbhaPassword = userAbhaPassword;
    }

    public String getUserDistrictName() {
        return userDistrictName;
    }

    public void setUserDistrictName(String userDistrictName) {
        this.userDistrictName = userDistrictName;
    }

    public String getUserStateName() {
        return userStateName;
    }

    public void setUserStateName(String userStateName) {
        this.userStateName = userStateName;
    }

    public MstTitle getEuserTitleId() {
        return euserTitleId;
    }

    public void setEuserTitleId(MstTitle euserTitleId) {
        this.euserTitleId = euserTitleId;
    }

//    public MstVillage getUserVillageId() {
//        return userVillageId;
//    }
//
//    public void setUserVillageId(MstVillage userVillageId) {
//        this.userVillageId = userVillageId;
//    }

    public String getUserXRequestId() {
        return userXRequestId;
    }

    public void setUserXRequestId(String userXRequestId) {
        this.userXRequestId = userXRequestId;
    }
    public String getUserAgeDetails() {
        return userAgeDetails;
    }

    public void setUserAgeDetails(String userAgeDetails) {
        this.userAgeDetails = userAgeDetails;
    }

    @Override
    public String toString() {
        return "MstUser{" +
                "userId=" + userId +
                ", userTitleId=" + userTitleId +
                ", userName='" + userName + '\'' +
                ", userXRequestId='" + userXRequestId + '\'' +
                ", password='" + password + '\'' +
                ", userFirstname='" + userFirstname + '\'' +
                ", userPincode='" + userPincode + '\'' +
                ", userMiddlename='" + userMiddlename + '\'' +
                ", userLastname='" + userLastname + '\'' +
                ", userFullname='" + userFullname + '\'' +
                ", userBloodgroupId=" + userBloodgroupId +
                ", userGenderId=" + userGenderId +
                ", userDob='" + userDob + '\'' +
                ", userAge='" + userAge + '\'' +
                ", userAgeDetails='" + userAgeDetails + '\'' +
                ", userImage='" + userImage + '\'' +
                ", userNationalityId=" + userNationalityId +
                ", userUid='" + userUid + '\'' +
                ", userNabhAbdmId='" + userNabhAbdmId + '\'' +
                ", userHealthId='" + userHealthId + '\'' +
                ", userHealthIdNumber='" + userHealthIdNumber + '\'' +
                ", userAbhaPassword='" + userAbhaPassword + '\'' +
                ", userLanguageId=" + userLanguageId +
                ", userEthnicityId=" + userEthnicityId +
                ", userRaceId=" + userRaceId +
                ", userIcId=" + userIcId +
                ", userPassportNo='" + userPassportNo + '\'' +
                ", userArea='" + userArea + '\'' +
                ", postOffice='" + postOffice + '\'' +
                ", userDrivingNo='" + userDrivingNo + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userResidencePhone='" + userResidencePhone + '\'' +
                ", userMsId=" + userMsId +
                ", userDoa='" + userDoa + '\'' +
                ", userMonth='" + userMonth + '\'' +
                ", userDay='" + userDay + '\'' +
                ", userOfficePhoneIsd='" + userOfficePhoneIsd + '\'' +
                ", userOfficePhoneCode='" + userOfficePhoneCode + '\'' +
                ", userOfficePhone='" + userOfficePhone + '\'' +
                ", userMobileCode='" + userMobileCode + '\'' +
                ", userMemo='" + userMemo + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userAltContact='" + userAltContact + '\'' +
                ", userAltPhone='" + userAltPhone + '\'' +
                ", userEsId=" + userEsId +
                ", userPriorityId=" + userPriorityId +
                ", userAddress='" + userAddress + '\'' +
                ", userCityId=" + userCityId +
                ", userLandmarkId=" + userLandmarkId +
                ", userUnitId=" + userUnitId +
                ", userPanNo='" + userPanNo + '\'' +
                ", userPfNo='" + userPfNo + '\'' +
                ", userCitizenId=" + userCitizenId +
//                ", userVillageId=" + userVillageId +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", euserFirstname='" + euserFirstname + '\'' +
                ", euserLastname='" + euserLastname + '\'' +
                ", euserTitleId=" + euserTitleId +
                ", euserDob='" + euserDob + '\'' +
                ", euserGenderId=" + euserGenderId +
                ", euserAge='" + euserAge + '\'' +
                ", userOtp='" + userOtp + '\'' +
                ", isEnglish=" + isEnglish +
                ", userIdDocImage='" + userIdDocImage + '\'' +
                ", userMotherMaidenName='" + userMotherMaidenName + '\'' +
                ", userAbbreviationName='" + userAbbreviationName + '\'' +
                ", userAbbreviation='" + userAbbreviation + '\'' +
                ", userEmergencyContact='" + userEmergencyContact + '\'' +
                ", userEmergencyContactNo='" + userEmergencyContactNo + '\'' +
                ", userSpouseName='" + userSpouseName + '\'' +
                ", userOccupation='" + userOccupation + '\'' +
                ", userSocialStatusId=" + userSocialStatusId +
                ", userPermanentArea='" + userPermanentArea + '\'' +
                ", userPermanentPostOffice='" + userPermanentPostOffice + '\'' +
                ", userPermanentPinCode='" + userPermanentPinCode + '\'' +
                ", userPermanentCityId=" + userPermanentCityId +
                ", userPermanentLandmarkId=" + userPermanentLandmarkId +
                ", userPermanentAddress='" + userPermanentAddress + '\'' +
                ", userIsDobReal=" + userIsDobReal +
                ", userGpSet=" + userGpSet +
                ", userGpDesignation=" + userGpDesignation +
                ", userGpDepartment=" + userGpDepartment +
                ", userGpMarNo='" + userGpMarNo + '\'' +
                ", userDistrictName='" + userDistrictName + '\'' +
                ", userStateName='" + userStateName + '\'' +
                '}';
    }
}
