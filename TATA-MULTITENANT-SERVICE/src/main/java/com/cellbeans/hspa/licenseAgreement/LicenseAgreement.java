package com.cellbeans.hspa.licenseAgreement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_temp")
public class LicenseAgreement {

    private static final long serialVersionUID = 1L;
    @JsonInclude(NON_NULL)
    @Column(name = "date_added")
    String date_added;
    @JsonInclude(NON_NULL)
    @Column(name = "date_update")
    String date_update;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_id")
    String licence_id;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_client_id")
    String licence_client_id;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_partner_id")
    String licence_partner_id;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_oem_id")
    String licence_oem_id;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_project_id")
    String licence_project_id;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_product_id")
    String licence_product_id;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_copyright")
    String licence_copyright;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_purchase_order_date")
    String licence_purchase_order_date;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_valid_upto")
    String licence_valid_upto;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_no")
    String licence_no;
    @JsonInclude(NON_NULL)
    @Column(name = "licence_login_bg")
    String licence_login_bg;
    @JsonIgnore
    @Column(name = "is_deleted")
    String is_deleted;
    @JsonIgnore
    @Column(name = "is_active")
    String is_active;
    @JsonIgnore
    @Column(name = "timestamp")
    String timestamp;
    @JsonIgnore
    @Column(name = "created_by")
    String created_by;
    @JsonIgnore
    @Column(name = "updated_by")
    String updated_by;
    @JsonInclude(NON_NULL)
    @Column(name = "client_id")
    String client_id;
    @JsonInclude(NON_NULL)
    @Column(name = "client_name")
    String client_name;
    @JsonInclude(NON_NULL)
    @Column(name = "client_address_line1")
    String client_address_line1;
    @JsonInclude(NON_NULL)
    @Column(name = "client_address_line2")
    String client_address_line2;
    @JsonInclude(NON_NULL)
    @Column(name = "client_phone")
    String client_phone;
    @JsonInclude(NON_NULL)
    @Column(name = "client_mobile")
    String client_mobile;
    @JsonInclude(NON_NULL)
    @Column(name = "client_email")
    String client_email;
    @JsonInclude(NON_NULL)
    @Column(name = "client_website")
    String client_website;
    @JsonInclude(NON_NULL)
    @Column(name = "client_logo")
    String client_logo;
    @JsonInclude(NON_NULL)
    @Column(name = "client_since")
    String client_since;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_id")
    String partner_id;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_name")
    String partner_name;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_address_line1")
    String partner_address_line1;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_address_line2")
    String partner_address_line2;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_phone")
    String partner_phone;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_mobile")
    String partner_mobile;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_email")
    String partner_email;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_website")
    String partner_website;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_logo")
    String partner_logo;
    @JsonInclude(NON_NULL)
    @Column(name = "partner_since")
    String partner_since;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_id")
    String oem_id;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_name")
    String oem_name;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_address_line1")
    String oem_address_line1;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_address_line2")
    String oem_address_line2;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_phone")
    String oem_phone;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_mobile")
    String oem_mobile;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_email")
    String oem_email;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_website")
    String oem_website;
    @JsonInclude(NON_NULL)
    @Column(name = "oem_logo")
    String oem_logo;
    @JsonInclude(NON_NULL)
    @Column(name = "project_id")
    String project_id;
    @JsonInclude(NON_NULL)
    @Column(name = "project_title")
    String project_title;
    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "project_description", length = 512)
    String project_description;
    @JsonInclude(NON_NULL)
    @Column(name = "project_logo")
    String project_logo;
    @JsonInclude(NON_NULL)
    @Column(name = "product_id")
    String product_id;
    @JsonInclude(NON_NULL)
    @Column(name = "product_title")
    String product_title;
    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "product_description", length = 512)
    String product_description;
    @JsonInclude(NON_NULL)
    @Column(name = "product_logo")
    String product_logo;
    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "product_features", length = 512)
    String product_features;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "la_id", unique = true, nullable = true)
    private long la_id;

    public long getLa_id() {
        return la_id;
    }

    public void setLa_id(long la_id) {
        this.la_id = la_id;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getDate_update() {
        return date_update;
    }

    public void setDate_update(String date_update) {
        this.date_update = date_update;
    }

    public String getLicence_id() {
        return licence_id;
    }

    public void setLicence_id(String licence_id) {
        this.licence_id = licence_id;
    }

    public String getLicence_client_id() {
        return licence_client_id;
    }

    public void setLicence_client_id(String licence_client_id) {
        this.licence_client_id = licence_client_id;
    }

    public String getLicence_partner_id() {
        return licence_partner_id;
    }

    public void setLicence_partner_id(String licence_partner_id) {
        this.licence_partner_id = licence_partner_id;
    }

    public String getLicence_oem_id() {
        return licence_oem_id;
    }

    public void setLicence_oem_id(String licence_oem_id) {
        this.licence_oem_id = licence_oem_id;
    }

    public String getLicence_project_id() {
        return licence_project_id;
    }

    public void setLicence_project_id(String licence_project_id) {
        this.licence_project_id = licence_project_id;
    }

    public String getLicence_product_id() {
        return licence_product_id;
    }

    public void setLicence_product_id(String licence_product_id) {
        this.licence_product_id = licence_product_id;
    }

    public String getLicence_copyright() {
        return licence_copyright;
    }

    public void setLicence_copyright(String licence_copyright) {
        this.licence_copyright = licence_copyright;
    }

    public String getLicence_purchase_order_date() {
        return licence_purchase_order_date;
    }

    public void setLicence_purchase_order_date(String licence_purchase_order_date) {
        this.licence_purchase_order_date = licence_purchase_order_date;
    }

    public String getLicence_valid_upto() {
        return licence_valid_upto;
    }

    public void setLicence_valid_upto(String licence_valid_upto) {
        this.licence_valid_upto = licence_valid_upto;
    }

    public String getLicence_no() {
        return licence_no;
    }

    public void setLicence_no(String licence_no) {
        this.licence_no = licence_no;
    }

    public String getLicence_login_bg() {
        return licence_login_bg;
    }

    public void setLicence_login_bg(String licence_login_bg) {
        this.licence_login_bg = licence_login_bg;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_address_line1() {
        return client_address_line1;
    }

    public void setClient_address_line1(String client_address_line1) {
        this.client_address_line1 = client_address_line1;
    }

    public String getClient_address_line2() {
        return client_address_line2;
    }

    public void setClient_address_line2(String client_address_line2) {
        this.client_address_line2 = client_address_line2;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_mobile() {
        return client_mobile;
    }

    public void setClient_mobile(String client_mobile) {
        this.client_mobile = client_mobile;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_website() {
        return client_website;
    }

    public void setClient_website(String client_website) {
        this.client_website = client_website;
    }

    public String getClient_logo() {
        return client_logo;
    }

    public void setClient_logo(String client_logo) {
        this.client_logo = client_logo;
    }

    public String getClient_since() {
        return client_since;
    }

    public void setClient_since(String client_since) {
        this.client_since = client_since;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_address_line1() {
        return partner_address_line1;
    }

    public void setPartner_address_line1(String partner_address_line1) {
        this.partner_address_line1 = partner_address_line1;
    }

    public String getPartner_address_line2() {
        return partner_address_line2;
    }

    public void setPartner_address_line2(String partner_address_line2) {
        this.partner_address_line2 = partner_address_line2;
    }

    public String getPartner_phone() {
        return partner_phone;
    }

    public void setPartner_phone(String partner_phone) {
        this.partner_phone = partner_phone;
    }

    public String getPartner_mobile() {
        return partner_mobile;
    }

    public void setPartner_mobile(String partner_mobile) {
        this.partner_mobile = partner_mobile;
    }

    public String getPartner_email() {
        return partner_email;
    }

    public void setPartner_email(String partner_email) {
        this.partner_email = partner_email;
    }

    public String getPartner_website() {
        return partner_website;
    }

    public void setPartner_website(String partner_website) {
        this.partner_website = partner_website;
    }

    public String getPartner_logo() {
        return partner_logo;
    }

    public void setPartner_logo(String partner_logo) {
        this.partner_logo = partner_logo;
    }

    public String getPartner_since() {
        return partner_since;
    }

    public void setPartner_since(String partner_since) {
        this.partner_since = partner_since;
    }

    public String getOem_id() {
        return oem_id;
    }

    public void setOem_id(String oem_id) {
        this.oem_id = oem_id;
    }

    public String getOem_name() {
        return oem_name;
    }

    public void setOem_name(String oem_name) {
        this.oem_name = oem_name;
    }

    public String getOem_address_line1() {
        return oem_address_line1;
    }

    public void setOem_address_line1(String oem_address_line1) {
        this.oem_address_line1 = oem_address_line1;
    }

    public String getOem_address_line2() {
        return oem_address_line2;
    }

    public void setOem_address_line2(String oem_address_line2) {
        this.oem_address_line2 = oem_address_line2;
    }

    public String getOem_phone() {
        return oem_phone;
    }

    public void setOem_phone(String oem_phone) {
        this.oem_phone = oem_phone;
    }

    public String getOem_mobile() {
        return oem_mobile;
    }

    public void setOem_mobile(String oem_mobile) {
        this.oem_mobile = oem_mobile;
    }

    public String getOem_email() {
        return oem_email;
    }

    public void setOem_email(String oem_email) {
        this.oem_email = oem_email;
    }

    public String getOem_website() {
        return oem_website;
    }

    public void setOem_website(String oem_website) {
        this.oem_website = oem_website;
    }

    public String getOem_logo() {
        return oem_logo;
    }

    public void setOem_logo(String oem_logo) {
        this.oem_logo = oem_logo;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public String getProject_logo() {
        return project_logo;
    }

    public void setProject_logo(String project_logo) {
        this.project_logo = project_logo;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }

    public String getProduct_features() {
        return product_features;
    }

    public void setProduct_features(String product_features) {
        this.product_features = product_features;
    }

    @Override
    public String toString() {
        return "LicenseAgreement{" + "la_id=" + la_id + ", date_added='" + date_added + '\'' + ", date_update='" + date_update + '\'' + ", licence_id='" + licence_id + '\'' + ", licence_client_id='" + licence_client_id + '\'' + ", licence_partner_id='" + licence_partner_id + '\'' + ", licence_oem_id='" + licence_oem_id + '\'' + ", licence_project_id='" + licence_project_id + '\'' + ", licence_product_id='" + licence_product_id + '\'' + ", licence_copyright='" + licence_copyright + '\'' + ", licence_purchase_order_date='" + licence_purchase_order_date + '\'' + ", licence_valid_upto='" + licence_valid_upto + '\'' + ", licence_no='" + licence_no + '\'' + ", licence_login_bg='" + licence_login_bg + '\'' + ", is_deleted='" + is_deleted + '\'' + ", is_active='" + is_active + '\'' + ", timestamp='" + timestamp + '\'' + ", created_by='" + created_by + '\'' + ", updated_by='" + updated_by + '\'' + ", client_id='" + client_id + '\'' + ", client_name='" + client_name + '\'' + ", client_address_line1='" + client_address_line1 + '\'' + ", client_address_line2='" + client_address_line2 + '\'' + ", client_phone='" + client_phone + '\'' + ", client_mobile='" + client_mobile + '\'' + ", client_email='" + client_email + '\'' + ", client_website='" + client_website + '\'' + ", client_logo='" + client_logo + '\'' + ", client_since='" + client_since + '\'' + ", partner_id=" + partner_id + ", partner_name='" + partner_name + '\'' + ", partner_address_line1='" + partner_address_line1 + '\'' + ", partner_address_line2='" + partner_address_line2 + '\'' + ", partner_phone='" + partner_phone + '\'' + ", partner_mobile='" + partner_mobile + '\'' + ", partner_email='" + partner_email + '\'' + ", partner_website='" + partner_website + '\'' + ", partner_logo='" + partner_logo + '\'' + ", partner_since='" + partner_since + '\'' + ", oem_id='" + oem_id + '\'' + ", oem_name='" + oem_name + '\'' + ", oem_address_line1='" + oem_address_line1 + '\'' + ", oem_address_line2='" + oem_address_line2 + '\'' + ", oem_phone='" + oem_phone + '\'' + ", oem_mobile='" + oem_mobile + '\'' + ", oem_email='" + oem_email + '\'' + ", oem_website='" + oem_website + '\'' + ", oem_logo='" + oem_logo + '\'' + ", project_id='" + project_id + '\'' + ", project_title='" + project_title + '\'' + ", project_description='" + project_description + '\'' + ", project_logo='" + project_logo + '\'' + ", product_id='" + product_id + '\'' + ", product_title='" + product_title + '\'' + ", product_description='" + product_description + '\'' + ", product_logo='" + product_logo + '\'' + ", product_features='" + product_features + '\'' + '}';
    }

}
