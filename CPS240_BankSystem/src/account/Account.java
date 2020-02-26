package account;

import java.math.BigDecimal;

import informationDAO.UserDAO;

public class Account {
	private String accountId;
	private String licenseNumber;
	private BigDecimal accountBalance;
	private AccountType type;
	private AccountStatus status;
	
	private boolean canOverDraft;
	
	public Account(String[] accountLine, UserDAO userdao) {
		this.accountId = accountLine[0];
		this.licenseNumber = accountLine[1];
		this.accountBalance = new BigDecimal(accountLine[2]);
		this.type = AccountType.valueOf(accountLine[3]);
		this.status = AccountStatus.valueOf(accountLine[4]);
		this.canOverDraft = Boolean.valueOf(accountLine[5]);
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s", this.accountId, this.licenseNumber, this.accountBalance, this.type, this.status, this.canOverDraft);
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public boolean isCanOverDraft() {
		return canOverDraft;
	}

	public void setCanOverDraft(boolean canOverDraft) {
		this.canOverDraft = canOverDraft;
	}
}
