package com.cellbeans.hspa.trnsponsor;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.io.Serializable;

//@Entity
@EntityListeners(AuditingEntityListener.class)
//@Table(name = "trn_sponsor_combination")
public class sponsorcombination implements Serializable {
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "scId", unique = true, nullable = true)
//    private long scId;
//	
//	
//    @JoinColumn(name = "ctId")
//    private String ctId;
//	
//	@Column(name = "priorityId")
//    private String priorityId;
//
//    @JoinColumn(name = "companyId")
//    private String companyId;
//	
//	@Column(name = "policyno")
//	private String policyno;
//	
//	@Column(name = "policycode")
//	private String policycode;
//	
//	@Column(name = "dateissue")
//	private String dateissue;
//	
//	@Column(name = "dateexp")
//	private String dateexp;
//	
//	@Column(name = "tariffId")
//	private String tariffId;
//	
//	@Column(name = "advancedamt")
//	private String advancedamt;
//	
//	@Column(name = "balanceamt")
//	private String balanceamt;
//	
//	@ManyToOne
//	@JoinColumn(name = "userId")
//	private MstUser userId;
//
//	public long getScId() {
//		return scId;
//	}
//
//	public void setScId(long scId) {
//		this.scId = scId;
//	}
//	public String getPriorityId() {
//		return priorityId;
//	}
//
//	public void setPriorityId(String priorityId) {
//		this.priorityId = priorityId;
//	}
//
//	
//	public String getPolicyno() {
//		return policyno;
//	}
//
//	public void setPolicyno(String policyno) {
//		this.policyno = policyno;
//	}
//
//	public String getPolicycode() {
//		return policycode;
//	}
//
//	public void setPolicycode(String policycode) {
//		this.policycode = policycode;
//	}
//
//	public String getDateissue() {
//		return dateissue;
//	}
//
//	public void setDateissue(String dateissue) {
//		this.dateissue = dateissue;
//	}
//
//	public String getDateexp() {
//		return dateexp;
//	}
//
//	public void setDateexp(String dateexp) {
//		this.dateexp = dateexp;
//	}
//
//	public String getTariffId() {
//		return tariffId;
//	}
//
//	public void setTariffId(String tariffId) {
//		this.tariffId = tariffId;
//	}
//
//	public String getAdvancedamt() {
//		return advancedamt;
//	}
//
//	public void setAdvancedamt(String advancedamt) {
//		this.advancedamt = advancedamt;
//	}
//
//	public String getBalanceamt() {
//		return balanceamt;
//	}
//
//	public void setBalanceamt(String balanceamt) {
//		this.balanceamt = balanceamt;
//	}
//
//	public MstUser getUserId() {
//		return userId;
//	}
//
//	public void setUserId(MstUser userId) {
//		this.userId = userId;
//	}
//
//	public String getCtId() {
//		return ctId;
//	}
//
//	public void setCtId(String ctId) {
//		this.ctId = ctId;
//	}
//
//	public String getCompanyId() {
//		return companyId;
//	}
//
//	public void setCompanyId(String companyId) {
//		this.companyId = companyId;
//	}

}
