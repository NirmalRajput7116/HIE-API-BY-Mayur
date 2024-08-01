package com.cellbeans.hspa.memrchannelsource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "memr_channelsource")
public class MemrChannelSource {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channelSourceId", unique = true, nullable = true)
    private long channelSourceId;

    @JsonInclude(NON_NULL)
    @Column(name = "channelSourceName")
    private String channelSourceName;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default true", nullable = true)
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
    @Column(nullable = true, updatable = true)
    private Date lastModifiedDate;

    public long getChannelSourceId() {
        return channelSourceId;
    }

    public void setChannelSourceId(long channelSourceId) {
        this.channelSourceId = channelSourceId;
    }

    public String getChannelSourceName() {
        return channelSourceName;
    }

    public void setChannelSourceName(String channelSourceName) {
        this.channelSourceName = channelSourceName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public MemrChannelSource() {
    }

    @Override
    public String toString() {
        return "MemrChannelSource{" +
                "channelSourceId=" + channelSourceId +
                ", channelSourceName='" + channelSourceName + '\'' +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

