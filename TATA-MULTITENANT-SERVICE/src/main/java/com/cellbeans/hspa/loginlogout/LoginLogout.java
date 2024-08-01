package com.cellbeans.hspa.loginlogout;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "loginlogouttrail")
public class LoginLogout {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lltId")
    private Long lltId;

    @JsonInclude(NON_NULL)
    @Column(name = "lltLoginDate")
    private Date lltLoginDate;

    @JsonInclude(NON_NULL)
    @Column(name = "lltLogoutDate")
    private Date lltLogoutDate;

    @JsonInclude(NON_NULL)
    @Column(name = "lltUserName")
    private String lltUserName;

    @JsonInclude(NON_NULL)
    @Column(name = "lltUserId")
    private String lltUserId;

    @JsonInclude(NON_NULL)
    @Column(name = "lltUnitId")
    private String lltUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "lltUnitName")
    private String lltUnitName;

    @JsonInclude(NON_NULL)
    @Column(name = "ipAddress")
    private String ipAddress;

    public LoginLogout() {
    }

    public Long getLltId() {
        return lltId;
    }

    public void setLltId(Long lltId) {
        this.lltId = lltId;
    }

    public Date getLltLoginDate() {
        return lltLoginDate;
    }

    public void setLltLoginDate(Date lltLoginDate) {
        this.lltLoginDate = lltLoginDate;
    }

    public Date getLltLogoutDate() {
        return lltLogoutDate;
    }

    public void setLltLogoutDate(Date lltLogoutDate) {
        this.lltLogoutDate = lltLogoutDate;
    }

    public String getLltUserName() {
        return lltUserName;
    }

    public void setLltUserName(String lltUserName) {
        this.lltUserName = lltUserName;
    }

    public String getLltUserId() {
        return lltUserId;
    }

    public void setLltUserId(String lltUserId) {
        this.lltUserId = lltUserId;
    }

    public String getLltUnitId() {
        return lltUnitId;
    }

    public void setLltUnitId(String lltUnitId) {
        this.lltUnitId = lltUnitId;
    }

    public String getLltUnitName() {
        return lltUnitName;
    }

    public void setLltUnitName(String lltUnitName) {
        this.lltUnitName = lltUnitName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
