package com.cellbeans.hspa.mipdroom;

import com.cellbeans.hspa.mipdroomamenity.MipdRoomAmenity;
import com.cellbeans.hspa.mipdward.MipdWard;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mipd_room")
public class MipdRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId", unique = true, nullable = true)
    private long roomId;

    @JsonInclude(NON_NULL)
    @Column(name = "roomName")
    private String roomName;

    @JsonInclude(NON_NULL)
    @Column(name = "roomCode")
    private String roomCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "roomWardId")
    private MipdWard roomWardId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MipdRoomAmenity> mipdRoomAmenityList = new ArrayList<>();

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "roomUnitId")
    private MstUnit roomUnitId;

    public List<MipdRoomAmenity> getMipdRoomAmenityList() {
        return mipdRoomAmenityList;
    }

    public MstUnit getRoomUnitId() {
        return roomUnitId;
    }

    public void setRoomUnitId(MstUnit roomUnitId) {
        this.roomUnitId = roomUnitId;
    }

    public void setMipdRoomAmenityList(List<MipdRoomAmenity> mipdRoomAmenityList) {
        this.mipdRoomAmenityList = mipdRoomAmenityList;
    }

    public MipdWard getRoomWardId() {
        return roomWardId;
    }

    public void setRoomWardId(MipdWard roomWardId) {
        this.roomWardId = roomWardId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
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

}            
