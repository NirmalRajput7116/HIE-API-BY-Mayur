
package com.cellbeans.hspa.nmstvillage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstvillage.NmstVillage;
import java.lang.String;
import java.util.List;

public interface NmstVillageRepository extends JpaRepository<NmstVillage, Long> {

    
	Page<NmstVillage> findByVillageNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstVillage> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstVillage> findByVillageNameContains(String key);
}

