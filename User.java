package dataStructures;

import java.util.ArrayList;
import java.util.Date;

public class User {
	private String groupcode = "G611837";
	private final String id;
	private String name, surname, birthplace, home, gender;
	private ArrayList<String> studiedAt, workplaces, movies = new ArrayList<String>();
	private Date birthday;

	// Constructor
	/* Minimal constructor ,use not advised */
	public User(String id) {
		this.id = id;
	}

	/* Default Groupcode */
	public User(String id, String name, String surname, Date birthday, String gender, String birthplace, String home,
			ArrayList<String> studiedAt, ArrayList<String> workplaces, ArrayList<String> movies) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.gender = gender;
		this.birthplace = birthplace;
		this.home = home;
		this.studiedAt = studiedAt;
		this.workplaces = workplaces;
		this.movies = movies;
	}

	/* Custom Groupcode */
	public User(String id, String name, String surname, Date birthday, String gender, String birthplace, String home,
			ArrayList<String> studiedAt, ArrayList<String> workplaces, ArrayList<String> movies, String groupcode) {
		this(id, name, surname, birthday, gender, birthplace, home, studiedAt, workplaces, movies);
		this.groupcode = groupcode;
	}

	// Getters & Setters

	public String getGroupcode() {
		return groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getId() {
		return id;
	}

	public ArrayList<String> getStudiedAt() {
		return studiedAt;
	}

	public ArrayList<String> getWorkplaces() {
		return workplaces;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	// List methods
	public void addWorkplace(String newWorkplace) {
		workplaces.add(newWorkplace);
	}

	public void addStudyPlace(String newStudyPlace) {
		studiedAt.add(newStudyPlace);
	}

	public void addMovie(String newMovie) {
		movies.add(newMovie);
	}

	private String StringArrayListToString(ArrayList<String> input) {
		String output = input.get(0);
		for (int i = 1; i < input.size(); i++) {
			output = output + ";" + input.get(i);
		}
		return output;
	}

	// ToString
	@Override
	public String toString() {
		return id + "," + name + "," + surname + "," + birthday.toString() + "," + gender + "," + birthplace + ","
				+ home + "," + StringArrayListToString(studiedAt) + "," + StringArrayListToString(workplaces) + ","
				+ StringArrayListToString(movies) + "," + groupcode;
	}

}
