package com.cellbeans.hspa.temrotrequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrOtRequestRepository extends JpaRepository<TemrOtRequest, Long> {

    Page<TemrOtRequest> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TemrOtRequest> findByOtrTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(long id, Pageable page);

    List<TemrOtRequest> findByUntitIdAndIsActiveTrueAndIsDeletedFalseAndIsScheduledFalse(String unitId);

    List<TemrOtRequest> findByOtrTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(long unitId);
}
