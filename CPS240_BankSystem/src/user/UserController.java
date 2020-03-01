package user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import informationDAO.AccountDAO;
import informationDAO.UserDAO;

public class UserController {
	
	UserDAO userdao;
	AccountDAO accountdao;
	
	public boolean addUser(String licenseNumber, String firstName, String mName, String lastName, String occupation, String birthday, String street, String addressNum, String zip) throws FileNotFoundException, IllegalArgumentException {
		if(licenseNumber == null) {
			throw new IllegalArgumentException("Valid License Number is required");
		} else if(firstName == null) {
			throw new IllegalArgumentException("Valid First Name is required");
		} else if(lastName == null) {
			throw new IllegalArgumentException("Valid Last Name is required");
		} else if(occupation == null ) {
			throw new IllegalArgumentException("Valid occupation name is required");
		} else if(!birthday.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
			throw new IllegalArgumentException("Valid birthday is required");
		} else if(street == null) {
			throw new IllegalArgumentException("Valid street name is required");
		} else if(addressNum == null) {
			throw new IllegalArgumentException("Valid address number is required");
		} else if(!zip.matches("([0-9]{5})")){
			throw new IllegalArgumentException("Valid zip number is required");
		}
		return this.userdao.addUser(new User(licenseNumber, firstName, mName, lastName, occupation, birthday, street, addressNum, zip));		
	}
	
	public boolean deleteUser(String licenseNumber) throws IOException, ParseException{
		if(accountdao.getAccountByLicenseNumber(licenseNumber).size() != 0) {
			throw new IllegalStateException("All accounts should be deleted before delete user");
		}
		return this.userdao.deleteUser(licenseNumber);		
	}
}
