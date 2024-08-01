package com.cellbeans.hspa.trnadmission;

import com.cellbeans.hspa.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrnAdmissionSearchController {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TrnAdmission> findDetails(@RequestHeader("X-tenantId") String tenantName, TrnAdmissionSearchDTO trnAdmissionSearchDTO) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> list = new ArrayList<TrnAdmission>();
        int offset = trnAdmissionSearchDTO.getSearchAnyLimit() * (trnAdmissionSearchDTO.getSearchAnyPageNo() - 1);
        int limit = trnAdmissionSearchDTO.getSearchAnyLimit();
        System.out.println("-------------------Date--------------------");
        System.out.println(trnAdmissionSearchDTO.getSearchFromDate());
        System.out.println(trnAdmissionSearchDTO.getSearchToDate());
        String Query = "Select t from TrnAdmission t where t.admissionPatientId.patientMrNo like '%" + trnAdmissionSearchDTO.getSearchMrNo() + "%' ";
        if (trnAdmissionSearchDTO.getSearchPatientName().equals("") || trnAdmissionSearchDTO.getSearchPatientName().equals(null)) {
        } else {
            Query += " and (t.admissionPatientId.patientUserId.userFirstname like '%" + trnAdmissionSearchDTO.getSearchPatientName() + "%'  or  t.admissionPatientId.patientUserId.userMiddlename like '%" + trnAdmissionSearchDTO.getSearchPatientName() + "%'   or  t.admissionPatientId.patientUserId.userLastname like '%" + trnAdmissionSearchDTO.getSearchPatientName() + "%')";
        }
        if (trnAdmissionSearchDTO.getSearchGenderId() != 0) {
            Query += " and t.admissionPatientId.patientUserId.userGenderId.genderId = " + trnAdmissionSearchDTO.getSearchGenderId() + " ";
        }
        if (trnAdmissionSearchDTO.getSearchDoctorName().equals("") || trnAdmissionSearchDTO.getSearchDoctorName().equals(null)) {
        } else {
            Query += " and (t.admissionStaffId.staffUserId.userFirstname like '%" + trnAdmissionSearchDTO.getSearchDoctorName() + "%'  or admissionStaffId.staffUserId.userMiddlename like '%" + trnAdmissionSearchDTO.getSearchDoctorName() + "%'   or  admissionStaffId.staffUserId.userLastname like '%" + trnAdmissionSearchDTO.getSearchDoctorName() + "%')";
        }
        if (trnAdmissionSearchDTO.getSearchFromDate().equals(trnAdmissionSearchDTO.getSearchToDate())) {
            Query += " and t.admissionDate = '" + trnAdmissionSearchDTO.getSearchFromDate() + "' ";
        }
        if (!trnAdmissionSearchDTO.getSearchFromDate().equals(trnAdmissionSearchDTO.getSearchToDate())) {
            Query += " and t.admissionDate  BETWEEN '" + trnAdmissionSearchDTO.getSearchFromDate() + "' and '" + trnAdmissionSearchDTO.getSearchToDate() + "' ";
        }
        if ((trnAdmissionSearchDTO.getPrint()).equals(true)) {
            String Query1 = "";
            Query1 = "";
            List<TrnAdmission> trnAdmissionlist = entityManager.createQuery(Query).getResultList();
            return trnAdmissionlist;
        } else {
            //  Query +="  LIMIT " + limit + " OFFSET " + offset;
            System.out.println(Query);
//                      String countQury = Query.replaceFirst(" t ", " count(t.admissionId) ");
//                      System.out.println(countQury);
//                      Long count = (Long) entityManager.createQuery(countQury).getSingleResult();
            try {
                list = entityManager.createQuery(Query, TrnAdmission.class).getResultList();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //  List<TrnAdmission> trnAdmissionlist=entityManager.createQuery(Query).setMaxResults(limit).setFirstResult(offset).getResultList();
            // trnAdmissionlist.setCount
            return list;
        }

    }

    public long findCount(@RequestHeader("X-tenantId") String tenantName, TrnAdmissionSearchDTO trnAdmissionSearchDTO) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = trnAdmissionSearchDTO.getSearchAnyLimit() * (trnAdmissionSearchDTO.getSearchAnyPageNo() - 1);
        int limit = trnAdmissionSearchDTO.getSearchAnyLimit();
        String Query = "Select count(t.admissionId) from TrnAdmission t where t.admissionPatientId.patientMrNo like '%" + trnAdmissionSearchDTO.getSearchMrNo() + "%' ";
        if (trnAdmissionSearchDTO.getSearchPatientName() != "" || trnAdmissionSearchDTO.getSearchPatientName() != null) {
            Query += " and t.admissionPatientId.patientUserId.userFirstname like '%" + trnAdmissionSearchDTO.getSearchPatientName() + "%'  or t.admissionPatientId.patientUserId.userMiddlename like '%" + trnAdmissionSearchDTO.getSearchPatientName() + "%'   or  t.admissionPatientId.patientUserId.userLastname like '%" + trnAdmissionSearchDTO.getSearchPatientName() + "%'";
        }
        if (trnAdmissionSearchDTO.getSearchGenderId() != 0 || trnAdmissionSearchDTO.getSearchGenderId() != null) {
            Query += " and t.admissionPatientId.patientUserId.userGenderId.genderId = " + trnAdmissionSearchDTO.getSearchGenderId() + " ";
        }
        if (trnAdmissionSearchDTO.getSearchDoctorName() != "" || trnAdmissionSearchDTO.getSearchDoctorName() != null) {
            Query += " and t.admissionStaffId.staffUserId.userFirstname like '%" + trnAdmissionSearchDTO.getSearchDoctorName() + "%'  or admissionStaffId.staffUserId.userMiddlename like '%" + trnAdmissionSearchDTO.getSearchDoctorName() + "%'   or  admissionStaffId.staffUserId.userLastname like '%" + trnAdmissionSearchDTO.getSearchDoctorName() + "%'";
        }
        if (trnAdmissionSearchDTO.getSearchFromDate() == trnAdmissionSearchDTO.getSearchToDate()) {
            Query += " and  date(t.admissionDate)  = '" + trnAdmissionSearchDTO.getSearchFromDate() + "' ";
        }
        if (trnAdmissionSearchDTO.getSearchFromDate() != trnAdmissionSearchDTO.getSearchToDate()) {
            Query += " and date(t.admissionDate)   BETWEEN " + trnAdmissionSearchDTO.getSearchFromDate() + " and " + trnAdmissionSearchDTO.getSearchToDate() + " ";
        }
        if ((trnAdmissionSearchDTO.getPrint()).equals(true)) {
            String Query1 = "";
            Query1 = "";
            Long count = (Long) entityManager.createQuery(Query).getSingleResult();
            return count;
        } else {
            //Query +="  LIMIT " + limit + " OFFSET " + offset;
            System.out.println(Query);
//                      String countQury = Query.replaceFirst(" t ", " count(t.admissionId) ");
//                      System.out.println(countQury);
//                      Long count = (Long) entityManager.createQuery(countQury).getSingleResult();
            Long count = (Long) entityManager.createQuery(Query).getSingleResult();
            return count;
        }

    }

}
