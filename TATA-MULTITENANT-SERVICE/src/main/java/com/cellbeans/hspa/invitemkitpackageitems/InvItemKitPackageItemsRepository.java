package com.cellbeans.hspa.invitemkitpackageitems;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemKitPackageItemsRepository extends JpaRepository<InvItemKitPackageItems, Long> {

    List<InvItemKitPackageItems> findByIkpiIkpId(Long ikpiIkpId);

    //   Page<InvItemKitPackageItems> findByIkpiIkpIdIkpNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//
//
    Page<InvItemKitPackageItems> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    //
//    List<InvItemKitPackageItems> findByIkpiIkpIdIkpNameContains(String key);
//
    List<InvItemKitPackageItems> findAllByIkpiIkpIdAndIsActiveTrueAndIsDeletedFalse(Long ikpiIkpId);

}
            

