package com.cellbeans.hspa.tinvtaxprimarytax;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvTaxPrimaryTaxRepository extends JpaRepository<TinvTaxPrimaryTax, Long> {
    List<TinvTaxPrimaryTax> findByInvTaxTaxIdEquals(Long key);
}
