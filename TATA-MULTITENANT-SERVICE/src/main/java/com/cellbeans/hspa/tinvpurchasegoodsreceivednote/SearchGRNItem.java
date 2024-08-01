package com.cellbeans.hspa.tinvpurchasegoodsreceivednote;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invsupplier.InvSupplier;

public class SearchGRNItem {
    private int offset;
    private int limit;
    private String searchFromDate;
    private String searchToDate;
    private String searchPieEnquiryNo;
    private InvSupplier searchSupplierId;
    private String searchQuotationRefNo;
    private String searchPoNo;
    private InvStore searchStoreId;
    private String searchGrnNo;
    private long unitId;

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

    public String getSearchPoNo() {
        return searchPoNo;
    }

    public void setSearchPoNo(String searchPoNo) {
        this.searchPoNo = searchPoNo;
    }

    public String getSearchGrnNo() {
        return searchGrnNo;
    }

    public void setSearchGrnNo(String searchGrnNo) {
        this.searchGrnNo = searchGrnNo;
    }

}
