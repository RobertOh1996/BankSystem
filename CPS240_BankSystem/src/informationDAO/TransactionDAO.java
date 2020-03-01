package informationDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import transaction.Transaction;

public interface TransactionDAO {
	
	public Transaction getTransaction(String accountId, String transactionId) throws FileNotFoundException;

	public List<Transaction> getTransactionsBetweenRange(String accountId, LocalDate StartDate, LocalDate endDate) throws FileNotFoundException;
	
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException;
	
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
