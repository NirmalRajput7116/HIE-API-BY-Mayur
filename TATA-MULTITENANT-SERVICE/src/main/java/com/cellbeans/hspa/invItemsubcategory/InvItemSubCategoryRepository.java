package com.cellbeans.hspa.invItemsubcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemSubCategoryRepository extends JpaRepository<InvItemSubCategory, Long> {

    Page<InvItemSubCategory> findAllByIsActiveTrueAndIsDeletedFalseOrderByIscName(Pageable page);

    Page<InvItemSubCategory> findByIscNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIscName(String name, Pageable page);

    List<InvItemSubCategory> findAllByIscCIdIcIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

}
