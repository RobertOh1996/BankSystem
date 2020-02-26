package account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

import informationDAO.AccountDAO;
import informationDAO.TransactionDAO;
import user.User;

public class AccountFunctions {
	
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
}
