package informationDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import user.User;

public interface UserDAO {
	public User createUser(String licenseNumber) throws FileNotFoundException, ParseException;
	
	public boolean addUser(User user) throws FileNotFoundException;
	
	public boolean deleteUser(String licenseNumber) throws IOException;
}
