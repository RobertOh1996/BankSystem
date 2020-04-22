package informationDAO;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import transaction.Transaction;

/**
 * It defines necessary functions for TransactionDAO.
 * @author Jaemin Oh
 *
 */
public interface TransactionDAO {
	
	/**
	 * Get transaction log by account ID and transaction ID from database.
	 * @param accountId: A valid account ID
	 * @param transactionId: A valid transaction ID
	 * @return: A transaction object
	 * @throws FileNotFoundException: Thrown if connection to transaction database is not successful.
	 */
	public Transaction getTransaction(String accountId, String transactionId) throws FileNotFoundException;

	/**
	 * Get transaction log by account ID and specified period from database.
	 * @param accountId: A valid account ID
	 * @param StartDate: A valid first date of transaction
	 * @param endDate: A valid last date of transaction
	 * @return: A list of transaction
	 * @throws FileNotFoundException: Thrown if connection to transaction database is not successful.
	 */
	public List<Transaction> getTransactionsBetweenRange(String accountId, LocalDate StartDate, LocalDate endDate) throws FileNotFoundException;
	
	/**
	 * Write transaction log into transaction database.
	 * @param transaction: A transaction object
	 * @return: True if the transaction is successfully wrote, or not, get False.
	 * @throws FileNotFoundException: Thrown if connection to transaction database is not successful.
	 */
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException;
	
	/**
	 * It gives account number to created accounts.
	 * @return: Given transaction number.
	 * @throws FileNotFoundException: Thrown if connection to transaction database is not successful.
	 */
	public static String getNextId() throws FileNotFoundException{
		Scanner sc = new Scanner("transactionInfo.txt");
		int transactionCounter = 0;
		while(sc.hasNextLine()) {
			sc.nextLine();
			transactionCounter += 1;
		}
		sc.close();
		return String.valueOf(transactionCounter);
	}
}
