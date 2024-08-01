package com.cellbeans.hspa.forgotpassword;

public class ConfirmationBean {

    String old_pass;
    String new_pass;
    String conf_pass;
    String username;

    public ConfirmationBean() {
    }

    public ConfirmationBean(String old_pass, String new_pass, String conf_pass, String username) {
        this.old_pass = old_pass;
        this.new_pass = new_pass;
        this.conf_pass = conf_pass;
        this.username = username;
    }

    public String getOld_pass() {
        return old_pass;
    }

    public void setOld_pass(String old_pass) {
        this.old_pass = old_pass;
    }

    public String getNew_pass() {
        return new_pass;
    }

    public void setNew_pass(String new_pass) {
        this.new_pass = new_pass;
    }

    public String getConf_pass() {
        return conf_pass;
    }

    public void setConf_pass(String conf_pass) {
        this.conf_pass = conf_pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
