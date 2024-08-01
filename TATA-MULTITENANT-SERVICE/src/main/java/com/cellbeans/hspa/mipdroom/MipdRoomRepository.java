package com.cellbeans.hspa.mipdroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdRoomRepository extends JpaRepository<MipdRoom, Long> {

    Page<MipdRoom> findByRoomNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdRoom> findByRoomNameContainsAndIsActiveTrueAndIsDeletedFalseOrRoomCodeContainsAndIsActiveTrueAndIsDeletedFalseOrRoomWardIdWardNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, Pageable page);

    Page<MipdRoom> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdRoom> findByRoomNameContains(String key);

    List<MipdRoom> findByRoomWardIdWardIdAndIsActiveTrueAndIsDeletedFalse(Long wardId);

}
            
