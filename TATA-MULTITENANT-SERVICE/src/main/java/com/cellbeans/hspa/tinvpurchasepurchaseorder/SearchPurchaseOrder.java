package com.cellbeans.hspa.tinvpurchasepurchaseorder;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invsupplier.InvSupplier;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;

public class SearchPurchaseOrder {
    private int offset;
    private int limit;
    private String searchFromDate;
    private String searchToDate;
    private String searchPieEnquiryNo;
    private InvSupplier searchSupplierId;
    private String searchQuotationRefNo;
    private InvStore searchStoreId;
    private MstPaymentMode searchPaymentMode;
    private long poId;
    private long unitId;

    public SearchPurchaseOrder(String searchPieEnquiryNo, InvSupplier searchSupplierId, String searchQuotationRefNo, InvStore searchStoreId, long poId) {
        this.searchPieEnquiryNo = searchPieEnquiryNo;
        this.searchSupplierId = searchSupplierId;
        this.searchQuotationRefNo = searchQuotationRefNo;
        this.searchStoreId = searchStoreId;
        this.poId = poId;
    }

    public SearchPurchaseOrder() {
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearchFromDate() {
        return searchFromDate;
    }

    public void setSearchFromDate(String searchFromDate) {
        this.searchFromDate = searchFromDate;
    }

    public String getSearchToDate() {
        return searchToDate;
    }

    public void setSearchToDate(String searchToDate) {
        this.searchToDate = searchToDate;
    }

    public String getSearchPieEnquiryNo() {
        return searchPieEnquiryNo;
    }

    public void setSearchPieEnquiryNo(String searchPieEnquiryNo) {
        this.searchPieEnquiryNo = searchPieEnquiryNo;
    }

    public InvStore getSearchStoreId() {
        return searchStoreId;
    }

    public void setSearchStoreId(InvStore searchStoreId) {
        this.searchStoreId = searchStoreId;
    }

    public InvSupplier getSearchSupplierId() {
        return searchSupplierId;
    }

    public void setSearchSupplierId(InvSupplier searchSupplierId) {
        this.searchSupplierId = searchSupplierId;
    }

    public String getSearchQuotationRefNo() {
        return searchQuotationRefNo;
    }

    public void setSearchQuotationRefNo(String searchQuotationRefNo) {
        this.searchQuotationRefNo = searchQuotationRefNo;
    }

    public long getPoId() {
        return poId;
    }

    public void setPoId(long poId) {
        this.poId = poId;
    }

    public MstPaymentMode getSearchPaymentMode() {
        return searchPaymentMode;
    }

    public void setSearchPaymentMode(MstPaymentMode searchPaymentMode) {
        this.searchPaymentMode = searchPaymentMode;
    }

}
