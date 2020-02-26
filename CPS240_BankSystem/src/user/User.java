package user;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class User {
	
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

	public User(String[] userData) throws ParseException {
		this.licenseNumber = userData[0];
		this.firstName = userData[1];
		this.mName = userData[2];
		this.lastName = userData[3];
		this.occupation = userData[4];
		this.birthday = LocalDate.parse(userData[5], date);
		this.street = userData[6];
		this.addressNum = userData[7];
		this.zip = userData[8];
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
	

	public int getAge() {
		return Period.between(birthday, LocalDate.now()).getYears();
	}
	
}
