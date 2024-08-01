package com.cellbeans.hspa.tinvpurchasequotation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchaseQuotationRepository extends JpaRepository<TinvPurchaseQuotation, Long> {

    Page<TinvPurchaseQuotation> findByPqIdAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPurchaseQuotation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchaseQuotation> findByPqNameContains(String key);

    List<TinvPurchaseQuotation> findByPqPieIdPieIdAndPqIsApproveTrueAndIsActiveTrueAndIsDeletedFalse(Long key);

   /* TinvPurchaseQuotation findTopByOrderByPqIdDesc();
    default String makeEQNumber(String storeName)
    {
        TinvPurchaseQuotation tinvPurchaseQuotation = findTopByOrderByPqIdDesc();
        StringBuilder eqNumber = new StringBuilder("EQ/");

        try
        {
            if(tinvPurchaseQuotation.get != null)
            {
                String  number = lastPurchaseItemEnquiry.getPieEnquiryNo().substring(lastPurchaseItemEnquiry.getPieEnquiryNo().lastIndexOf("/")+1);
                int newNumber = Integer.parseInt(number);
                eqNumber.append(storeName).append("/"+(newNumber+1));
            }
            else
            {
                eqNumber.append(storeName).append("/1");
            }
        }
        catch (Exception e)
        {
            // System.out.println(e.getMessage());
            eqNumber.append(storeName).append("/1");
        }
        return eqNumber.toString();
    }
*/

}
            
