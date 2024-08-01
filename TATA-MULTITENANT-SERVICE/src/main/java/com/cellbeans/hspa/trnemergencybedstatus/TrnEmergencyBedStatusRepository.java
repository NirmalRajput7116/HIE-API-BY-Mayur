package com.cellbeans.hspa.trnemergencybedstatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnEmergencyBedStatusRepository extends JpaRepository<TrnEmergencyBedStatus, Long> {

    Page<TrnEmergencyBedStatus> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    TrnEmergencyBedStatus  findOneByTbsBedIdBedIdAndIsDeletedFalseAndIsActiveTrue(long bedid);
//    TrnEmergencyBedStatus findOneByTbsBedIdBedIdAndTbsStatusAndIsDeletedFalseAndIsActiveTrue(long bedid, int status);
//    List<TrnEmergencyBedStatus> findAllByTbsBedIdBedWardIdWardIdEqualsAndTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(long wordid, int status);
//    List<TrnEmergencyBedStatus> findAllByTbsBedIdBedRoomIdRoomIdEqualsAndTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(long roomid, int status);
//    List<TrnEmergencyBedStatus> findAllByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(int status);
//
//    @Modifying
//    @Transactional
//    @Query(value ="UPDATE `trn_bed_status` SET `tbs_status` = ?1 WHERE `tbs_bed_id` = ?2", nativeQuery = true)
//    int bedstatusupdate(@Param("status") int status, @Param("bedid") Long bedid);

    Page<TrnEmergencyBedStatus> findAllByEbsBedIdBedWardIdWardIdAndEbsStatusAndIsActiveTrueAndIsDeletedFalse(long wardid, int status, Pageable page);

    //change bed status by bed id
    TrnEmergencyBedStatus findTopByEbsBedIdBedIdAndIsActiveTrueAndIsDeletedFalse(long bedid);

}
            
