package com.cellbeans.hspa.aadharapi;

import com.cellbeans.hspa.mstbloodgroup.MstBloodgroup;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstmaritalstatus.MstMaritalStatus;
import com.cellbeans.hspa.msttitle.MstTitle;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "aadharMst")
public class AadharMst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    private Long id;

    @JsonInclude(NON_NULL)
    @Column(name = "aadhar_no")
    private String aadharNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "prefix")
    private MstTitle prefix;

    @JsonInclude(NON_NULL)
    @Column(name = "firstName")
    private String firstName;

    @JsonInclude(NON_NULL)
    @Column(name = "lastName")
    private String lastName;

    @JsonInclude(NON_NULL)
    @Column(name = "full_name")
    private String fullName;

    @JsonInclude(NON_NULL)
    @Column(name = "date_birth")
    private String dateOfBirth;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "genderId")
    private MstGender genderId;

    @JsonInclude(NON_NULL)
    @Column(name = "address")
    private String address;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cityId")
    private MstCity cityId;

    @JsonInclude(NON_NULL)
    @Column(name = "mobile")
    private String mobile;

    @JsonInclude(NON_NULL)
    @Column(name = "phone")
    private String phone;

    @JsonInclude(NON_NULL)
    @Column(name = "email")
    private String email;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "maritalStatus")
    private MstMaritalStatus maritalStatus;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bloodGroup")
    private MstBloodgroup bloodGroup;

    public AadharMst() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public MstTitle getPrefix() {
        return prefix;
    }

    public void setPrefix(MstTitle prefix) {
        this.prefix = prefix;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public MstGender getGenderId() {
        return genderId;
    }

    public void setGenderId(MstGender genderId) {
        this.genderId = genderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MstCity getCityId() {
        return cityId;
    }

    public void setCityId(MstCity cityId) {
        this.cityId = cityId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MstMaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MstMaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public MstBloodgroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(MstBloodgroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

}
