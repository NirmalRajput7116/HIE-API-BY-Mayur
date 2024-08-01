package com.cellbeans.hspa.emailutility;

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
@Table(name = "Email_Configuration")
public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mailId", unique = true, nullable = true)
    private long mailId;

    @JsonInclude(NON_NULL)
    @Column(name = "mailUserName")
    private String mailUserName;

    @JsonInclude(NON_NULL)
    @Column(name = "mailPassword")
    private String mailPassword;

    @JsonInclude(NON_NULL)
    @Column(name = "mailTrasportProtocol")
    private String mailTrasportProtocol;

    @JsonInclude(NON_NULL)
    @Column(name = "mailSmtpAuth")
    private String mailSmtpAuth;

    @JsonInclude(NON_NULL)
    @Column(name = "mailSmtpStarttlsEnable")
    private String mailSmtpStarttlsEnable;

    @JsonInclude(NON_NULL)
    @Column(name = "mailDebug")
    private String mailDebug;

    @JsonInclude(NON_NULL)
    @Column(name = "mailHost")
    private String mailHost;

    @JsonInclude(NON_NULL)
    @Column(name = "mailPort")
    private String mailPort;

    @JsonInclude(NON_NULL)
    @Column(name = "mailSmtpConnectiontimeout")
    private String mailSmtpConnectiontimeout;

    @JsonInclude(NON_NULL)
    @Column(name = "mailSmtpTimeout")
    private String mailSmtpTimeout;

    @JsonInclude(NON_NULL)
    @Column(name = "mailSmtpWritetimeout")
    private String mailSmtpWritetimeout;

    @JsonInclude(NON_NULL)
    @Column(name = "mailSmtpStarttlsRequired")
    private String mailSmtpStarttlsRequired;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    public long getMailId() {
        return mailId;
    }

    public void setMailId(long mailId) {
        this.mailId = mailId;
    }

    public String getMailUserName() {
        return mailUserName;
    }

    public void setMailUserName(String mailUserName) {
        this.mailUserName = mailUserName;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getMailTrasportProtocol() {
        return mailTrasportProtocol;
    }

    public void setMailTrasportProtocol(String mailTrasportProtocol) {
        this.mailTrasportProtocol = mailTrasportProtocol;
    }

    public String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(String mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public String getMailSmtpStarttlsEnable() {
        return mailSmtpStarttlsEnable;
    }

    public void setMailSmtpStarttlsEnable(String mailSmtpStarttlsEnable) {
        this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
    }

    public String getMailDebug() {
        return mailDebug;
    }

    public void setMailDebug(String mailDebug) {
        this.mailDebug = mailDebug;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getMailPort() {
        return mailPort;
    }

    public void setMailPort(String mailPort) {
        this.mailPort = mailPort;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public String getMailSmtpConnectiontimeout() {
        return mailSmtpConnectiontimeout;
    }

    public void setMailSmtpConnectiontimeout(String mailSmtpConnectiontimeout) {
        this.mailSmtpConnectiontimeout = mailSmtpConnectiontimeout;
    }

    public String getMailSmtpTimeout() {
        return mailSmtpTimeout;
    }

    public void setMailSmtpTimeout(String mailSmtpTimeout) {
        this.mailSmtpTimeout = mailSmtpTimeout;
    }

    public String getMailSmtpWritetimeout() {
        return mailSmtpWritetimeout;
    }

    public void setMailSmtpWritetimeout(String mailSmtpWritetimeout) {
        this.mailSmtpWritetimeout = mailSmtpWritetimeout;
    }

    public String getMailSmtpStarttlsRequired() {
        return mailSmtpStarttlsRequired;
    }

    public void setMailSmtpStarttlsRequired(String mailSmtpStarttlsRequired) {
        this.mailSmtpStarttlsRequired = mailSmtpStarttlsRequired;
    }

    @Override
    public String toString() {
        return "EmailDTO{" + "mailId=" + mailId + ", mailUserName='" + mailUserName + '\'' + ", mailPassword='" + mailPassword + '\'' + ", mailTrasportProtocol='" + mailTrasportProtocol + '\'' + ", mailSmtpAuth='" + mailSmtpAuth + '\'' + ", mailSmtpStarttlsEnable='" + mailSmtpStarttlsEnable + '\'' + ", mailDebug='" + mailDebug + '\'' + ", mailHost='" + mailHost + '\'' + ", mailPort='" + mailPort + '\'' + ", mailSmtpConnectiontimeout='" + mailSmtpConnectiontimeout + '\'' + ", mailSmtpTimeout='" + mailSmtpTimeout + '\'' + ", mailSmtpWritetimeout='" + mailSmtpWritetimeout + '\'' + ", mailSmtpStarttlsRequired='" + mailSmtpStarttlsRequired + '\'' + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}