package informationDAO;

import java.io.FileNotFoundException;
import java.text.ParseException;

import user.User;

public interface UserDAO {
	public User makeUser(String licenseNumber) throws FileNotFoundException, ParseException;
	
	public boolean addUser(User user) throws FileNotFoundException;
	
	public boolean removeUser() throws FileNotFoundException;
}
