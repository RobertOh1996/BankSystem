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

public class AccountClass implements AccountDAO {
	
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
		Scanner sc = new Scanner("AccountInfo.txt");
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
	public boolean addAccount(Account account) throws FileNotFoundException {
		PrintWriter wr = new PrintWriter(new FileOutputStream(new File("AccountInfo.txt"), true));
		wr.println(account);
		wr.close();
		return false;
	}

	@Override
	public boolean deleteAccount(String accountId) throws IOException {
		File accountList = new File("AccountInfo.txt");
		File deleteFile = new File("forDelete.txt");
		BufferedReader rd = new BufferedReader(new FileReader("AccountInfo.txt"));
		BufferedWriter rw = new BufferedWriter(new FileWriter("forDelete.txt"));
		String Line;
		while((Line = rd.readLine()) != null) {
			String id = Line.split(",")[0];
			if(id.equals(accountId)) continue;
			rw.write(Line + System.getProperty("line.seperator"));
		}
		rw.close();
		rd.close();
		accountList.delete();
		return deleteFile.renameTo(accountList);
	}

	@Override
	public boolean updateAccount(Account account) throws IOException {
		File accountList = new File("AccountInfo.txt");
		File updateFile = new File("forUpdate.txt");
		BufferedReader rd = new BufferedReader(new FileReader("AccountInfo.txt"));
		BufferedWriter rw = new BufferedWriter(new FileWriter("forUpdate.txt"));
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
		return updateFile.renameTo(accountList);
	}

}
