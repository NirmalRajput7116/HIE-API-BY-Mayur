package com.cellbeans.hspa.mstpatient;

public class PatientDao {

    String patientName;
    String username;
    String password;
    String dob;
    String age;
    String userMonth;
    String userDay;
    Long gender;
    String mobileno;
    String email;
    String address;
    Long city;
    Long bloodgroup;
    String pincode;
    String pid;
    String client;
    String userImage;
    String firstName;
    String lastName;
    Long titleId;
    //    @Column(name = "isRegByApi", columnDefinition = "binary(1) default false", nullable = true)
    Boolean isRegByApi;
    String uploadfile;
    String userIdDocFp;
    String userInsuranceCardNo;
    String userInsuranceCardFp;
    String userInsuranceDateOfIssue;
    String userInsuranceDateOfExp;
    String userUid;
    Long userInsuranceId;
    Long userNationalityId;
    Long userId;
    Long userCardTypeId;
    String userMobileCode;
    Boolean insurancePatient;
    String userPaymentCardNo;
    String userPaymentCardHolderName;
    String userPaymentCardExpiryDate;
    String createdBy;
    Long patientType;
    Long unitId;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(Long bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getIsRegByApi() {
        return isRegByApi;
    }

    public void setIsRegByApi(Boolean regByApi) {
        isRegByApi = regByApi;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public String getUserMonth() {
        return userMonth;
    }

    public void setUserMonth(String userMonth) {
        this.userMonth = userMonth;
    }

    public String getUserDay() {
        return userDay;
    }

    public void setUserDay(String userDay) {
        this.userDay = userDay;
    }

    public String getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(String uploadfile) {
        this.uploadfile = uploadfile;
    }

    public String getUserIdDocFp() {
        return userIdDocFp;
    }

    public void setUserIdDocFp(String userIdDocFp) {
        this.userIdDocFp = userIdDocFp;
    }

    public String getUserInsuranceCardNo() {
        return userInsuranceCardNo;
    }

    public void setUserInsuranceCardNo(String userInsuranceCardNo) {
        this.userInsuranceCardNo = userInsuranceCardNo;
    }

    public String getUserInsuranceCardFp() {
        return userInsuranceCardFp;
    }

    public void setUserInsuranceCardFp(String userInsuranceCardFp) {
        this.userInsuranceCardFp = userInsuranceCardFp;
    }

    public String getUserInsuranceDateOfIssue() {
        return userInsuranceDateOfIssue;
    }

    public void setUserInsuranceDateOfIssue(String userInsuranceDateOfIssue) {
        this.userInsuranceDateOfIssue = userInsuranceDateOfIssue;
    }

    public String getUserInsuranceDateOfExp() {
        return userInsuranceDateOfExp;
    }

    public void setUserInsuranceDateOfExp(String userInsuranceDateOfExp) {
        this.userInsuranceDateOfExp = userInsuranceDateOfExp;
    }

    public Long getUserInsuranceId() {
        return userInsuranceId;
    }

    public void setUserInsuranceId(Long userInsuranceId) {
        this.userInsuranceId = userInsuranceId;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public Long getUserNationalityId() {
        return userNationalityId;
    }

    public void setUserNationalityId(Long userNationalityId) {
        this.userNationalityId = userNationalityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserMobileCode() {
        return userMobileCode;
    }

    public void setUserMobileCode(String userMobileCode) {
        this.userMobileCode = userMobileCode;
    }

    public Boolean getInsurancePatient() {
        return insurancePatient;
    }

    public void setInsurancePatient(Boolean insurancePatient) {
        this.insurancePatient = insurancePatient;
    }

    public String getUserPaymentCardNo() {
        return userPaymentCardNo;
    }

    public void setUserPaymentCardNo(String userPaymentCardNo) {
        this.userPaymentCardNo = userPaymentCardNo;
    }

    public String getUserPaymentCardHolderName() {
        return userPaymentCardHolderName;
    }

    public void setUserPaymentCardHolderName(String userPaymentCardHolderName) {
        this.userPaymentCardHolderName = userPaymentCardHolderName;
    }

    public String getUserPaymentCardExpiryDate() {
        return userPaymentCardExpiryDate;
    }

    public void setUserPaymentCardExpiryDate(String userPaymentCardExpiryDate) {
        this.userPaymentCardExpiryDate = userPaymentCardExpiryDate;
    }

    public Long getUserCardTypeId() {
        return userCardTypeId;
    }

    public void setUserCardTypeId(Long userCardTypeId) {
        this.userCardTypeId = userCardTypeId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getPatientType() {
        return patientType;
    }

    public void setPatientType(Long patientType) {
        this.patientType = patientType;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}

