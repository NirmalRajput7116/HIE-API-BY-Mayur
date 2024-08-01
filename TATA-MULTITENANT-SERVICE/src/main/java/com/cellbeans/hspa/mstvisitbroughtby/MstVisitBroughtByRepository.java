package com.cellbeans.hspa.mstvisitbroughtby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MstVisitBroughtByRepository extends JpaRepository<MstVisitBroughtBy, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mst_visit_brought_by vbb WHERE vbb.vbb_visit_id = :visitId", nativeQuery = true)
    int deleteVbb(@Param("visitId") Long visitId);

    List<MstVisitBroughtBy> findAllByVbbVisitIdVisitIdEquals(Long visitId);
}
