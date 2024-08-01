package com.cellbeans.hspa.mbillservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_emptype")
public class EmpType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staffTypeId", unique = true, nullable = true)
    private int staffTypeId;

    @JsonInclude(NON_NULL)
    @Column(name = "staffTypeName")
    private String staffTypeName;

    public int getStaffTypeId() {
        return staffTypeId;
    }

    public void setStaffTypeId(int staffTypeId) {
        this.staffTypeId = staffTypeId;
    }

    public String getStaffTypeName() {
        return staffTypeName;
    }

    public void setStaffTypeName(String staffTypeName) {
        this.staffTypeName = staffTypeName;
    }

}
