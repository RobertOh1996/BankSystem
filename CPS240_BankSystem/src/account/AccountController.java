package account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import informationDAO.AccountDAO;
import informationDAO.TransactionDAO;
import informationDAO.UserDAO;
import transaction.Transaction;
import transaction.TransactionType;
import user.User;

public class AccountController {

	public AccountController(AccountDAO accountdao, TransactionDAO transactiondao) {
		this.accountdao = accountdao;
		this.transactiondao = transactiondao;
	}

	private static final BigDecimal overDraftFee = new BigDecimal("35.00");
	
	private AccountDAO accountdao;
	private TransactionDAO transactiondao;

	public Account createAccount(AccountType type, User user, boolean canOverDraft) throws FileNotFoundException{
		Account account = null;
		if(type.equals(AccountType.STUDENTCHECKING) || type.equals(AccountType.STUDENTSAVING)) {
			if(user.getAge() >= 17 && user.getAge() < 23) {
				account = new Account(user.getLicenseNumber(), type, canOverDraft);
			} else {
				throw new IllegalStateException(type + "is for only between 17 to 23");
			}
		}
		else {
			account = new Account(user.getLicenseNumber(), type, canOverDraft);
		}
		return account;		
	}
	
	public boolean addAccount(Account account) throws FileNotFoundException{
		return this.accountdao.addAccount(account);		
	}
	
	public boolean deleteAccount(String accountId) throws IOException, ParseException{
		Account account = this.accountdao.getAccountByAccountNumber(accountId);
		if(account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalStateException("Your current balance is minus.");
		}
		return this.accountdao.deleteAccount(accountId);	
	}
	
	public boolean deposit(Account account, String amountTo) throws IOException{
		Transaction tr = this.depositLog(account, amountTo);
		accountdao.updateAccount(account);
		transactiondao.writeTransaction(tr);		
		return true;		
	}
	
	public Transaction depositLog(Account account, String amountTo) throws FileNotFoundException{
		BigDecimal amountToDeposit = new BigDecimal(amountTo);
		BigDecimal newBalance = account.getAccountBalance().add(amountToDeposit);
		account.setAccountBalance(newBalance);
		if(account.getStatus() == AccountStatus.OVERDRAWN && newBalance.compareTo(BigDecimal.ZERO) >= 0) {
			account.setStatus(AccountStatus.ACTIVE);
		}
		return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.DEPOSIT, amountToDeposit, account.getAccountBalance());		
	}
	
	public boolean withdraw(Account account, String amountTo) throws IOException{
		Transaction tr = this.withdrawLog(account, amountTo);
		accountdao.updateAccount(account);
		transactiondao.writeTransaction(tr);
		if(account.isCanOverDraft() && account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
			tr = overDraftFeeService(account);
			accountdao.updateAccount(account);
			transactiondao.writeTransaction(tr);
		}
		return true;		
	}
	
	public Transaction withdrawLog(Account account, String amountTo) throws FileNotFoundException {
		BigDecimal amountToWithDraw = new BigDecimal(amountTo);
		BigDecimal newBalance = account.getAccountBalance().subtract(amountToWithDraw);
		if((newBalance.compareTo(BigDecimal.ZERO) < 0 && !account.isCanOverDraft())) {
			throw new IllegalStateException("This account is not for overdraft.");
		} else if(account.isCanOverDraft() && newBalance.compareTo(defineOverDraftLimit(account.getType())) <= 0) {
			throw new IllegalStateException("Overdraft maximum exdeeded.");
		} else {
			account.setAccountBalance(newBalance);
			return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.WITHDRAW, amountToWithDraw, account.getAccountBalance());
		}
	}
	
	public Transaction overDraftFeeService(Account account) throws FileNotFoundException{
		account.setAccountBalance(account.getAccountBalance().subtract(overDraftFee));
		return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.TR_FEE, overDraftFee, account.getAccountBalance());
	}
	
	public BigDecimal defineOverDraftLimit(AccountType type) {
		BigDecimal limit = BigDecimal.ZERO;
		if(type.equals(AccountType.STUDENTCHECKING)) {
			limit = new BigDecimal("-500.00");
		} else if(type.equals(AccountType.PERSONALCHECKING)) {
			limit = new BigDecimal("-1500.00");
		} else if(type.equals(AccountType.BUSINESSCHECKING)) {
			limit = new BigDecimal("-7500.00");
		} else {
			throw new IllegalArgumentException("Not Defined this account type");
		}
		return limit;
	}
	
}
