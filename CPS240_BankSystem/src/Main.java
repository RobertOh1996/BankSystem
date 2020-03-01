import user.User;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import account.Account;
import account.AccountController;
import account.AccountType;
import informationDAO.AccountDAOImpl;
import informationDAO.AccountDAO;
import informationDAO.TransactionDAO;
import informationDAO.TransactionDAOImpl;
import informationDAO.UserDAOImpl;
import informationDAO.UserDAO;
import transaction.Transaction;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		User user = new User("931107", "Jaemin", "N", "Oh", "Student", "12/12/1996", "Broomfield St", "806", "48858");
		AccountDAO accountDAO = new AccountDAOImpl();
		TransactionDAO transactionDAO = new TransactionDAOImpl();
		AccountController af = new AccountController(accountDAO, transactionDAO);
		Account account = af.createAccount(AccountType.PERSONALCHECKING, user, true);
	
		UserDAO userdao = new UserDAOImpl();
		userdao.addUser(user);
		AccountDAO accountdao = new AccountDAOImpl();
		accountdao.addAccount(account);
		af = new AccountController(accountdao, transactionDAO);
		af.deposit(account, "200.00");		
		System.out.println(account.getAccountBalance());
		af.withdraw(account, "300.00");
		System.out.println(account.getAccountBalance());
		
		List<Transaction> transactionList = transactionDAO.getTransactionsBetweenRange("0", LocalDate.now(), LocalDate.now());
		for(Transaction t: transactionList) {
			System.out.println(t);
		}
	
	}
}
