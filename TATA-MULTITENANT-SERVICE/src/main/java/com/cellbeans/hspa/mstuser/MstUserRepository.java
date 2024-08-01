package com.cellbeans.hspa.mstuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MstUserRepository extends JpaRepository<MstUser, Long> {

    Page<MstUser> findByUserFullnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//    Page<MstUser> findByUserFirstnameContainsAndUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstUser> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstUser> findByUserFullnameContains(String key);

    MstUser findByUserNameEqualsAndPasswordEqualsAndIsActiveTrueAndIsDeletedFalse(String username, String Password);

    @Transactional
    @Modifying
    @Query(value = "update MstUser set password=:password where userName=:userName")
    int updatePassword(@Param("password") String password, @Param("userName") String userName);

    @Query(value = "select * from mst_user mu where user_id=:userId", nativeQuery = true)
    MstUser findbyUserID(@Param("userId") Long userId);

    //repo for password auto reset by vaibhav
    MstUser findOneByUserId(Long userId);
}
            
