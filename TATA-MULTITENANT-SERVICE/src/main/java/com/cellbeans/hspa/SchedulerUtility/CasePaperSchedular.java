package com.cellbeans.hspa.SchedulerUtility;

import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.temrvisitallergy.TemrVisitAllergy;
import com.cellbeans.hspa.temrvisithistory.TemrVisitHistory;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CasePaperSchedular {

    EntityManager entityManager;

    public Object createCasePaper(EntityManager entityManager, String strDate) {
        this.entityManager = entityManager;
        Object emailMsg = "";
//       String query = "SELECT CONCAT(mu.user_firstname ,  ' ' , mu.user_lastname) AS patient_name, mp.patient_id,tt.timeline_id,mp.patient_mr_no,mu.user_age,mu.user_mobile,(SELECT CONCAT(mu.user_firstname , ' ' , mu.user_lastname) AS staff_name FROM mst_staff ms INNER JOIN mst_user mu WHERE mu.user_id=ms.staff_user_id AND ms.staff_id=tt.timelineemrfinal_staff_id) AS staff_name,mg.gender_name FROM temr_timeline tt INNER JOIN mst_visit mv INNER JOIN mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_gender mg WHERE   " +
//               "cast(tt.timeline_finalized_date AS date)=cast(NOW() AS DATE) AND mp.patient_id=mv.visit_patient_id AND tt.timeline_visit_id=mv.visit_id AND mu.user_id=mp.patient_user_id AND mg.gender_id=mu.user_gender_id";
        String query = "SELECT CONCAT(mu.user_firstname ,  ' ' , mu.user_lastname) AS patient_name, mp.patient_id,tt.timeline_id,mp.patient_mr_no,mu.user_age,mu.user_mobile,(SELECT CONCAT(mu.user_firstname , ' ' , mu.user_lastname) AS staff_name FROM mst_staff ms INNER JOIN mst_user mu WHERE mu.user_id=ms.staff_user_id AND ms.staff_id=tt.timelineemrfinal_staff_id) AS staff_name,mg.gender_name FROM temr_timeline tt INNER JOIN mst_visit mv INNER JOIN mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_gender mg WHERE   " +
                "Date(tt.timeline_finalized_date)=" + strDate + " AND mp.patient_id=mv.visit_patient_id AND tt.timeline_visit_id=mv.visit_id AND mu.user_id=mp.patient_user_id AND mg.gender_id=mu.user_gender_id";
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + "<b>" + tList.get(i)[0] + " " + tList.get(i)[4] + "/" + tList.get(i)[7] + " (Mob No :" + tList.get(i)[5];
            emailMsg = emailMsg + ") ) OPDNo :" + tList.get(i)[3] + " Walkin/Centre Consultation - Dr. " + tList.get(i)[6] + "</b>";
            emailMsg = emailMsg + "" + getPatientHistorey(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getPatientChiefComplaint(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getPatientSymptom(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getPatientAllegy(tList.get(i)[1]);
            emailMsg = emailMsg + "" + getPatientNonDrugAllegy(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getPatientVital(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getDoctorAdvice(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getClinicalFinding(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getInvestigation(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getPrescription(tList.get(i)[2]);
            emailMsg = emailMsg + "" + getDoctorNote(tList.get(i)[2]);

        }
        return emailMsg;
    }

    private Object getPatientHistorey(Object timeLineId) {
        Object emailMsg = "<br> Patient History :- <br>";
        String query = "SELECT th FROM TemrVisitHistory th  WHERE th.vhTimelineId.timelineId=" + timeLineId;
        List<TemrVisitHistory> tList = entityManager.createQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + "" + tList.get(i).getVhContent();
        }
        return emailMsg;
    }

    private Object getPatientChiefComplaint(Object timeLineId) {
        Object emailMsg = "<br> <b>Patient Chief Complaints :-</b> <br>";
        String query = "SELECT mc.cc_name,tc.vcc_cc_no,tc.vcc_cc_unit FROM temr_visit_chief_complaint tc  INNER JOIN memr_chief_complaint mc WHERE tc.vcc_cc_id=mc.cc_id AND tc.vcc_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + "" + tList.get(i)[0] + " , (" + tList.get(i)[1] + tList.get(i)[2] + ") ";
        }
        return emailMsg;

    }

    private Object getPatientSymptom(Object timeLineId) {
        Object emailMsg = "<br> <b>Patient Symptoms :- </b><br>";
        String query = "SELECT ms.symptom_name FROM temr_visit_symptom ts INNER JOIN memr_symptom ms WHERE ms.symptom_id=ts.vs_symptom_id AND ts.vs_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + "" + tList.get(i)[0];
        }
        return emailMsg;
    }

    private Object getPatientAllegy(Object patientId) {
        Object emailMsg = "<br> <b>Patient Allergy :- </b><br>";
        String query = "SELECT ta FROM TemrVisitAllergy ta  WHERE ta.vaPatientId.patientId=" + patientId;
        List<TemrVisitAllergy> tList = entityManager.createQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + "" + tList.get(i).getVaCompoundId().getCompoundName();
        }
        return emailMsg;
    }

    private Object getPatientNonDrugAllegy(Object timeLineId) {
        Object emailMsg = "<br> <b>Patient Non Drug Allergy :- </b><br>";
        String query = "  SELECT md.nda_name,ms.as_name FROM temr_visit_non_drug_allergy nd INNER JOIN mst_non_drug_allergy md INNER JOIN memr_allergy_sensitivity ms WHERE md.nda_id=nd.vnda_nda_id AND ms.as_id=nd.vnda_as_id  " +
                "AND nd.vn_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + "" + tList.get(i)[0] + " ( " + tList.get(i)[0] + " )";
        }
        return emailMsg;
    }

    private Object getPatientVital(Object timeLineId) {
        Object emailMsg = "<br> <b>Patient Vitals :- </b><br>";
        String query = " SELECT tv.created_date,tv.vital_height,tv.vital_weight,tv.vital_bmi,tv.vital_body_temp,tv.vital_pulse,tv.vital_respiration_rate,tv.vital_sys_bp,tv.vital_dia_bp,tv.vital_map,tv.vital_spo2,tv.vital_blood_glucose,tv.vital_himoglobin,tv.vital_fat,tv.vital_muscle_mass,tv.vital_remark FROM temr_vital tv WHERE tv.vital_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + " Date & Time : " + tList.get(i)[0] + "<br>";
            emailMsg = emailMsg + "Height : " + tList.get(i)[1] + "<br>";
            emailMsg = emailMsg + "Weight : " + tList.get(i)[2] + "<br>";
            emailMsg = emailMsg + " BMI : " + tList.get(i)[3] + "<br>";
            emailMsg = emailMsg + " Body Temp : " + tList.get(i)[4] + "<br>";
            emailMsg = emailMsg + " Pulse : " + tList.get(i)[5] + "<br>";
            emailMsg = emailMsg + " Respiration Rate : " + tList.get(i)[6] + "<br>";
            emailMsg = emailMsg + " Systolic Blood Pressure : " + tList.get(i)[7] + "<br>";
            emailMsg = emailMsg + " Diastolic Blood Pressure : " + tList.get(i)[8] + "<br>";
            emailMsg = emailMsg + " Vital Map : " + tList.get(i)[9] + "<br>";
            emailMsg = emailMsg + " SPo2 : " + tList.get(i)[10] + "<br>";
            emailMsg = emailMsg + " Blood Glucose : " + tList.get(i)[11] + "<br>";
            emailMsg = emailMsg + "Hemoglobin : " + tList.get(i)[12] + "<br>";
            emailMsg = emailMsg + "Vital Fat : " + tList.get(i)[13] + "<br>";
            emailMsg = emailMsg + "Muscle Mass: " + tList.get(i)[14] + "<br>";
            emailMsg = emailMsg + "Remark : " + tList.get(i)[15] + "<br>";
        }
        return emailMsg;
    }

    private Object getDoctorAdvice(Object timeLineId) {
        Object emailMsg = "<br> <b>Doctor Advice :- </b><br>";
        String query = "SELECT mp.procedure_name,ms.speciality_name,ta.dc_content FROM temr_doctors_advice ta INNER JOIN mst_procedure mp INNER JOIN mst_speciality ms WHERE mp.procedure_id=ta.dc_procedure_id AND ms.speciality_id=mp.procedure_speiclity_id AND ta.dc_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + " Procedure : " + tList.get(i)[0] + "<br>";
            emailMsg = emailMsg + "Speciality : " + tList.get(i)[1] + "<br>";
            emailMsg = emailMsg + "Content : " + tList.get(i)[2] + "<br>";
        }
        return emailMsg;
    }

    private Object getClinicalFinding(Object timeLineId) {
        Object emailMsg = "<br> <b>Clinical Finding :- </b><br>";
        String query = "SELECT ms.speciality_name,tf.cf_name FROM temr_clinical_finding tf INNER JOIN mst_speciality ms WHERE ms.speciality_id=tf.ss_speciality_id AND tf.cf_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + " Speciality : " + tList.get(i)[0] + "<br>";
            emailMsg = emailMsg + " Name : " + tList.get(i)[1] + "<br>";
        }
        return emailMsg;
    }

    private Object getInvestigation(Object timeLineId) {
        Object emailMsg = "<br> <b>Investigation :-</b> <br>";
        String query = "SELECT ms.service_name,ms.service_code FROM temr_visit_investigation ti INNER JOIN mbill_service ms WHERE ms.service_id=ti.vi_service_id AND ti.vi_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + " Service : " + tList.get(i)[0] + "<br>";
            emailMsg = emailMsg + " Code : " + tList.get(i)[1] + "<br>";
        }
        return emailMsg;
    }

    private Object getPrescription(Object timeLineId) {
        Object emailMsg = "<br> <b>Prescription :- </b><br>";
        String query = "SELECT ii.item_name, ti.ip_quantity,mr.route_name,di.di_name,ti.ipinstruction,ti.ip_dd_id " +
                "FROM temr_item_prescription ti " +
                "INNER JOIN inv_item ii " +
                "left JOIN mst_route mr ON mr.route_id=ii.item_route_id " +
                "INNER JOIN memr_drug_instruction di  " +
                "INNER JOIN temr_visit_prescription tv   " +
                "WHERE ii.item_id=ti.ip_inv_item_id AND di.di_id=ti.ip_di_id AND tv.vp_id=ti.ip_vp_id AND tv.vp_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + " Item : " + tList.get(i)[0] + "<br>";
            emailMsg = emailMsg + " Quantity : " + tList.get(i)[1] + "<br>";
            emailMsg = emailMsg + " Route : " + tList.get(i)[2] + "<br>";
            emailMsg = emailMsg + " Instruction : " + tList.get(i)[3] + "<br>";
            emailMsg = emailMsg + " Duration : " + tList.get(i)[4] + "<br>";
        }
        return emailMsg;
    }

    private Object getDoctorNote(Object timeLineId) {
        Object emailMsg = "<br> <b>Doctor Notes :- </b><br>";
        String query = "SELECT dc.dn_eng_note FROM temr_visit_doctor_note td INNER JOIN memr_doctors_note dc WHERE dc.dn_id=td.vadn_id AND td.va_timeline_id=" + timeLineId;
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < tList.size(); i++) {
            emailMsg = emailMsg + " Note : " + tList.get(i)[0] + "<br><br>";
        }
        emailMsg = emailMsg + "<hr></hr>";
        return emailMsg;
    }

    @org.springframework.transaction.annotation.Transactional
    public void updateAllUserAge(EntityManager entityManager, MstUserRepository mstUserRepository) throws Exception {
        this.entityManager = entityManager;
        String query = "select user_id, user_dob from mst_user where user_dob is not null;";
        List<Object[]> tList = entityManager.createNativeQuery(query).getResultList();
        // System.out.println("List : "+tList);
        for (int i = 0; i < tList.size(); i++) {
            try {
                //direct age calculation
                BigInteger user_id = (BigInteger) tList.get(i)[0];
                //String created_date = (String) tList.get(i)[1];
                String DOB = (String) tList.get(i)[1];
                //System.out.println("DOB : "+DOB);
                if (DOB != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = sdf.parse(DOB);
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH) + 1;
                    int date = c.get(Calendar.DATE);
                    LocalDate l1 = LocalDate.of(year, month, date);
                    LocalDate now1 = LocalDate.now();
                    Period diff1 = Period.between(l1, now1);
                    //System.out.println("age:" + diff1.getYears() + "years" + diff1.getMonths() + "months" + diff1.getDays() + "days");
                    this.update(diff1, user_id, mstUserRepository);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Period diff1, BigInteger user_id, MstUserRepository mstUserRepository) {
        MstUser mstUser = mstUserRepository.findbyUserID(Long.parseLong("" + user_id));
        mstUser.setUserAge("" + diff1.getYears());
        mstUser.setuserMonth("" + diff1.getMonths());
        mstUser.setuserDay("" + diff1.getDays());
        mstUserRepository.save(mstUser);
    }
}
