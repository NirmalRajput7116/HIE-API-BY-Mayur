
package com.cellbeans.hspa.ncligroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.ncligroup.NcliGroup;
import java.lang.String;
import java.util.List;

public interface NcliGroupRepository extends JpaRepository<NcliGroup, Long> {

    
	Page<NcliGroup> findByGroupNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NcliGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NcliGroup> findByGroupNameContains(String key);

        List<NcliGroup> findAllByGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(Long cfId);
}

