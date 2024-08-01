package com.cellbeans.hspa.tbillTariffReceipt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface TbillTariffReceiptRepository extends JpaRepository<TbillTariffReceipt, Long> {

    TbillTariffReceipt findTopByOrderByTrId();

    default String makeRecieptNumber() {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        TbillTariffReceipt lastReciept = findTopByOrderByTrId();
        StringBuilder receiptNumber = new StringBuilder("RECPT/");
        try {
            if (lastReciept.getTrReceiptNumber() != null) {
                String number = lastReciept.getTrReceiptNumber().substring(17);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%08d", newNumber + 1);
                receiptNumber.append(dateFormat.format(todayDate)).append("/" + incNumber);
            } else {
                receiptNumber.append(dateFormat.format(todayDate)).append("/00000001");
            }
        } catch (Exception exception) {
            receiptNumber.append(dateFormat.format(todayDate)).append("/00000001");
            // System.out.println("Error occurred BETWEEN LINE: 31 to 37");
        }
        return receiptNumber.toString();
    }

}
