package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.directory.InvalidAttributesException;

public class UserMap {
	static private Map<String, User> userMap = new HashMap<String, User>();

	public static boolean addUser(User u) {
		if (userMap.containsKey(u.getId()))
			return false;
		userMap.put(u.getId(), u);
		return true;
	}

	public void addUsers(ArrayList<User> usersIn) {
		for (User u : usersIn) {
			addUser(u);
		}
	}

	public static ArrayList<User> findValue(String field, String value) throws InvalidAttributesException {
		ArrayList<User> output = new ArrayList<>();
		switch (field) {
		case "id":
			output.add(userMap.get(value));
			break;
		case "groupcode":
			userMap.forEach((key, user) -> {
				if (user.getGroupcode().equals(value))
					output.add(user);
			});
			break;
		case "name":
			userMap.forEach((key, user) -> {
				if (user.getName().equals(value))
					output.add(user);
			});
			break;
		case "surname":
			userMap.forEach((key, user) -> {
				if (user.getSurname().equals(value))
					output.add(user);
			});
			break;
		case "birthplace":
			userMap.forEach((key, user) -> {
				if (user.getBirthplace().equals(value))
					output.add(user);
			});
			break;
		case "home":
			userMap.forEach((key, user) -> {
				if (user.getHome().equals(value))
					output.add(user);
			});
			break;
		case "gender":
			userMap.forEach((key, user) -> {
				if (user.getGender().equals(value))
					output.add(user);
			});
			break;
		case "birthday":
			userMap.forEach((key, user) -> {
				if (user.getBirthday().equals(ReadFile.convertDate(value)))
					output.add(user);
			});
			break;
		case "studiedAt":
			userMap.forEach((key, user) -> {
				for (String s : user.getStudiedAt())
					if (s.equals(value))
						output.add(user);
			});
			break;
		case "workplaces":
			userMap.forEach((key, user) -> {
				for (String s : user.getWorkplaces())
					if (s.equals(value))
						output.add(user);
			});
			break;
		case "movies":
			userMap.forEach((key, user) -> {
				for (String s : user.getMovies())
					if (s.equals(value))
						output.add(user);
			});
			break;
		default:
			throw new NoSuchFieldError("The introduced field doesn't exist.");

		}
		return output;
	}

}
