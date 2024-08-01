package com.cellbeans.hspa.tinvenquirysupplier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvEnquirySupplierRepositry extends JpaRepository<TinvEnquirySupplier, Long> {

    List<TinvEnquirySupplier> findAllByEsPieIdPieId(Long key);

}
