
package com.cellbeans.hspa.nmstemployeefavorites;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstemployeefavorites.NmstEmployeeFavorites;
import java.lang.String;
import java.util.List;

public interface NmstEmployeeFavoritesRepository extends JpaRepository<NmstEmployeeFavorites, Long> {

    
	Page<NmstEmployeeFavorites> findByEfTitleAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstEmployeeFavorites> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstEmployeeFavorites> findByEfTitleContains(String key);
}

