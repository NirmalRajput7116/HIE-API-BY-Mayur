package com.cellbeans.hspa.trndischargeapproveuserlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnDischargeApproveUserListRepository extends JpaRepository<TrnDischargeApproveUserList, Long> {

    Page<TrnDischargeApproveUserList> findBydaulIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnDischargeApproveUserList> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnDischargeApproveUserList> findByDaulIdContains(String key);

    List<TrnDischargeApproveUserList> findAllByDaulStaffIdStaffIdEqualsAndDaulStatusEquals(long admissionid, String staffid);

    List<TrnDischargeApproveUserList> findAllByDaulAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalse(long admissionid);

    List<TrnDischargeApproveUserList> findAllByDaulStatusEquals(String status);

}
            
