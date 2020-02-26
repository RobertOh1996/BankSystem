package account;

import java.io.FileNotFoundException;

import informationDAO.AccountDAO;
import user.User;

public class AccountFunctions {
	
	private AccountDAO accountdao;
	
	
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
}
