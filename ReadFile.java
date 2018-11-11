package project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ReadFile {

	// Reads the given file, crates all users, returns list of users
	public static ArrayList<User> readUsers(String filename) throws IOException {
		String groupcode = "", id = "", name = "", surname = "", birthplace = "", home = "", gender = "";
		Date birthday = null;
		ArrayList<String> movies = new ArrayList<String>(), studiedAt = new ArrayList<String>(),
				workplaces = new ArrayList<String>();
		ArrayList<User> allUsers = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		// Assuming that the order in People file is always the same, we skip fist line
		br.readLine();
		String line;
		while ((line = br.readLine()) != null) {
			String[] pices = line.split(",");
			// Check every time if the string is not empty
			for (int i = 0; i < pices.length; i++) {

				if (i == 0) {
					id = (!pices[i].equals("")) ? pices[0] : "";
				} else if (i == 1) {
					name = (!pices[i].equals("")) ? pices[1] : "";
				} else if (i == 2) {
					surname = (!pices[i].equals("")) ? pices[2] : "";
				} else if (i == 3) {
					birthday = (!pices[i].equals("")) ? convertDate(pices[3]) : new Date();
				} else if (i == 4) {
					gender = (!pices[i].equals("")) ? pices[4] : "";
				} else if (i == 5) {
					birthplace = (!pices[i].equals("")) ? pices[5] : "";
				} else if (i == 6) {
					home = (!pices[i].equals("")) ? pices[6] : "";
				} else if (i == 7) {
					String[] studylist = pices[7].split(";");
					ArrayList<String> studylist1 = (!pices[i].equals(""))
							? new ArrayList<String>(Arrays.asList(studylist))
							: new ArrayList<String>();
					studiedAt = studylist1;
				} else if (i == 8) {
					String[] worklist = pices[8].split(";");
					ArrayList<String> worklist1 = (!pices[i].equals(""))
							? new ArrayList<String>(Arrays.asList(worklist))
							: new ArrayList<String>();
					workplaces = worklist1;
				} else if (i == 9) {
					String[] filmlist = pices[9].split(";");
					ArrayList<String> filmlist1 = (!pices[i].equals(""))
							? new ArrayList<String>(Arrays.asList(filmlist))
							: new ArrayList<String>();
					movies = filmlist1;
				} else if (i == 10) {
					groupcode = pices[10];
				}

			}
			User user = (initializeUser(groupcode, id, name, surname, birthplace, home, gender, studiedAt, workplaces,
					movies, birthday));
			allUsers.add(user);
		}
		br.close();
		return allUsers;
	}

	/*
	 * On the social network: take the data from a person and add him/her to the
	 * network. This function does not read any data from Console. This function
	 * receives the data as parameters.
	 */
	public static User initializeUser(String groupcode, String id, String name, String surname, String birthplace,
			String home, String gender, ArrayList<String> studiedAt, ArrayList<String> workplaces,
			ArrayList<String> movies, Date birthday) {
		User user = new User(id, name, surname, birthday, gender, birthplace, home, studiedAt, workplaces, movies,
				groupcode);
		return user;
	}

	public static Date convertDate(String input) {
		LocalDate midresult = null;
		String[] dateparts = input.split("-");
		if (input.length() == 10) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			midresult = LocalDate.parse(input, formatter);
		} else if (dateparts[0].length() == 1 && dateparts[1].length() == 1) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
			midresult = LocalDate.parse(input, formatter);
		} else if (dateparts[0].length() == 1 && dateparts[1].length() == 2) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
			midresult = LocalDate.parse(input, formatter);
		} else if (dateparts[0].length() == 2 && dateparts[1].length() == 1) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
			midresult = LocalDate.parse(input, formatter);
		}
		Date result = Date.from(midresult.atStartOfDay(ZoneId.systemDefault()).toInstant());

		return result;
	}

	// Read friends file, every user has ArrayList of friends
	// setFriends takes input as only one friend not an array
	public static ArrayList<Friendship> readFriends(String filename) throws IOException {
		ArrayList<Friendship> friendships = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		br.readLine();
		String line;
		while ((line = br.readLine()) != null) {
			String[] pieces = line.split(",");
			if (UserMap.verifyPair(pieces[0], pieces[1])) {
				Friendship add = new Friendship(UserMap.get(pieces[0]), UserMap.get(pieces[1]));
				friendships.add(add);
			}
		}
		return friendships;
	}
}
