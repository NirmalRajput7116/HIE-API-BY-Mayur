package com.cellbeans.hspa.mstgpdepartment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstGpDepartmentRepository extends JpaRepository<MstGpDepartment, Long> {

    Page<MstGpDepartment> findByGpDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByGpDepartmentNameAsc(String name, Pageable page);

    Page<MstGpDepartment> findByGpDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstGpDepartment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstGpDepartment> findByGpDepartmentNameContains(String key);

}
            
