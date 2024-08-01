package com.cellbeans.hspa.mradfilmsize;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MradFilmSizeRepository extends JpaRepository<MradFilmSize, Long> {

    Page<MradFilmSize> findByFsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MradFilmSize> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MradFilmSize> findByFsNameContains(String key);

}
            
