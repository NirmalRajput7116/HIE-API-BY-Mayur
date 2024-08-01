package com.cellbeans.hspa.tinvscrapesaleitem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvScrapeSaleItemRepository extends JpaRepository<TinvScrapeSaleItem, Long> {

/*    Page<TinvScrapeSaleItem> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);


    Page<TinvScrapeSaleItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvScrapeSaleItem> findByContains(String key);*/

    List<TinvScrapeSaleItem> findBySsiSsIdSsIdAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
