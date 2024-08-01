package com.cellbeans.hspa.tnstinpatient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public interface TnstInPatientRepository extends JpaRepository<TnstInPatient, Long> {

    Page<TnstInPatient> findByIpAdmissionIdAdmissionIdAndIsDeletedFalse(Long admissionId, Pageable page);

    Page<TnstInPatient> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TnstInPatient> findByIpAdmissionIdAdmissionIdAndIpConfirmTrueAndIsDeletedFalse(Long admissionId, Pageable page);

    Page<TnstInPatient> findByIpAdmissionIdAdmissionIdAndIpStatusEqualsOrIpStatusEqualsOrIpStatusEqualsAndIsDeletedFalse(Long admissionId, int status1, int status2, int status3, Pageable page);

    /*Page<TnstInPatient> findByIssueAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    List<TnstInPatient> findByIssueAdmissionIdContains(String key);*/
    //Author: Mohit

    List<TnstInPatient> findByIpAdmissionIdAdmissionIdAndIsDeletedFalse(Long admissionId);

    default public List<TnstItemIssue> findIssueItemListByAdmissionId(Long admissionId) {
        List<TnstInPatient> inPatients = findByIpAdmissionIdAdmissionIdAndIsDeletedFalse(admissionId);
        List<TnstItemIssue> itemIssues = new ArrayList<>();
        Iterator<TnstInPatient> patientIterator = inPatients.iterator();
        while (patientIterator.hasNext()) {
            /*patientIterator.next().getIpTnstIssueItem().stream().filter(tnstItemIssue -> tnstItemIssue.getBilled().equals(false));*/
            /*itemIssues.addAll(patientIterator.next().getIpTnstIssueItem());*/
            itemIssues.addAll(patientIterator.next().getIpTnstIssueItem().stream().filter(tnstItemIssue -> tnstItemIssue.getBilled().equals(false)).collect(Collectors.toList()));
        }
        // itemIssues.addAll(inPatients.stream().findAny().get().getIpTnstIssueItem());
        return itemIssues;
    }

    //seetanshu for nursing returns
    List<TnstInPatient> findAllByIpAdmissionIdAdmissionUnitIdUnitIdAndIpStatusAndIsActiveTrueAndIsDeletedFalse(long unitId, int ipStatus);

    @Query(value = "select * from tnst_in_patient n where n.ip_status=1 and n.is_active=1 and n.is_deleted=0   \n#pageable\n", countQuery = " select count(*) from tnst_in_patient n where n.ip_status=1 and n.is_active=1 and n.is_deleted=0  ", nativeQuery = true)
    Page<TnstInPatient> findAllNursingIndentRequsets(Pageable page);
}