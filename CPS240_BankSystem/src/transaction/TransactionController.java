package transaction;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import informationDAO.TransactionDAOImpl;

/**
 * This class has several services executed by transaction contoller.
 * @author Jaemin Oh
 *
 */
public class TransactionController {
	
	private TransactionDAOImpl transactiondao;
	
	/**
	 * It gets a transaction by account ID and transaction ID.
	 * @param accountId: An account number for find transaction log.
	 * @param transactionId: A transaction number for find transaction log.
	 * @return: A transaction object.
	 * @throws FileNotFoundException: Thrown if connection to transaction database is not successful.
	 */
	public Transaction getTransaction(String accountId, String transactionId) throws FileNotFoundException {
		return this.transactiondao.getTransaction(accountId, transactionId);
	}
	
	/**
	 * It gets a transaction's list by account Id and specified period by two dates.
	 * @param accountId: An account number for find transaction log.
	 * @param startDate: A first date from transaction list.
	 * @param endDate: A last date from transaction list
	 * @return: A series of transactions
	 * @throws FileNotFoundException: Thrown if connection to transaction database is not successful.
	 */
	public List<Transaction> getTransactionByTime(String accountId, LocalDate startDate, LocalDate endDate) throws FileNotFoundException {
		return this.transactiondao.getTransactionsBetweenRange(accountId, startDate, endDate);		
	}
	
	/**
	 * It writes transaction to the database.
	 * @param transaction: A transaction object.
	 * @return: True if it writes transaction successfully, or not, get False
	 * @throws FileNotFoundException: Thrown if connection to transaction database is not successful.
	 */
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException{
		return this.transactiondao.writeTransaction(transaction);
	}
}
