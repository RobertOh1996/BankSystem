package transaction;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import informationDAO.TransactionDAOImpl;

public class TransactionController {
	
	private TransactionDAOImpl transactiondao;
	
	public Transaction getTransaction(String accountId, String transactionId) throws FileNotFoundException {
		return this.transactiondao.getTransaction(accountId, transactionId);
	}
	
	public List<Transaction> getTransactionByTime(String accountId, LocalDate startDate, LocalDate endDate) throws FileNotFoundException {
		return this.transactiondao.getTransactionsBetweenRange(accountId, startDate, endDate);		
	}
	
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException{
		return this.transactiondao.writeTransaction(transaction);
	}
}
