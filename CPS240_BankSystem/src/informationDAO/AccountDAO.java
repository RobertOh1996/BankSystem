package informationDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import account.Account;

public interface AccountDAO {
	public Account getAccountByAccountNumber(String accountNumber) throws FileNotFoundException, ParseException;
	
	public List<Account> getAccountByLicenseNumber(String licenseNumber) throws FileNotFoundException, ParseException;
	
	public boolean addAccount(Account account) throws FileNotFoundException;
	
	public boolean deleteAccount(String accountId) throws IOException;
	
	public boolean updateAccount(Account account) throws IOException;
}
