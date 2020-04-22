package informationDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import account.Account;

/**
 * It defines necessary functions for AccountDAO.
 * @author Jaemin Oh
 *
 */
public interface AccountDAO {
	
	/**
	 * Find account by account number in database.
	 * @param accountNumber: A valid user account number.
	 * @return: An account object.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 * @throws ParseException: Thrown if parsing date is not successful.
	 */
	public Account getAccountByAccountNumber(String accountNumber) throws FileNotFoundException, ParseException;
	
	/**
	 * Find account by driver's lincense number in database.
	 * @param licenseNumber: A valid user driver's license number
	 * @return: A series of account.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 * @throws ParseException: Thrown if parsing date is not successful.
	 */
	public List<Account> getAccountByLicenseNumber(String licenseNumber) throws FileNotFoundException, ParseException;
	
	/**
	 * Find All accounts.
	 * @return: A series of account.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 * @throws ParseException: Thrown if parsing date is not successful.
	 */
	public List<Account> getAccountAll() throws FileNotFoundException, ParseException;
	
	/**
	 * Add account into account database.
	 * @param account: A newly created account object.
	 * @return: True if account successfully added, or not, get False.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 */
	public boolean addAccount(Account account) throws FileNotFoundException;
	
	/**
	 * Delete account from account database.
	 * @param accountId: A valid account ID for find an account
	 * @return: True if account successfully deleted, or not, get False.
	 * @throws IOException: Thrown if connection to account database is not successful.
	 */
	public boolean deleteAccount(String accountId) throws IOException;
	
	/**
	 * Update account from account database.
	 * @param account: An account object to update.
	 * @return: True if account successfully updated, or not, get False.
	 * @throws IOException: Thrown if connection to account database is not successful.
	 */
	public boolean updateAccount(Account account) throws IOException;
	
	/**
	 * It gives account number to created accounts.
	 * @return: Given account number.
	 * @throws FileNotFoundException: Thrown if connection to account database is not successful.
	 */
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
