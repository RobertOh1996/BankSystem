package informationDAO;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import transaction.Transaction;

public class TransactionClass implements TransactionDAO {

	@Override
	public Transaction getTransaction(String accountId, String transactionId) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getTransactionByTime(String accountId, LocalDate StartDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
