package com.cellbeans.hspa.trnservicecabinschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrnServiceCabinScheduleRepository extends JpaRepository<TrnServiceCabinSchedule, Long> {

    //    Page<TrnServiceCabinSchedule> findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
    Page<TrnServiceCabinSchedule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Query(value = "SELECT * FROM trn_service_cabin_schedule tbs INNER JOIN tbill_bill_service tbbs ON tbs.scs_bs_id=tbbs.bs_id INNER JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id Inner JOIN mst_department md ON ms.staff_department_id = md.department_id  where tbs.is_active = 1 and tbs.is_deleted = 0 and tbs.scs_date = CURDATE() and (tbbs.bs_status = 0 or tbbs.bs_status = 1) ", nativeQuery = true)
    List<TrnServiceCabinSchedule> findAllByIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT * FROM trn_service_cabin_schedule tbs INNER JOIN tbill_bill_service tbbs ON tbs.scs_bs_id=tbbs.bs_id INNER JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id Inner JOIN mst_department md ON ms.staff_department_id = md.department_id  where tbs.is_active = 1 and tbs.is_deleted = 0 and tbs.scs_date = CURDATE() and tbbs.bs_status != 2 and md.department_name = :deptname ", nativeQuery = true)
    List<TrnServiceCabinSchedule> findAllByIsActiveTrueAndIsDeletedFalse(@Param("deptname") String deptname);

    @Query(value = "SELECT * FROM trn_service_cabin_schedule tbs INNER JOIN tbill_bill_service tbbs ON tbs.scs_bs_id=tbbs.bs_id INNER JOIN tbill_bill t ON t.bill_id = tbbs.bs_bill_id   where tbs.is_active = 1 and tbs.is_deleted = 0 and tbs.scs_date = CURDATE() and t.tbill_unit_id= :id  and t.ipd_bill=0 and   tbbs.bs_status = 1 ORDER BY tbbs.last_modified_date DESC", nativeQuery = true)
    List<TrnServiceCabinSchedule> findAllByIsActiveTrueAndIsDeletedFalseAndScsBsIdBsStatusEqulasOrScsBsIdBsStatusEqulasAndScsBsIdBsDateEqualsAndScsBsIdBsBillIdIpdBillFalse(@Param("id") Long id);

    TrnServiceCabinSchedule findAllByScsBsIdBsIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Query(value = "SELECT * FROM trn_service_cabin_schedule tbs INNER JOIN tbill_bill_service tbbs ON tbs.scs_bs_id=tbbs.bs_id INNER JOIN tbill_bill t ON t.bill_id = tbbs.bs_bill_id    INNER JOIN mst_staff s ON tbbs.bs_staff_id = s.staff_id   where tbs.is_active = 1 and tbs.is_deleted = 0 and tbs.scs_date = CURDATE() and t.ipd_bill=0 and  tbbs.bs_status = 1  and tbbs.bs_staff_id in (:staffList)  ORDER BY tbbs.last_modified_date DESC", nativeQuery = true)
    List<TrnServiceCabinSchedule> findAllByDoctorList(@Param("staffList") Long[] staffList);

}


