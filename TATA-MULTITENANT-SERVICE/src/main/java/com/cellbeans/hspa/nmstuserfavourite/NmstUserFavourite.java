
package com.cellbeans.hspa.nmstuserfavourite ;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nmst_user_favourite")
public class NmstUserFavourite implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "ufId", unique = true, nullable = true)
                            private long ufId;
                        
                            @Column(name = "ufEmployeeId")
                            private int ufEmployeeId;
                        
                           @Column(name = "ufTitle")
                           private String ufTitle;
                        
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    
    @JsonIgnore
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
                        
                            public long getUfId() {
                                    return this.ufId;
                            }

                            public void setUfId(int ufId) {
                                    this.ufId = ufId;
                            }
                        
                           public int getUfEmployeeId() {
                                    return this.ufEmployeeId;
                            }

                            public void setUfEmployeeId(int ufEmployeeId) {
                                    this.ufEmployeeId = ufEmployeeId;
                            }
                        
                            public String getUfTitle() {
                                    return this.ufTitle;
                            }

                            public void setUfTitle(String ufTitle) {
                                    this.ufTitle = ufTitle;
                            }
                        
    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }    
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
                        
}
