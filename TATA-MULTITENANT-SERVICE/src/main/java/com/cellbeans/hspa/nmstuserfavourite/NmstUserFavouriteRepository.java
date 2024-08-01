
package com.cellbeans.hspa.nmstuserfavourite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstuserfavourite.NmstUserFavourite;
import java.lang.String;
import java.util.List;

public interface NmstUserFavouriteRepository extends JpaRepository<NmstUserFavourite, Long> {

    
	Page<NmstUserFavourite> findByUfTitleAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstUserFavourite> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstUserFavourite> findByUfTitleContains(String key);
}

