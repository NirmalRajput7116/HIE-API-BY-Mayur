package com.cellbeans.hspa.tinvpharmacybillrecepit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PharmacyBillReceptRepository extends JpaRepository<PharmacyBillRecepit, Long> {

    List<PharmacyBillRecepit> PbrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(Long key);

}
