package user;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import informationDAO.AccountClass;
import informationDAO.UserClass;

public class User {
	private UserClass userdao;
	private AccountClass accountdao;
	
	private String licenseNumber;
	private String firstName;
	private String mName;
	private String lastName;
	private String occupation;
	private LocalDate birthday;
	private String street;
	private String addressNum;
	private String zip;
	
	private static final DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
	
	public User(String licenseNumber, String firstName, String mName, String lastName, String occupation, String birthday, String street, String addressNum, String zip) {
		this.licenseNumber = licenseNumber;
		this.firstName = firstName;
		this.mName = mName;
		this.lastName = lastName;
		this.occupation = occupation;
		this.birthday = LocalDate.parse(birthday, date);
		this.street = street;
		this.addressNum = addressNum;
		this.zip = zip;
	}

	public User(String[] userFromFile) throws ParseException {
		this.licenseNumber = userFromFile[0];
		this.firstName = userFromFile[1];
		this.mName = userFromFile[2];
		this.lastName = userFromFile[3];
		this.occupation = userFromFile[4];
		this.birthday = LocalDate.parse(userFromFile[5], date);
		this.street = userFromFile[6];
		this.addressNum = userFromFile[7];
		this.zip = userFromFile[8];
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s", this.licenseNumber, this.firstName, this.mName, this.lastName, this.occupation, this.birthday, this.street, this.addressNum, this.zip);
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddressNum() {
		return addressNum;
	}

	public void setAddressNum(String addressNum) {
		this.addressNum = addressNum;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
