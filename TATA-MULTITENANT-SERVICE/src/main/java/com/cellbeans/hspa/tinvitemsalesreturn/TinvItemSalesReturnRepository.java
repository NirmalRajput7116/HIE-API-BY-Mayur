package com.cellbeans.hspa.tinvitemsalesreturn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TinvItemSalesReturnRepository extends JpaRepository<TinvItemSalesReturn, Long> {

    Page<TinvItemSalesReturn> findByIsrNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvItemSalesReturn> findByIsrNameContainsAndIsrUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String name, long unitId, Pageable page);

    Page<TinvItemSalesReturn> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TinvItemSalesReturn> findAllByIsrUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitId, Pageable page);

    List<TinvItemSalesReturn> findByIsrIdContains(String key);

    //MIS start
    @Query(value = "SELECT * FROM tinv_item_sales_return  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  \n#pageable\n", countQuery = "Select count(*) from tinv_item_sales_return  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    Page<TinvItemSalesReturn> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TinvItemSalesReturn> findAllByIsrPsIdPsVisitIdVisitPatientIdPatientMrNoContainsOrIsrPsIdPsPatientFirstNameContainsOrIsrPsIdPsPatientMiddleNameContainsOrIsrPsIdPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm, Pageable page);

    @Query(value = "SELECT * FROM tinv_item_sales_return  where is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from tinv_item_sales_return  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<TinvItemSalesReturn> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, Pageable page);

    //print MIS
    List<TinvItemSalesReturn> findAllByIsrPsIdPsVisitIdVisitPatientIdPatientMrNoContainsOrIsrPsIdPsPatientFirstNameContainsOrIsrPsIdPsPatientMiddleNameContainsOrIsrPsIdPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm);

    @Query(value = "SELECT * FROM tinv_item_sales_return  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()   ", nativeQuery = true)
    List<TinvItemSalesReturn> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(); //rohan

    List<TinvItemSalesReturn> findAllByIsrUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitid);

    Page<TinvItemSalesReturn> findAllByIsrUnitIdUnitIdAndIsrPsIdPharmacyTypeAndIsActiveTrueAndIsDeletedFalse(Long unitid, int phrmcyType, Pageable page);

    @Query(value = "SELECT * FROM tinv_item_sales_return  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)   ", nativeQuery = true)
    List<TinvItemSalesReturn> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed);
    //MIS end

    List<TinvItemSalesReturn> findAllByIsActiveTrueAndIsDeletedFalse();

}
            
