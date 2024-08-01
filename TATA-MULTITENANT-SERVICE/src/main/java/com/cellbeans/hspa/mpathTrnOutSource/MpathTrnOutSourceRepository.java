package com.cellbeans.hspa.mpathTrnOutSource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MpathTrnOutSourceRepository extends JpaRepository<MpathTrnOutSource, Long> {
//    Page<MotSurgeryNote> findBySnNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathTrnOutSource> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
    //  Page<MpathTrnOutSource> findAllByTosAgencyRateAtAgencyIdAgencyNameContainsAndCreatedDateLessThanEqualAndCreatedDateGreaterThanEqualAndIsActiveTrueAndIsDeletedFalse(String searchAgencyName,  String searchStartdate, String searchEnddateDate, Pageable page);

    @Query(value = "select * from mpath_trn_outsource tos  left outer join tbill_agency_rate ar on tos.tos_agency_rate=ar.at_id  left outer join mpath_agency ag on ar.at_agency_id=ag.agency_id  where (ag.agency_name like '%':agencyname'%') and tos.created_date BETWEEN :sd and :ed and tos.is_active=1  and tos.is_deleted=0 \n#pageable\n", countQuery = "select count(*) from mpath_trn_outsource tos  left outer join tbill_agency_rate ar on tos.tos_agency_rate=ar.at_id  left outer join mpath_agency ag on ar.at_agency_id=ag.agency_id  where (ag.agency_name like '%':agencyname'%') and DATE_FORMAT(tos.created_date,'%y-%m-%d') BETWEEN :sd AND :ed  and tos.is_active=1  and tos.is_deleted=0", nativeQuery = true)
    Page<MpathTrnOutSource> findAllListByFilter(@Param("agencyname") String searchAgencyName, @Param("sd") String searchStartdate, @Param("ed") String searchEnddate, Pageable page);

   /* @Query(value ="SELECT * FROM  mpath_trn_outsource  tos INNER JOIN tbill_bill tb ON tb.bill_id = t. ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and tb.tbill_unit_id =:unitid and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n",countQuery = "Select count(*) from tpath_bs  t INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and tb.tbill_unit_id =:unitid and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    Page<TpathBs>findAllByToFromCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long unitid, Pageable page);
*/
//    List<MotSurgeryNote> findBySnNameContains(String key);

}
            
