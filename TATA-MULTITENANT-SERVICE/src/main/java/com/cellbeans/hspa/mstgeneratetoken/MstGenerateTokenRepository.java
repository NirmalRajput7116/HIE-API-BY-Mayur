package com.cellbeans.hspa.mstgeneratetoken;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstGenerateTokenRepository extends JpaRepository<MstGenerateToken, Long> {
    Page<MstGenerateToken> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    //    Page<MstGenerateToken> findAllByIsActiveTrueAndCreatedDateBetweenAndIsDeletedFalse(Pageable page);
    @Query(value = "select * from mst_generate_token mgt WHERE mgt.is_active = true and mgt.is_deleted = false and (DATE(mgt.created_date) between :fdate and :tdate) \n#pageable\n", countQuery = "select  * from mst_generate_token mgt WHERE mgt.is_active = true and mgt.is_deleted = false and (DATE(mgt.created_date) between :fdate and :tdate) \n#pageable\n", nativeQuery = true)
    Page<MstGenerateToken> findAllByDate(@Param("fdate") String fdate, @Param("tdate") String tdate, Pageable page);

    @Query(value = "SELECT * FROM mst_generate_token WHERE token_id= (SELECT min(token_id)FROM mst_generate_token WHERE date(created_date)= CURDATE() AND cc_id IS NULL)", nativeQuery = true)
    MstGenerateToken getNextToken();

    @Query(value = "SELECT * FROM mst_generate_token WHERE token_id= (SELECT max(token_id)FROM mst_generate_token WHERE date(created_date)= CURDATE())", nativeQuery = true)
    MstGenerateToken generateToken();

    @Query(value = "SELECT * FROM mst_generate_token WHERE token_id= (SELECT max(token_id)FROM mst_generate_token WHERE date(created_date)= CURDATE())", nativeQuery = true)
    MstGenerateToken getLastToken();

    @Query(value = "SELECT m.* FROM mst_generate_token m LEFT JOIN mst_cash_counter cc ON m.cc_id =cc.cc_id WHERE m.is_called = 1 AND DATE(m.created_date)= CURDATE()", nativeQuery = true)
    List<MstGenerateToken> findAll();

}
