package user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import informationDAO.AccountDAO;
import informationDAO.UserDAO;

/**
 * This class has several services executed by user contoller.
 * @author Jaemin Oh
 *
 */
public class UserController {
	
	UserDAO userdao;
	AccountDAO accountdao;
	
	/**
	 * It defines valid user data and add it.
	 * @param licenseNumber: A user's driver license number.
	 * @param firstName: A user's first name.
	 * @param mName: A user's middle name.
	 * @param lastName: A user's last name.
	 * @param occupation: A user's occuption.
	 * @param birthday: A user's birthday
	 * @param street: A user's street number.
	 * @param addressNum: A user's address number.
	 * @param zip: A user's zip number
	 * @return: True if user data added successfully, or not, False.
	 * @throws FileNotFoundException: If connection to user database is not successful.
	 * @throws IllegalArgumentException: If required information is not exist.
	 */
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
	
	/**
	 * It defiens valid condition for delete and delete user data.
	 * @param licenseNumber: A key to account list for deletion.
	 * @return: True if data deleted successfully, or not, get False.
	 * @throws IOException: Thrown if a valid license number is not present.
	 * @throws ParseException: Thrown if there is a problem related to parsing birthday
	 */
	public boolean deleteUser(String licenseNumber) throws IOException, ParseException{
		if(accountdao.getAccountByLicenseNumber(licenseNumber).size() != 0) {
			throw new IllegalStateException("All accounts should be deleted before delete user");
		}
		return this.userdao.deleteUser(licenseNumber);		
	}
}
