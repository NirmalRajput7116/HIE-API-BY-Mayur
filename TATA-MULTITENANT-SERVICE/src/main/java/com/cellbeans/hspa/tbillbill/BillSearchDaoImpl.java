package com.cellbeans.hspa.tbillbill;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BillSearchDaoImpl implements BillSearchDao {

    @PersistenceContext
    private EntityManager entityManager;

    /*@Override
    public List<TBillBill> findBillDetails(BillSearch objBillSearch) {
        String Query="";

        if(objBillSearch.getBillNumber()==null){
            objBillSearch.setBillNumber("");
        }
        if(objBillSearch.getPatientMrNo()==null){
            objBillSearch.setPatientMrNo("");
        }

        if(objBillSearch.getType().equalsIgnoreCase("opd")){
             Query="select p from TBillBill p where p.billVisitId.visitPatientId.patientMrNo like '%"+objBillSearch.getPatientMrNo()+"%' ";

            *//*if(objBillSearch.getPatientMrNo()!=null && objBillSearch.getPatientMrNo()!=""){
				Query+=" and p.billVisitId.visitPatientId.patientMrNo='"+objBillSearch.getPatientMrNo()+"' ";
			}*//*
			 if(objBillSearch.getBillNumber()!=""){
				 Query+=" and p.billNumber like '%"+objBillSearch.getBillNumber()+"%' ";
			 }else if(objBillSearch.getUserFirstname()!=null && objBillSearch.getUserFirstname()!=""){
				Query+=" and p.billVisitId.visitPatientId.patientUserId.userFirstname like '%"+objBillSearch.getUserFirstname()+"%' ";
			}
			
		}else if(objBillSearch.getType().equalsIgnoreCase("ipd")){
			 Query="select p from TBillBill p where p.billAdmissionId.admissionPatientId.patientMrNo like '%"+objBillSearch.getPatientMrNo()+"%' ";
			 
			*//*if(objBillSearch.getPatientMrNo()!=null && objBillSearch.getPatientMrNo()!=""){
				Query+=" and p.billAdmissionId.admissionPatientId.patientMrNo='"+objBillSearch.getPatientMrNo()+"' ";
			}*//*
			 if(objBillSearch.getBillNumber()!=""){
				 Query+=" and p.billNumber  like '%"+objBillSearch.getBillNumber()+"%' ";
			 }else if(objBillSearch.getUserFirstname()!=null && objBillSearch.getUserFirstname()!=""){
				Query+=" and p.billAdmissionId.admissionPatientId.patientUserId.userFirstname like '%"+objBillSearch.getUserFirstname()+"%' ";
			}
			
		}else{ //PUPUN1492653000/10
			 Query="select p from TBillBill p where p.billNumber like '%"+objBillSearch.getBillNumber()+"%' ";
			if(objBillSearch.getPatientMrNo()!=null && objBillSearch.getPatientMrNo()!=""){
				Query+=" and (p.billVisitId.visitPatientId.patientMrNo like '%"+objBillSearch.getPatientMrNo()+"%' OR p.billAdmissionId.admissionPatientId.patientMrNo like '%"+objBillSearch.getPatientMrNo()+"%' ) ";
			}else if(objBillSearch.getUserFirstname()!=null && objBillSearch.getUserFirstname()!=""){
				Query+=" and (p.billVisitId.visitPatientId.patientUserId.userFirstname like '%"+objBillSearch.getUserFirstname()+"%' OR p.billAdmissionId.admissionPatientId.patientUserId.userFirstname like '%"+objBillSearch.getUserFirstname()+"%' ) ";
			}
		}
		
		if(objBillSearch.getTariffId()!=0){
			Query+=" and p.billTariffId.tariffId="+objBillSearch.getTariffId()+" ";
		}else if(objBillSearch.getClassId()!=0){
			Query+=" and p.billClassId.classId="+objBillSearch.getClassId()+" ";
		}


		Query+=" and p.tbillUnitId.unitId="+objBillSearch.getUnitId()+" and Date(p.billDate) between '"+objBillSearch.getFromdate()+"' And '" +objBillSearch.getTodate()+ "'";

		// System.out.println(Query);
		List<TBillBill> BillSearchList=entityManager.createQuery(Query,TBillBill.class).setFirstResult(objBillSearch.getOffset()-1)
				.setMaxResults(objBillSearch.getLimit())
				.getResultList();
		String CountQuery=StringUtils.replace(Query,"p fr","count(p.billId) fr");
		long count=(long)entityManager.createQuery(CountQuery).getSingleResult();
//		for (TBillBill tBillBill : objbilllist) {
//			if(tBillBill.getBillTariffId().getIsDeleted())
//			{
//				tBillBill.getBillTariffId().setTariffName("-");
//			}
//			tBillBill.setTotal(objbilllist.size());
//		}

		if(count!=0){
			BillSearchList.get(0).setTotal((int)count);
		}
		return BillSearchList;
	}*/
    @Override
    public List<TBillBillDto> findBillDetails(BillSearch objBillSearch) {
        String Query = "";
        long count = 0;
        if (objBillSearch.getBillNumber() == null) {
            objBillSearch.setBillNumber("");
        }
        if (objBillSearch.getPatientMrNo() == null) {
            objBillSearch.setPatientMrNo("");
        }
        if (objBillSearch.getType().equalsIgnoreCase("opd")) {
            Query = "select new com.cellbeans.hspa.tbillbill.TBillBillDto(p.billId, p.billNumber, p.createdDate, p.ipdBill, p.billVisitId.visitPatientId.patientMrNo, p.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,p.billVisitId.visitPatientId.patientUserId.userFirstname, p.billVisitId.visitPatientId.patientUserId.userMiddlename, p.billVisitId.visitPatientId.patientUserId.userLastname, p.billOpdNumber, p.billTotCoPay, p.billTotCoPayOutstanding, p.finalBill, p.billSubTotal, p.billNetPayable, p.billAmountPaid, p.billOutstanding, p.billNill, p.billTariffId.tariffId,p.companyNetPay,p.grossAmount,p.isApprove,p.isRejected) from TBillBill p where p.billVisitId.visitPatientId.patientMrNo like '%" + objBillSearch.getPatientMrNo() + "%' ";
            if (objBillSearch.getBillNumber() != "") {
                Query += " and p.billNumber like '%" + objBillSearch.getBillNumber() + "%' ";
            } else if (objBillSearch.getUserFirstname() != null && objBillSearch.getUserFirstname() != "") {
                Query += " and p.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + objBillSearch.getUserFirstname() + "%' ";
            } else if (objBillSearch.getUserLastname() != null && objBillSearch.getUserLastname() != "") {
                Query += " and p.billVisitId.visitPatientId.patientUserId.userLastname like '%" + objBillSearch.getUserLastname() + "%' ";
            }

        } else if (objBillSearch.getType().equalsIgnoreCase("ipd")) {
            Query = "select new com.cellbeans.hspa.tbillbill.TBillBillDto(p.billId, p.billNumber, p.createdDate, p.ipdBill, p.billAdmissionId.admissionPatientId.patientMrNo, p.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName, p.billAdmissionId.admissionPatientId.patientUserId.userFirstname, p.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, p.billAdmissionId.admissionPatientId.patientUserId.userLastname, p.billIpdNumber, p.billTotCoPay, p.billTotCoPayOutstanding, p.finalBill, p.billSubTotal, p.billNetPayable, p.billAmountPaid, p.billOutstanding, p.billNill, p.billTariffId.tariffId,p.companyNetPay,p.grossAmount,p.isApprove,p.isRejected) from TBillBill p where p.billAdmissionId.admissionPatientId.patientMrNo like '%" + objBillSearch.getPatientMrNo() + "%' and p.ipdBill = true";
            if (objBillSearch.getBillNumber() != "") {
                Query += " and p.billNumber  like '%" + objBillSearch.getBillNumber() + "%' ";
            } else if (objBillSearch.getUserFirstname() != null && objBillSearch.getUserFirstname() != "") {
                Query += " and p.billAdmissionId.admissionPatientId.patientUserId.userFirstname like '%" + objBillSearch.getUserFirstname() + "%' ";
            } else if (objBillSearch.getUserLastname() != null && objBillSearch.getUserLastname() != "") {
                Query += " and p.billVisitId.visitPatientId.patientUserId.userLastname like '%" + objBillSearch.getUserLastname() + "%' ";
            }

        } else { //PUPUN1492653000/10
            Query = "select new com.cellbeans.hspa.tbillbill.TBillBillDto(p.billId, p.billNumber, p.createdDate, p.ipdBill, p.billVisitId.visitPatientId.patientMrNo, p.billVisitId.visitPatientId.patientUserId.userTitleId.titleName, p.billVisitId.visitPatientId.patientUserId.userFirstname, p.billVisitId.visitPatientId.patientUserId.userMiddlename, p.billVisitId.visitPatientId.patientUserId.userLastname, p.billEmrNumber, p.billTotCoPay, p.billTotCoPayOutstanding, p.finalBill, p.billSubTotal, p.billNetPayable, p.billAmountPaid, p.billOutstanding, p.billNill, p.billTariffId.tariffId,p.companyNetPay,p.grossAmount, p.isApprove,p.isRejected) from TBillBill p where p.billNumber like '%" + objBillSearch.getBillNumber() + "%' and p.emrbill = true ";
            if (objBillSearch.getPatientMrNo() != null && objBillSearch.getPatientMrNo() != "") {
                Query += " and (p.billVisitId.visitPatientId.patientMrNo like '%" + objBillSearch.getPatientMrNo() + "%' )";
            } else if (objBillSearch.getUserFirstname() != null && objBillSearch.getUserFirstname() != "") {
                Query += " and (p.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + objBillSearch.getUserFirstname() + "%' ) ";
            } else if (objBillSearch.getUserLastname() != null && objBillSearch.getUserLastname() != "") {
                Query += " and (p.billVisitId.visitPatientId.patientUserId.userLastname like '%" + objBillSearch.getUserLastname() + "%' ) ";
            }
        }
        if (objBillSearch.getTariffId() != 0) {
            Query += " and p.billTariffId.tariffId=" + objBillSearch.getTariffId() + " ";
        } else if (objBillSearch.getClassId() != 0) {
            Query += " and p.billClassId.classId=" + objBillSearch.getClassId() + " ";
        }
        Query += " and p.isCancelled = false and p.isActive = true and p.isDeleted = false and p.tbillUnitId.unitId=" + objBillSearch.getUnitId();
        if ((objBillSearch.getFromdate() != null) && (objBillSearch.getTodate() != null)) {
            Query += "and Date(p.billDate) between '" + objBillSearch.getFromdate() + "' And '" + objBillSearch.getTodate() + "'";
        }
        List<TBillBillDto> BillSearchList = entityManager.createQuery(Query, TBillBillDto.class).setFirstResult((objBillSearch.getOffset() - 1) * objBillSearch.getLimit()).setMaxResults(objBillSearch.getLimit()).getResultList();
        if (objBillSearch.getType().equalsIgnoreCase("opd")) {
            String CountQuery = StringUtils.replace(Query, "new com.cellbeans.hspa.tbillbill.TBillBillDto(p.billId, p.billNumber, p.createdDate, p.ipdBill, p.billVisitId.visitPatientId.patientMrNo, p.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,p.billVisitId.visitPatientId.patientUserId.userFirstname, p.billVisitId.visitPatientId.patientUserId.userMiddlename, p.billVisitId.visitPatientId.patientUserId.userLastname, p.billOpdNumber, p.billTotCoPay, p.billTotCoPayOutstanding, p.finalBill, p.billSubTotal, p.billNetPayable, p.billAmountPaid, p.billOutstanding, p.billNill, p.billTariffId.tariffId,p.companyNetPay,p.grossAmount,p.isApprove,p.isRejected) ", "count(p.billId) ");
            count = (Long) entityManager.createQuery(CountQuery).getSingleResult();

        } else if (objBillSearch.getType().equalsIgnoreCase("ipd")) {
            String CountQuery = StringUtils.replace(Query, "new com.cellbeans.hspa.tbillbill.TBillBillDto(p.billId, p.billNumber, p.createdDate, p.ipdBill, p.billAdmissionId.admissionPatientId.patientMrNo, p.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName, p.billAdmissionId.admissionPatientId.patientUserId.userFirstname, p.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, p.billAdmissionId.admissionPatientId.patientUserId.userLastname, p.billIpdNumber, p.billTotCoPay, p.billTotCoPayOutstanding, p.finalBill, p.billSubTotal, p.billNetPayable, p.billAmountPaid, p.billOutstanding, p.billNill, p.billTariffId.tariffId,p.companyNetPay,p.grossAmount,p.isApprove,p.isRejected) ", "count(p.billId) ");
            count = (Long) entityManager.createQuery(CountQuery).getSingleResult();
        } else if (objBillSearch.getType().equalsIgnoreCase("emg")) {
            String CountQuery = StringUtils.replace(Query, "new com.cellbeans.hspa.tbillbill.TBillBillDto(p.billId, p.billNumber, p.createdDate, p.ipdBill, p.billVisitId.visitPatientId.patientMrNo, p.billVisitId.visitPatientId.patientUserId.userTitleId.titleName, p.billVisitId.visitPatientId.patientUserId.userFirstname, p.billVisitId.visitPatientId.patientUserId.userMiddlename, p.billVisitId.visitPatientId.patientUserId.userLastname, p.billEmrNumber, p.billTotCoPay, p.billTotCoPayOutstanding, p.finalBill, p.billSubTotal, p.billNetPayable, p.billAmountPaid, p.billOutstanding, p.billNill, p.billTariffId.tariffId,p.companyNetPay,p.grossAmount, p.isApprove,p.isRejected) ", "count(p.billId) ");
            count = (Long) entityManager.createQuery(CountQuery).getSingleResult();
        }
        if (count != 0) {
            if (BillSearchList.size() != 0) {
                BillSearchList.get(0).setTotal((int) count);
            }
        }
        return BillSearchList;
    }

}
