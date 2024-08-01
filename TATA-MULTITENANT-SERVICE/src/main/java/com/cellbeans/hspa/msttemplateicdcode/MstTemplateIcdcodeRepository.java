package com.cellbeans.hspa.msttemplateicdcode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstTemplateIcdcodeRepository extends JpaRepository<MstTemplateIcdcode, Long> {

    Page<MstTemplateIcdcode> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstTemplateIcdcode> findAllByIcEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(long id, Pageable page);

}
            
