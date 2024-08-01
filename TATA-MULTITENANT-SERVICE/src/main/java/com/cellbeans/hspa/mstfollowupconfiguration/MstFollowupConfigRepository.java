package com.cellbeans.hspa.mstfollowupconfiguration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstFollowupConfigRepository extends JpaRepository<MstFollowupConfig, Long> {

    Page<MstFollowupConfig> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstFollowupConfig> findByFcStaffIdStaffUserIdUserFirstnameContainsOrFcStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String name1, String name2, Pageable page);

    MstFollowupConfig findByFcStaffIdStaffIdEquals(Long staffId);

}
