package informationDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import account.Account;

public interface AccountDAO {
	public Account getAccountByAccountNumber(String accountNumber) throws FileNotFoundException, ParseException;
	
	public List<Account> getAccountByLicenseNumber(String licenseNumber) throws FileNotFoundException, ParseException;
	
	public boolean addAccount(Account account) throws FileNotFoundException;
	
	public boolean deleteAccount(String accountId) throws IOException;
	
	public boolean updateAccount(Account account) throws IOException;
	
	public static String createNextId() throws FileNotFoundException{
		Scanner sc = new Scanner("AccountInfo.txt");
		int accountCounter = 0;
		while(sc.hasNextLine()) {
			sc.nextLine();
			accountCounter += 1;
		}
		sc.close();
		return String.valueOf(accountCounter);		
	}
}
