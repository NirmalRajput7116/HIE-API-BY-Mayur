package com.cellbeans.hspa.tbillbill;

import java.util.List;

public interface BillSearchDao {

    public List<TBillBillDto> findBillDetails(BillSearch objBillSearch);
    //@Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billVisitId.visitPatientId.patientMrNo, t.billVisitId.visitPatientId.patientUserId.userFirstname, t.billVisitId.visitPatientId.patientUserId.userMiddlename, t.billVisitId.visitPatientId.patientUserId.userLastname, t.billOpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId) from TBillBill t where t.tbillUnitId.unitId = :unitId And t.isActive=true AND t.isDeleted = false")
    //public Page<TBillBillDto> findBillDetails(BillSearch objBillSearch,Pageable page);

}
