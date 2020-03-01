package account;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import informationDAO.AccountDAO;
import informationDAO.UserDAO;

public class Account {
	
	private String accountId;
	private String licenseNumber;
	private BigDecimal accountBalance;
	private AccountType type;
	private AccountStatus status;

	private boolean canOverDraft;

	public Account(String[] accountData, UserDAO userdao) {
		this.accountId = accountData[0];
		this.licenseNumber = accountData[1];
		this.accountBalance = new BigDecimal(accountData[2]);
		this.type = AccountType.valueOf(accountData[3]);
		this.status = AccountStatus.valueOf(accountData[4]);
		this.canOverDraft = Boolean.valueOf(accountData[5]);
	}

	public Account(String licenseNumber, AccountType type, boolean canOverDraft) throws FileNotFoundException {
		this.accountId = AccountDAO.createNextId();
		this.licenseNumber = licenseNumber;
		this.accountBalance = BigDecimal.ZERO;
		this.type = type;
		this.status = AccountStatus.ACTIVE;
		this.canOverDraft = canOverDraft;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s", this.accountId, this.licenseNumber, this.accountBalance,
				this.type, this.status, this.canOverDraft);
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
