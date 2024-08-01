package com.cellbeans.hspa.invServiceWiseConsumptionItems;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvServiceWisePackageItemsRepository extends JpaRepository<InvServiceWisePackageItems, Long> {
    List<InvServiceWisePackageItems> findByIswpiIswpId(Long iswpiIswpId);

}
            

