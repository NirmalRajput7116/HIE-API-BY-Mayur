package com.cellbeans.hspa.tinvpharmacyreturnbillrecepit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PharmacyReturnBillRecepitRepository extends JpaRepository<PharmacyReturnBillRecepit, Long> {

    List<PharmacyReturnBillRecepit> PbrrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(Long key);

}
