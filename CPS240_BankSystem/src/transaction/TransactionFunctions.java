package transaction;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import informationDAO.TransactionClass;

public class TransactionFunctions {
	
	private TransactionClass transactiondao;
	
	public Transaction getTransaction(String accountId, String transactionId) throws FileNotFoundException {
		return this.transactiondao.getTransaction(accountId, transactionId);
	}
	
	public List<Transaction> getTransactionByTime(String accountId, LocalDate startDate, LocalDate endDate) throws FileNotFoundException {
		return this.transactiondao.getTransactionByTime(accountId, startDate, endDate);		
	}
	
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException{
		return this.transactiondao.writeTransaction(transaction);
	}
}
