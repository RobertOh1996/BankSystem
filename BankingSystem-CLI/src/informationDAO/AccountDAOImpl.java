package informationDAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import account.Account;

/**
 * This class implemented AccountDAO.
 * @author Jaemin Oh
 *
 */
public class AccountDAOImpl implements AccountDAO {
	
	private UserDAO userdao;	

	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws FileNotFoundException, ParseException {
		Scanner sc = new Scanner(new File("AccountInfo.txt"));
		Account accountList = null;
		while(accountList == null || sc.hasNextLine()) {
			String[] accountLine = sc.nextLine().split(",");
			if(accountNumber.equals(accountLine[0])) {
				accountList = new Account(accountLine, this.userdao);
			}
		}
		sc.close();
		return accountList;
	}	

	@Override
	public List<Account> getAccountByLicenseNumber(String licenseNumber) throws FileNotFoundException, ParseException {
		Scanner sc = new Scanner(new File("AccountInfo.txt"));
		List<Account> accountList = new ArrayList<Account>();
		while(sc.hasNextLine()) {
			String[] accountLine = sc.nextLine().split(",");
			if(licenseNumber.equals(accountLine[1])) {
				accountList.add(new Account(accountLine, this.userdao));
			}
		}
		sc.close();
		return accountList;
	}
	

	@Override
	public List<Account> getAccountAll() throws FileNotFoundException, ParseException {
		Scanner sc = new Scanner(new File("AccountInfo.txt"));
		List<Account> accountList = new ArrayList<Account>();
		while(sc.hasNextLine()) {
			String[] accountLine = sc.nextLine().split(",");
			accountList.add(new Account(accountLine, this.userdao));
		}
		sc.close();
		return accountList;
	}

	@Override
	public boolean addAccount(Account account) throws FileNotFoundException {
		PrintWriter wr = new PrintWriter(new FileOutputStream(new File("AccountInfo.txt"), true));
		wr.println(account);
		wr.close();
		return false;
	}

	@Override
	public boolean deleteAccount(String accountId) throws IOException {
		File accountList = new File("AccountInfo.txt");
		File deleteAccount = new File("forDeleteAccount.txt");
		BufferedReader rd = new BufferedReader(new FileReader(accountList));
		BufferedWriter rw = new BufferedWriter(new FileWriter(deleteAccount));
		String Line;
		while((Line = rd.readLine()) != null) {
			String id = Line.split(",")[0];
			if(id.equals(accountId)) continue;
			rw.write(Line + System.getProperty("line.seperator"));
		}
		rw.close();
		rd.close();
		accountList.delete();
		return deleteAccount.renameTo(accountList);
	}

	@Override
	public boolean updateAccount(Account account) throws IOException {
		File accountList = new File("AccountInfo.txt");
		File updateAccount = new File("forUpdateAccount.txt");
		BufferedReader rd = new BufferedReader(new FileReader(accountList));
		BufferedWriter rw = new BufferedWriter(new FileWriter(updateAccount));
		String Line;
		while((Line = rd.readLine()) != null) {
			String accountId = Line.split(",")[0];
			if(accountId.equals(account.getAccountId())) {
				rw.write(account.toString() + System.getProperty("line.seperator"));
			} else{
				rw.write(Line + System.getProperty("line.seperator"));
			}
		}
		rd.close();
		rw.close();
		accountList.delete();
		return updateAccount.renameTo(accountList);
	}


}
