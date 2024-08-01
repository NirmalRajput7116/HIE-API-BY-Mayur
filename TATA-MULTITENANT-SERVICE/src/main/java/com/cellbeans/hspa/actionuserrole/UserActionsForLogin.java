package com.cellbeans.hspa.actionuserrole;

public class UserActionsForLogin {
    private long aurId;

//    private List<ActionButtonMst> actionButton;

//    private String aurStaffId;


    private String aurStaffName;


//    private String aurRoleId;


    private String aurRoleName;


//    private String aurActionSubModuleId;


    private String aurActionSubModuleName;


//    private String aurActionModuleId;


    private String aurActionModuleName;


    private boolean aurAdd;


    private boolean aurView;


    private boolean aurEdit;


    private boolean aurDelete;


    private boolean aurSearch;


    private boolean aurApprove;


    private Boolean isDeleted = false;


    private Boolean isActive = true;

    public long getAurId() {
        return aurId;
    }

    public void setAurId(long aurId) {
        this.aurId = aurId;
    }

    public String getAurStaffName() {
        return aurStaffName;
    }

    public void setAurStaffName(String aurStaffName) {
        this.aurStaffName = aurStaffName;
    }

    public String getAurRoleName() {
        return aurRoleName;
    }

    public void setAurRoleName(String aurRoleName) {
        this.aurRoleName = aurRoleName;
    }

    public String getAurActionSubModuleName() {
        return aurActionSubModuleName;
    }

    public void setAurActionSubModuleName(String aurActionSubModuleName) {
        this.aurActionSubModuleName = aurActionSubModuleName;
    }

    public String getAurActionModuleName() {
        return aurActionModuleName;
    }

    public void setAurActionModuleName(String aurActionModuleName) {
        this.aurActionModuleName = aurActionModuleName;
    }

    public boolean isAurAdd() {
        return aurAdd;
    }

    public void setAurAdd(boolean aurAdd) {
        this.aurAdd = aurAdd;
    }

    public boolean isAurView() {
        return aurView;
    }

    public void setAurView(boolean aurView) {
        this.aurView = aurView;
    }

    public boolean isAurEdit() {
        return aurEdit;
    }

    public void setAurEdit(boolean aurEdit) {
        this.aurEdit = aurEdit;
    }

    public boolean isAurDelete() {
        return aurDelete;
    }

    public void setAurDelete(boolean aurDelete) {
        this.aurDelete = aurDelete;
    }

    public boolean isAurSearch() {
        return aurSearch;
    }

    public void setAurSearch(boolean aurSearch) {
        this.aurSearch = aurSearch;
    }

    public boolean isAurApprove() {
        return aurApprove;
    }

    public void setAurApprove(boolean aurApprove) {
        this.aurApprove = aurApprove;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
