package com.cellbeans.hspa.mis.misinventoryexpiryreport;

import com.cellbeans.hspa.mstunit.MstUnit;

public class pharmacysalesDTO {
    public String firstname;
    public String lastname;
    public String item_code;
    public String item_name;
    public String consumeQty;
    public String unitName;
    public String storeName;
    public long count;
    public MstUnit headerObject;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public MstUnit getHeaderObject() {
        return headerObject;
    }

    public void setHeaderObject(MstUnit headerObject) {
        this.headerObject = headerObject;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getConsumeQty() {
        return consumeQty;
    }

    public void setConsumeQty(String consumeQty) {
        this.consumeQty = consumeQty;
    }

}
