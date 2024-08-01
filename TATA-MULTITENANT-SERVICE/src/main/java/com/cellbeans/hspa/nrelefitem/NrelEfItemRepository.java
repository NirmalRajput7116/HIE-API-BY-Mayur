
package com.cellbeans.hspa.nrelefitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nrelefitem.NrelEfItem;
import java.lang.String;
import java.util.List;

public interface NrelEfItemRepository extends JpaRepository<NrelEfItem, Long> {

    
	Page<NrelEfItem> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NrelEfItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NrelEfItem> findByTestContains(String key);
}

