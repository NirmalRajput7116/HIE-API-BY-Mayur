package com.cellbeans.hspa.trnbedstatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TrnBedStatusRepository extends JpaRepository<TrnBedStatus, Long> {
    Page<TrnBedStatus> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    TrnBedStatus findOneByTbsBedIdBedIdAndIsDeletedFalseAndIsActiveTrue(long bedid);

    TrnBedStatus findOneByTbsBedIdBedIdAndTbsStatusAndIsDeletedFalseAndIsActiveTrue(long bedid, int status);

    List<TrnBedStatus> findAllByTbsBedIdBedWardIdWardIdEqualsAndTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(long wordid, int status);

    List<TrnBedStatus> findAllByTbsBedIdBedRoomIdRoomIdEqualsAndTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(long roomid, int status);

    List<TrnBedStatus> findAllByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(int status);

    int countByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(int status);

    TrnBedStatus findByTbsBedIdBedIdAndIsDeletedFalseAndIsActiveTrue(Long bedId);

    TrnBedStatus findByTbsBedIdBedIdAndTbsStatusAndIsDeletedFalseAndIsActiveTrue(long bedId, int bedStatus);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `trn_bed_status` SET `tbs_status` = ?1 WHERE `tbs_bed_id` = ?2", nativeQuery = true)
    int bedstatusupdate(@Param("status") int status, @Param("bedid") Long bedid);

    Page<TrnBedStatus> findAllByTbsStatusAndTbsBedIdEmegencyAndIsActiveTrueAndIsDeletedFalse(int status, Boolean bedtype, Pageable page);

    //get occupied bed by unit id
    List<TrnBedStatus> findAllByTbsStatusEqualsAndTbsBedIdBedUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(int status, long unitId);

    //by sachin
    List<TrnBedStatus> findAllByTbsBedIdBedWardIdWardIdEqualsAndIsActiveTrueAndIsDeletedFalse(long wordid);

    List<TrnBedStatus> findAllByTbsBedIdBedRoomIdRoomIdEqualsAndIsActiveTrueAndIsDeletedFalse(long roomid);

}
            
