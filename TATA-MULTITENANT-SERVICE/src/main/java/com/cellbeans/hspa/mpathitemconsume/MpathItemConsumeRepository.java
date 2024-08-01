package com.cellbeans.hspa.mpathitemconsume;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathItemConsumeRepository extends JpaRepository<MpathItemConsume, Long> {

    Page<MpathItemConsume> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathItemConsume> findAllByItemConsumeTestIdTestIdAndIsActiveTrueAndIsDeletedFalse(long testId);

}
            
