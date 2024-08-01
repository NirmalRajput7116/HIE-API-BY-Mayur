package com.cellbeans.hspa.invitemsubgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemSubgroupRepository extends JpaRepository<InvItemSubGroup, Long> {

    Page<InvItemSubGroup> findAllByIsActiveTrueAndIsDeletedFalseOrderByIsgName(Pageable page);

    Page<InvItemSubGroup> findByIsgNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIsgName(String name, String name1, Pageable page);

    List<InvItemSubGroup> findAllByIsgGIdIgIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

}
