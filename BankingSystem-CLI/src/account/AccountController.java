package account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import informationDAO.AccountDAO;
import informationDAO.TransactionDAO;
import informationDAO.UserDAO;
import transaction.Transaction;
import transaction.TransactionType;
import user.User;

/**
 * This class has several services executed by account contoller.
 * @author Jaemin Oh
 *
 */
public class AccountController {

	public AccountController(AccountDAO accountdao, TransactionDAO transactiondao) {
		this.accountdao = accountdao;
		this.transactiondao = transactiondao;
	}

	/**
	 * It defines valid overdraft fee for account information
	 */
	private static final BigDecimal overDraftFee = new BigDecimal("35.00");
	
	private AccountDAO accountdao;
	private TransactionDAO transactiondao;

	/**
	 * Create account with given information.
	 * @param type: A valid account type.
	 * @param user: A valid user object.
	 * @param canOverDraft: Represents whether this user can overdraft or not.
	 * @return: An newly created account.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 */
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
	
	/**
	 * Add account into account database.
	 * @param account: A valid account information.
	 * @return: True if the account successfully enrolled, or not, get False.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 */
	public boolean addAccount(Account account) throws FileNotFoundException{
		return this.accountdao.addAccount(account);		
	}
	
	/**
	 * Delete account only if the account balance is 0
	 * @param accountId: A valid account ID
	 * @return: True if account successfully deleted, or not, get False.
	 * @throws IOException: Thrown if connection to account database is not successful.
	 * @throws ParseException: Thrown if parsing process is not successful.
	 */
	public boolean deleteAccount(String accountId) throws IOException, ParseException{
		Account account = this.accountdao.getAccountByAccountNumber(accountId);
		if(account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalStateException("Your current balance is minus.");
		}
		return this.accountdao.deleteAccount(accountId);	
	}
	
	/**
	 * Update account balance and write deposit log into transaction database.
	 * @param account: An account wanted to be deposit
	 * @param amountTo: An amount to want to deposit
	 * @return: True if updating and writing is successful, or not, get False.
	 * @throws IOException: Thrown if connection to account database is not successful.
	 */
	public boolean deposit(Account account, String amountTo) throws IOException{
		Transaction tr = this.depositLog(account, amountTo);
		accountdao.updateAccount(account);
		transactiondao.writeTransaction(tr);		
		return true;		
	}
	
	/**
	 * Deposit money into the account.
	 * @param account: An account wanted to be deposit
	 * @param amountTo: An amount to want to deposit
	 * @return: A valid transaction log.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 */
	public Transaction depositLog(Account account, String amountTo) throws FileNotFoundException{
		BigDecimal amountToDeposit = new BigDecimal(amountTo);
		BigDecimal newBalance = account.getAccountBalance().add(amountToDeposit);
		account.setAccountBalance(newBalance);
		if(account.getStatus() == AccountStatus.OVERDRAWN && newBalance.compareTo(BigDecimal.ZERO) >= 0) {
			account.setStatus(AccountStatus.ACTIVE);
		}
		return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.DEPOSIT, amountToDeposit, account.getAccountBalance());		
	}
	
	/**
	 * Update account balance and write withdraw log into transaction database.
	 * @param account: An account wanted to be withdraw
	 * @param amountTo: An amount to want to withdraw
	 * @return: True if updating and writing is successful, or not, get False.
	 * @throws IOException: Thrown if connection to account database is not successful.
	 */
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
	
	/**
	 * Withdraw money from the account.
	 * @param account: An account wanted to be withdraw
	 * @param amountTo: An amount to want to withdraw
	 * @return: A valid transaction log.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 */
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
	
	/**
	 * Apply overdraft fee into specific account.
	 * @param account: An account should be apply overdraft fee.
	 * @return: A valid transaction log.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 */
	public Transaction overDraftFeeService(Account account) throws FileNotFoundException{
		account.setAccountBalance(account.getAccountBalance().subtract(overDraftFee));
		return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.TR_FEE, overDraftFee, account.getAccountBalance());
	}
	
	/**
	 * Define overdraft fee limit depends on account type.
	 * @param type: A valid account type.
	 * @return: A designated overdraft limit.
	 */
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
	
	/**
	 * Apply monthly fee into specific account.
	 * @throws IOException: Thrown if connection to account database is not successful.
	 * @throws ParseException: Thrown if parsing process is not successful.
	 */
	public void monthlyFeeService() throws IOException, ParseException{
		List<Account> accountList = accountdao.getAccountAll();
		for(Account ac : accountList) {
			BigDecimal monthlyFee = this.defineMonthlyFee(ac);
			ac.setAccountBalance(ac.getAccountBalance().subtract(monthlyFee));
			accountdao.updateAccount(ac);
			transactiondao.writeTransaction(new Transaction(ac.getAccountId(), LocalDate.now(), TransactionType.MONTHLY_FEE, monthlyFee, ac.getAccountBalance()));
		}
	}
	
	/**
	 * Define montly fee depends on account type.
	 * @param account: A valid account object.
	 * @return: A designated monthly fee.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 * @throws ParseException: Thrown if parsing process is not successful.
	 */
	public BigDecimal defineMonthlyFee(Account account) throws FileNotFoundException, ParseException{
		BigDecimal monthlyFee = BigDecimal.ZERO;
		if(account.getType().equals(AccountType.PERSONALCHECKING)) {
			monthlyFee = BigDecimal.TEN;
			LocalDate firstday = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			LocalDate lastday = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
			List<Transaction> trList = transactiondao.getTransactionsBetweenRange(account.getAccountId(), firstday, lastday);
			boolean feeExempted = false;
			boolean balanceIsOver500 = true;
			for(Transaction tr: trList) {
				if(tr.getType().equals(TransactionType.DEPOSIT) && tr.getAmountTo().compareTo(new BigDecimal("500.00")) >= 0) {
					feeExempted = true;
					break;
				}
				else if(tr.getAccountBalance().compareTo(new BigDecimal("500.00")) < 0) {
					balanceIsOver500 = false;
					break;
				}
			}
			List<Account> acList = accountdao.getAccountByLicenseNumber(account.getLicenseNumber());
			BigDecimal avg = BigDecimal.ZERO;
			BigDecimal sum = BigDecimal.ZERO;
			for(Account ac: acList) {
				sum = sum.add(ac.getAccountBalance());
			}
			if(avg.compareTo(BigDecimal.ZERO) > 0) {
				avg = sum.divide(BigDecimal.valueOf(acList.size()), 15, RoundingMode.HALF_UP);
			}
			if(avg.compareTo(new BigDecimal("5000.00")) >= 0) {
				feeExempted = true;
			}
			if(feeExempted == true || balanceIsOver500 == true) {
				monthlyFee = BigDecimal.ZERO;
			}		
		}
		else if(account.getType().equals(AccountType.STUDENTSAVING)){
			monthlyFee = new BigDecimal("5.00");
			if(account.getAccountBalance().compareTo(new BigDecimal("250.00")) >= 0) {
				monthlyFee = BigDecimal.ZERO;
			}
		}
		else if(account.getType().equals(AccountType.PERSOANLSAVING)){
			monthlyFee = new BigDecimal("25.00");
			if(account.getAccountBalance().compareTo(new BigDecimal("250.00")) >= 0) {
				monthlyFee = BigDecimal.ZERO;
			}
		}
		else if(account.getType().equals(AccountType.STUDENTSAVING)){
			monthlyFee = new BigDecimal("25.00");
			if(account.getAccountBalance().compareTo(new BigDecimal("2500.00")) >= 0) {
				monthlyFee = BigDecimal.ZERO;
			}
		}
		else System.out.println("Not defined type");
		return monthlyFee;	
	}
}
