package informationDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;

import user.User;

public class UserClass implements UserDAO {

	@Override
	public User createUser(String licenseNumber) throws FileNotFoundException, ParseException {
		Scanner sc = new Scanner(new File("userInfo.txt"));
		User user = null;
		while(user == null || sc.hasNextLine()){
			String[] userLine = sc.nextLine().split(",");
			if(licenseNumber.equals(userLine[0])) {
				user = new User(userLine);
			}
		}
		sc.close();
		return user;
	}

	@Override
	public boolean addUser(User user) throws FileNotFoundException {
		PrintWriter wr = new PrintWriter(new FileOutputStream(new File("userInfo.txt")));
		wr.println(user);
		wr.close();
		return false;
	}

	@Override
	public boolean deleteUser() throws FileNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
