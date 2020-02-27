package transaction;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import informationDAO.TransactionDAO;

public class Transaction {
	
	private static final DateTimeFormatter Date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
	
	private String accountId;
	private TransactionType type;
	private String transactionId;
	private LocalDate date;
	private BigDecimal amountTo;
	private BigDecimal accountBalance;
	
	public Transaction(String[] transactionData) {
		this.transactionId = transactionData[0];
		this.accountId = transactionData[1];
		this.date = LocalDate.parse(transactionData[2], Date);
		this.type = TransactionType.valueOf(transactionData[3]);
		this.amountTo = new BigDecimal(transactionData[4]);
		this.accountBalance = new BigDecimal(transactionData[5]);
	}

	public Transaction(String accountId, LocalDate date, TransactionType type, BigDecimal amountTo, BigDecimal accountBalance) throws FileNotFoundException {
		this.transactionId = TransactionDAO.getNextId();
		this.accountId = accountId;
		this.date = date;
		this.type = type;
		this.amountTo = amountTo;
		this.accountBalance = accountBalance;
;	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s", this.transactionId, this.accountId, this.date, this.type, this.amountTo, this.accountBalance);
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(BigDecimal amountTo) {
		this.amountTo = amountTo;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}	
	
}
