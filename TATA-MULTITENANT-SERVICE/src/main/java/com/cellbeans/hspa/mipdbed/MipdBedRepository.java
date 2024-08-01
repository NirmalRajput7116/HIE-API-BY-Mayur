package com.cellbeans.hspa.mipdbed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MipdBedRepository extends JpaRepository<MipdBed, Long> {
    Page<MipdBed> findByBedNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdBed> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdBed> findAllByBedWardIdWardIdEquals(Long key);

    List<MipdBed> findByBedNameContains(String key);

    List<MipdBed> findByBedRoomIdRoomIdAndIsActiveTrueAndIsDeletedFalse(Long roomId);

    @Query(value = "SELECT * FROM mipd_bed r where  r.is_active = 1 and r.is_deleted = 0 and  r.emegency = 1 and (r.bed_id not in(select a.eba_bed_id from trn_emergency_bed_allocation a where a.eba_patient_status = 0 or  a.eba_patient_status = 1))   \n#pageable\n", countQuery = "SELECT COUNT(r.bed_id) FROM mipd_bed r where  r.is_active = 1 and r.is_deleted = 0 and  r.emegency = 1 and (r.bed_id not in(select a.eba_bed_id from trn_emergency_bed_allocation a where a.eba_patient_status = 0 or  a.eba_patient_status = 1))", nativeQuery = true)
    Page<MipdBed> findAllAvailableBedForEmegency(Pageable page);

}
            
