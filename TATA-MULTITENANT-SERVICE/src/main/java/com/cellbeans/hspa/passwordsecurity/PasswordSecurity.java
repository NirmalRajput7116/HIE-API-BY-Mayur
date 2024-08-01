package com.cellbeans.hspa.passwordsecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "password_security")
public class PasswordSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    private Long id;

    @JsonInclude(NON_NULL)
    @Column(name = "minLength")
    private Integer minLength = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "maxLength")
    private Integer maxLength = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "minSpecialCharacter")
    private Integer minSpecialCharacter = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "uppercaseLetter")
    private Integer uppercaseLetter = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "minNumericDigit")
    private Integer minNumericDigit = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "forceReset")
    private Boolean forceReset = false;

    @JsonInclude(NON_NULL)
    @Column(name = "duration")
    private Integer duration = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "date")
    private Integer date = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "month")
    private Integer month = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "year")
    private Integer year = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "active")
    private Boolean active = true;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "resetDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date resetDate;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "pwdEndDate")
    private Date pwdEndDate;

    @JsonInclude(NON_NULL)
    @Column(name = "resetDone", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean resetDone = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinSpecialCharacter() {
        return minSpecialCharacter;
    }

    public void setMinSpecialCharacter(Integer minSpecialCharacter) {
        this.minSpecialCharacter = minSpecialCharacter;
    }

    public void setUppercaseLetter(Integer uppercaseLetter) {
        this.uppercaseLetter = uppercaseLetter;
    }

    public Integer getUppercaseLetter() {
        return uppercaseLetter;
    }

    public Integer getMinNumericDigit() {
        return minNumericDigit;
    }

    public void setMinNumericDigit(Integer minNumericDigit) {
        this.minNumericDigit = minNumericDigit;
    }

    public Boolean getForceReset() {
        return forceReset;
    }

    public void setForceReset(Boolean forceReset) {
        this.forceReset = forceReset;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public Date getPwdEndDate() {
        return pwdEndDate;
    }

    public void setPwdEndDate(Date pwdEndDate) {
        this.pwdEndDate = pwdEndDate;
    }

    public Boolean getResetDone() {
        return resetDone;
    }

    public void setResetDone(Boolean resetDone) {
        this.resetDone = resetDone;
    }
}
