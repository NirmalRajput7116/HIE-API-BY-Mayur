package com.cellbeans.hspa.trnbloodbank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnBloodBankRepository extends JpaRepository<TrnBloodBank, Long> {

    Page<TrnBloodBank> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnBloodBank> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnBloodBank> findByBbQuantityContains(String key);

}

