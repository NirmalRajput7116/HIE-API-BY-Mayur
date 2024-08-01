package com.cellbeans.hspa.mstnotificationconfig;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstNotificationConfigRepository extends JpaRepository<MstNotificationConfig, Long> {

    Page<MstNotificationConfig> findByNcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstNotificationConfig> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstNotificationConfig> findByNcNameContains(String key);

}
            
