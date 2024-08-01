package com.cellbeans.hspa.advancesearch;

import com.cellbeans.hspa.mstvisit.MstVisit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class AdvanceSearchDaoImpl implements AdvanceSearchDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<?> findDetails(AdvanceSearch objAdvanceSearch) {
        String Query = "";
        if (objAdvanceSearch.getType().equalsIgnoreCase("opd")) {
            if (objAdvanceSearch.getPatientMrNo() == null) {
                objAdvanceSearch.setPatientMrNo("");
            }
            Query = "select p from MstVisit p where p.visitPatientId.patientMrNo like '%" + objAdvanceSearch.getPatientMrNo() + "%'";
            if (objAdvanceSearch.getUserFirstname() != null && objAdvanceSearch.getUserFirstname() != "") {
                Query += " and (p.visitPatientId.patientUserId.userFirstname='" + objAdvanceSearch.getUserFirstname() + "' or p.visitPatientId.patientUserId.userMiddlename='" + objAdvanceSearch.getUserFirstname() + "' or  p.visitPatientId.patientUserId.userLastname='" + objAdvanceSearch.getUserFirstname() + "') ";
            } else if (objAdvanceSearch.getUserMobile() != null && objAdvanceSearch.getUserMobile() != "") {
                Query += " and p.visitPatientId.patientUserId.userMobile='" + objAdvanceSearch.getUserMobile() + "' ";
            } else if (objAdvanceSearch.getUserEmail() != null && objAdvanceSearch.getUserEmail() != "") {
                Query += " and p.visitPatientId.patientUserId.userEmail='" + objAdvanceSearch.getUserEmail() + "' ";
            } else if (objAdvanceSearch.getGenderId() != 0) {
                Query += " and p.visitPatientId.patientUserId.userGenderId.genderId=" + objAdvanceSearch.getGenderId() + " ";
            } else if (objAdvanceSearch.getUserResidencePhone() != null && objAdvanceSearch.getUserResidencePhone() != "") {
                Query += " and p.visitPatientId.patientUserId.userResidencePhone='" + objAdvanceSearch.getUserResidencePhone() + "' ";
            } else if (objAdvanceSearch.getNationalityId() != 0) {
                Query += " and p.visitPatientId.patientUserId.userNationalityId.nationalityId='" + objAdvanceSearch.getNationalityId() + "' ";
            } else if (objAdvanceSearch.getUserUid() != null && objAdvanceSearch.getUserUid() != "") {
                Query += " and p.visitPatientId.patientUserId.userUid='" + objAdvanceSearch.getUserUid() + "' ";
            } else if (objAdvanceSearch.getBloodgroupId() != 0) {
                Query += " and p.visitPatientId.patientUserId.userBloodgroupId.bloodgroupId=" + objAdvanceSearch.getBloodgroupId() + " ";
            } else if (objAdvanceSearch.getCityId() != 0) {
                Query += " and p.visitPatientId.patientUserId.userCityId.cityId=" + objAdvanceSearch.getCityId() + " ";
            } else if (objAdvanceSearch.getStateId() != 0) {
                Query += " and p.visitPatientId.patientUserId.userCityId.cityStateId.stateId=" + objAdvanceSearch.getStateId() + " ";
            } else if (objAdvanceSearch.getCountryId() != 0) {
                Query += " and p.visitPatientId.patientUserId.userCityId.cityStateId.stateCountryId.countryId=" + objAdvanceSearch.getCityId() + " ";
            } else if (objAdvanceSearch.getUserDobFrom() != null && objAdvanceSearch.getUserDobFrom() != "") {
                String newDobFrom = objAdvanceSearch.getUserDobFrom().replace('-', '/');
                if (objAdvanceSearch.getUserDobTo() != null && objAdvanceSearch.getUserDobTo() != "") {
                    String newTo = objAdvanceSearch.getUserDobTo().replace('-', '/');
                    Query += " and (p.visitPatientId.patientUserId.userDob between '" + newDobFrom + "' and '" + newTo + "') ";
                } else {
                    Query += " and (p.visitPatientId.patientUserId.userDob between '" + newDobFrom + "' and 'CURRENTDATE') ";
                }
            } else if (objAdvanceSearch.getReId() != 0) {
                Query += " and p.referBy.reId=" + objAdvanceSearch.getReId() + " ";
            } else if (objAdvanceSearch.getReName() != null && objAdvanceSearch.getReName() != "") {
                Query += " and p.referBy.reName='" + objAdvanceSearch.getReName() + "' ";
            } else if (objAdvanceSearch.getRegistrationDateFrom() != null && objAdvanceSearch.getRegistrationDateFrom() != "") {
                if (objAdvanceSearch.getRegistrationDateTo() != null && objAdvanceSearch.getRegistrationDateTo() != "") {
                    Query += " and (p.createdDate between '" + objAdvanceSearch.getRegistrationDateFrom() + "' and '" + objAdvanceSearch.getRegistrationDateFrom() + "') ";
                } else {
                    Query += " and (p.createdDate between '" + objAdvanceSearch.getRegistrationDateFrom() + "' and 'CURRENTDATE') ";
                }
            } else if (objAdvanceSearch.getVisitDateFrom() != null) {
                if (objAdvanceSearch.getVisitDateTo() != null) {
                    Query += " and (p.visitDate between '" + new SimpleDateFormat("yyyy-MM-dd").format(objAdvanceSearch.getVisitDateFrom()) + "' and '" + new SimpleDateFormat("yyyy-MM-dd").format(objAdvanceSearch.getVisitDateTo()) + "') ";
                } else {
                    Query += " and (p.visitDate between '" + new SimpleDateFormat("yyyy-MM-dd").format(objAdvanceSearch.getVisitDateFrom()) + "' and 'CURRENTDATE') ";
                }
            }

        } else {
            if (objAdvanceSearch.getPatientMrNo() == null) {
                objAdvanceSearch.setPatientMrNo("");
            }
            Query = "select p from TrnAdmission p where p.admissionPatientId.patientMrNo like '%" + objAdvanceSearch.getPatientMrNo() + "%'";
            if (objAdvanceSearch.getUserFirstname() != null && objAdvanceSearch.getUserFirstname() != "") {
                Query += " and (p.admissionPatientId.patientUserId.userFirstname='" + objAdvanceSearch.getUserFirstname() + "' or p.admissionPatientId.patientUserId.userMiddlename='" + objAdvanceSearch.getUserFirstname() + "' or  p.admissionPatientId.patientUserId.userLastname='" + objAdvanceSearch.getUserFirstname() + "') ";
            } else if (objAdvanceSearch.getUserMobile() != null && objAdvanceSearch.getUserMobile() != "") {
                Query += " and p.admissionPatientId.patientUserId.userMobile='" + objAdvanceSearch.getUserMobile() + "' ";
            } else if (objAdvanceSearch.getUserEmail() != null && objAdvanceSearch.getUserEmail() != "") {
                Query += " and p.admissionPatientId.patientUserId.userEmail='" + objAdvanceSearch.getUserEmail() + "' ";
            } else if (objAdvanceSearch.getGenderId() != 0) {
                Query += " and p.admissionPatientId.patientUserId.userGenderId.genderId=" + objAdvanceSearch.getGenderId() + " ";
            } else if (objAdvanceSearch.getUserResidencePhone() != null && objAdvanceSearch.getUserResidencePhone() != "") {
                Query += " and p.admissionPatientId.patientUserId.userResidencePhone='" + objAdvanceSearch.getUserResidencePhone() + "' ";
            } else if (objAdvanceSearch.getNationalityId() != 0) {
                Query += " and p.admissionPatientId.patientUserId.userNationalityId.nationalityId='" + objAdvanceSearch.getNationalityId() + "' ";
            } else if (objAdvanceSearch.getUserUid() != null && objAdvanceSearch.getUserUid() != "") {
                Query += " and p.admissionPatientId.patientUserId.userUid='" + objAdvanceSearch.getUserUid() + "' ";
            } else if (objAdvanceSearch.getBloodgroupId() != 0) {
                Query += " and p.admissionPatientId.patientUserId.userBloodgroupId.bloodgroupId=" + objAdvanceSearch.getBloodgroupId() + " ";
            } else if (objAdvanceSearch.getCityId() != 0) {
                Query += " and p.admissionPatientId.patientUserId.userCityId.cityId=" + objAdvanceSearch.getCityId() + " ";
            } else if (objAdvanceSearch.getStateId() != 0) {
                Query += " and p.admissionPatientId.patientUserId.userCityId.cityStateId.stateId=" + objAdvanceSearch.getStateId() + " ";
            } else if (objAdvanceSearch.getCountryId() != 0) {
                Query += " and p.admissionPatientId.patientUserId.userCityId.cityStateId.stateCountryId.countryId=" + objAdvanceSearch.getCityId() + " ";
            } else if (objAdvanceSearch.getUserDobFrom() != null && objAdvanceSearch.getUserDobFrom() != "") {
                String newDobFrom = objAdvanceSearch.getUserDobFrom().replace('-', '/');
                if (objAdvanceSearch.getUserDobTo() != null && objAdvanceSearch.getUserDobTo() != "") {
                    String newDobTo = objAdvanceSearch.getUserDobTo().replace('-', '/');
                    Query += " and (p.admissionPatientId.patientUserId.userDob between '" + newDobFrom + "' and '" + newDobTo + "') ";
                } else {
                    Query += " and (p.admissionPatientId.patientUserId.userDob between '" + newDobFrom + "' and 'CURRENTDATE') ";
                }
            } else if (objAdvanceSearch.getReId() != 0) {
                Query += " and p.admissionReId.reId=" + objAdvanceSearch.getReId() + " ";
            } else if (objAdvanceSearch.getReName() != null && objAdvanceSearch.getReName() != "") {
                Query += " and p.admissionReId.reName='" + objAdvanceSearch.getReName() + "' ";
            } else if (objAdvanceSearch.getRegistrationDateFrom() != null && objAdvanceSearch.getRegistrationDateFrom() != "") {
                if (objAdvanceSearch.getRegistrationDateTo() != null && objAdvanceSearch.getRegistrationDateTo() != "") {
                    Query += " and (p.createdDate between '" + objAdvanceSearch.getRegistrationDateFrom() + "' and '" + objAdvanceSearch.getRegistrationDateFrom() + "') ";
                } else {
                    Query += " and (p.createdDate between '" + objAdvanceSearch.getRegistrationDateFrom() + "' and 'CURRENTDATE') ";
                }
            } else if (objAdvanceSearch.getAdmissionDateFrom() != null) {
                if (objAdvanceSearch.getAdmissionDateTo() != null) {
                    Query += " and (p.admissionDate between '" + new SimpleDateFormat("yyyy-MM-dd").format(objAdvanceSearch.getAdmissionDateFrom()) + "' and '" + new SimpleDateFormat("yyyy-MM-dd").format(objAdvanceSearch.getAdmissionDateTo()) + "') ";
                } else {
                    Query += " and (p.admissionDate between '" + new SimpleDateFormat("yyyy-MM-dd").format(objAdvanceSearch.getAdmissionDateFrom()) + "' and 'CURRENTDATE') ";
                }
            }
        }
        // System.out.println(Query);
        List<?> listMstPatient = entityManager.createQuery(Query).getResultList();
        return listMstPatient;
    }

    @Override
    public List<MstVisit> findAllByPatientId(long patientId) {
        String Query = "select p from MstVisit p where p.visitPatientId.patientId=" + patientId + "";
        List<MstVisit> listMstVisit = entityManager.createQuery(Query, MstVisit.class).getResultList();
        return listMstVisit;
    }

}
