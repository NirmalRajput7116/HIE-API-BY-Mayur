package com.cellbeans.hspa.aadharapi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AadharMstRepository extends JpaRepository<AadharMst, Long> {

    List<AadharMst> findAllByAadharNoContains(String aadharNo);

}