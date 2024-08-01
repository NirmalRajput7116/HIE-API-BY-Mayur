package com.cellbeans.hspa.tinvscrapesale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TinvScrapeSaleRepository extends JpaRepository<TinvScrapeSale, Long> {
    
    
	/*Page<TinvScrapeSale> findByAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
                    

	Page<TinvScrapeSale> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);*/

    /*List<TinvScrapeSale> findByContains(String key);*/
}
            
