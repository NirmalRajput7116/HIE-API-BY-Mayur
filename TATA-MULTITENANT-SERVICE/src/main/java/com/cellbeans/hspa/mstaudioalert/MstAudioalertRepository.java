package com.cellbeans.hspa.mstaudioalert;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstAudioalertRepository extends JpaRepository<MstAudioalert, Long> {

    Page<MstAudioalert> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstAudioalert> findAllByAuAlertnameContainsAndIsActiveTrueAndIsDeletedFalse(String key, Pageable page);
}
