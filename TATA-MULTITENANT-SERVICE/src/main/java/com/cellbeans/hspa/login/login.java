package com.cellbeans.hspa.login;

public class login {
    String userName;
    String password;
    long unitId;
    long cashCounterId;
    String unitName;
    String ipAddress;
    long orgId;
    String orgName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUnitId() {
        return unitId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getCashCounterId() {
        return cashCounterId;
    }

    public void setCashCounterId(long cashCounterId) {
        this.cashCounterId = cashCounterId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "login{" + "userName='" + userName + '\'' + ", password='" + password + '\'' + ", unitId=" + unitId
                + ", cashCounterId=" + cashCounterId + '}';
    }

}
