import user.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import account.Account;
import account.AccountFunctions;
import account.AccountType;
import informationDAO.AccountClass;
import informationDAO.AccountDAO;
import informationDAO.TransactionClass;
import informationDAO.TransactionDAO;
import informationDAO.UserClass;
import informationDAO.UserDAO;
import transaction.Transaction;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		User user = new User("931107", "Jaemin", "N", "Oh", "Student", "12/12/1996", "Broomfield St", "806", "48858");
		AccountFunctions af = new AccountFunctions();
		Account account = af.createAccount(AccountType.PERSONALCHECKING, user, true);
	
		UserDAO userdao = new UserClass();
		userdao.addUser(user);
		AccountDAO accountdao = new AccountClass();
		accountdao.addAccount(account);
		TransactionDAO transactiondao = new TransactionClass();
		af = new AccountFunctions(accountdao, transactiondao);
		af.depositLog(account, "200.00");
		System.out.println(account.getAccountBalance());
		af.withdrawLog(account, "300.00");
		System.out.println(account.getAccountBalance());
		account = accountdao.getAccountByAccountNumber("0");
		System.out.println(account.getAccountBalance());
		Transaction transactionList = transactiondao.getTransaction("0", "0");
		System.out.println(transactionList);
	}

}
