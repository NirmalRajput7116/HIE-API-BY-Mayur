package com.cellbeans.hspa.invItemKitPackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemKitPackageRepository extends JpaRepository<InvItemKitPackage, Long> {

    Page<InvItemKitPackage> findByIkpNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvItemKitPackage> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvItemKitPackage> findByIkpNameContains(String key);

    List<InvItemKitPackage> findByIkpNameContainsAndIkpCodeContainsAndIsActiveTrueAndIsDeletedFalse(String packageName, String packageCode);

}