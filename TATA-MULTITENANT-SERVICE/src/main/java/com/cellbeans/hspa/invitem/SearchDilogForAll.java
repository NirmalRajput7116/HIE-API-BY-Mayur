package com.cellbeans.hspa.invitem;

import com.cellbeans.hspa.invitemcategory.InvItemCategory;
import com.cellbeans.hspa.invitemgroup.InvItemGroup;

/**
 * @author Inya Gaikwad
 * This Class is responsible for Search this is model is used to pass data as well as catch data at the  time of search.
 * <p>
 * Class has
 * {@link javax.persistence.ManyToOne} with  {@link InvItemCategory},{@link InvItemGroup}
 */

public class SearchDilogForAll {
    private String dilogItemName;
    private String dilogBrandName;
    private String dilogItemCode;
    private String dilogStoreId;
    private InvItemCategory dilogIcId;
    private InvItemGroup dilogGroupId;
    private int offSet;
    private int limit;
    //private long unitId;
//    public long getUnitId() {
//        return unitId;
//    }
//
//    public void setUnitId(long unitId) {
//        this.unitId = unitId;
//    }

    public String getDilogItemName() {
        return dilogItemName;
    }

    public void setDilogItemName(String dilogItemName) {
        this.dilogItemName = dilogItemName;
    }

    public String getDilogBrandName() {
        return dilogBrandName;
    }

    public void setDilogBrandName(String dilogBrandName) {
        this.dilogBrandName = dilogBrandName;
    }

    public String getDilogItemCode() {
        return dilogItemCode;
    }

    public void setDilogItemCode(String dilogItemCode) {
        this.dilogItemCode = dilogItemCode;
    }

    public InvItemCategory getDilogIcId() {
        return dilogIcId;
    }

    public void setDilogIcId(InvItemCategory dilogIcId) {
        this.dilogIcId = dilogIcId;
    }

    public InvItemGroup getDilogGroupId() {
        return dilogGroupId;
    }

    public void setDilogGroupId(InvItemGroup dilogGroupId) {
        this.dilogGroupId = dilogGroupId;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDilogStoreId() {
        return dilogStoreId;
    }

    public void setDilogStoreId(String dilogStoreId) {
        this.dilogStoreId = dilogStoreId;
    }
}
