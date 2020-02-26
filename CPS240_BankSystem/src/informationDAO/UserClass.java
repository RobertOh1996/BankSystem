package informationDAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	public boolean deleteUser(String licenseNumber) throws IOException {
		File userList = new File("userInfo.txt");
		File deleteUser = new File("forDeleteUser.txt");
		BufferedReader rd = new BufferedReader(new FileReader(userList));
		BufferedWriter rw = new BufferedWriter(new FileWriter(deleteUser));
		String Line;
		while((Line = rd.readLine()) != null){
			String seperated = Line.split(",")[0];
			if(seperated.equals(licenseNumber)) continue;
			rw.write(Line + System.getProperty("line.seperator"));			
		}
		rd.close();
		rw.close();
		userList.delete();
		return deleteUser.renameTo(userList);
	}

}
