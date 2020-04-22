package informationDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import transaction.Transaction;

/**
 * This class implemented transactionDAO.
 * @author Jaemin Oh
 *
 */
public class TransactionDAOImpl implements TransactionDAO {
	
	/**
	 * It defines valid date format for transaction information
	 */
	private static final DateTimeFormatter Date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

	@Override
	public Transaction getTransaction(String accountId, String transactionId) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("transactionInfo.txt"));
		Transaction tr = null;
		while(tr == null || sc.hasNextLine()) {
			String[] trLine = sc.nextLine().split(",");
			if(transactionId.equals(trLine[0]) && accountId.equals(trLine[1])) {
				tr = new Transaction(trLine);
			}
		}
		sc.close();
		return tr;
	}

	@Override
	public List<Transaction> getTransactionsBetweenRange(String accountId, LocalDate StartDate, LocalDate endDate) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("transactionInfo.txt"));
		List<Transaction> transactionList = new ArrayList<Transaction>();
		while(sc.hasNextLine()) {
			String[] trLine = sc.nextLine().split(",");
			if(accountId.equals(trLine[2]) && (LocalDate.parse(trLine[2], Date).equals(LocalDate.now()) || (LocalDate.parse(trLine[2], Date).isAfter(StartDate) && LocalDate.parse(trLine[2], Date).isBefore(endDate)))) {
				transactionList.add(new Transaction(trLine));
			}
		}
		sc.close();
		return transactionList;
	}

	@Override
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException {
		File transactionFile = new File("transactionInfo.txt");
		PrintWriter wr = new PrintWriter(new FileOutputStream(transactionFile, true));
		wr.println(transaction);
		wr.close();
		return true;
	}

}
