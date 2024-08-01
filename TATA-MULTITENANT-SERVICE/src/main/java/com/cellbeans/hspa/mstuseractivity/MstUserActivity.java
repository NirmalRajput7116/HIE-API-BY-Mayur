
package com.cellbeans.hspa.mstuseractivity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.sql.Blob;
import java.sql.Time;
import java.util.Collection;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_user_activity")
public class MstUserActivity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uaId", unique = true, nullable = true)
    private Long uaId;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "uaLoginDatetime")
    private Date uaLoginDatetime;

    @JsonInclude(NON_NULL)
    @Column(name = "uaLoginUserId")

    private Long uaLoginUserId;
    @JsonInclude(NON_NULL)

    @Column(name = "uaLogoutDatetime")
    private Date uaLogoutDatetime;

    public Long getUaId() {
        return uaId;
    }

    public void setUaId(Long uaId) {
        this.uaId = uaId;
    }

    public Date getUaLoginDatetime() {
        return uaLoginDatetime;
    }

    public void setUaLoginDatetime(Date uaLoginDatetime) {
        this.uaLoginDatetime = uaLoginDatetime;
    }

    public Long getUaLoginUserId() {
        return uaLoginUserId;
    }

    public void setUaLoginUserId(Long uaLoginUserId) {
        this.uaLoginUserId = uaLoginUserId;
    }

    public Date getUaLogoutDatetime() {
        return uaLogoutDatetime;
    }

    public void setUaLogoutDatetime(Date uaLogoutDatetime) {
        this.uaLogoutDatetime = uaLogoutDatetime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}