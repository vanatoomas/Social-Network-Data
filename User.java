package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class User {
    private String groupcode = "G611837";
    private final String id;
    private String name, surname, birthplace, home, gender;
    private ArrayList<String> studiedAt, workplaces, movies = new ArrayList<String>();
    private Date birthday;
    private ArrayList<Friendship> friends = new ArrayList<>();

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

    public ArrayList<Friendship> getFriends() {
        return friends;
    }

    public ArrayList<User> getFriendsUsers() {
        ArrayList<User> result = new ArrayList<>();
        for (Friendship fs : friends){
            if(fs.getFriend1().getId() != this.id){
                result.add(fs.getFriend1());
            }
            else{
                result.add(fs.getFriend2());
            }
        }
        return result;
    }

    public ArrayList<String> getMovies() {
        return movies;
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

    public void addFriend(Friendship f) {
        friends.add(f);
    }

    public boolean hasWorkplace(String value) {
        return arrayListContains(workplaces, value);
    }

    public boolean hasSchool(String value) {
        return arrayListContains(studiedAt, value);
    }

    public boolean hasMovie(String value) {
        return arrayListContains(movies, value);
    }

    // Utility
    private static String StringArrayListToString(ArrayList<String> input) {
        if (input.size() < 1)
            return "";

        String output = input.get(0);
        for (int i = 1; i < input.size(); i++) {
            output = output + ";" + input.get(i);
        }
        return output;
    }

    private static boolean arrayListContains(ArrayList<String> list, String value) {
        for (String s : list) {
            if (s.equals(value))
                return true;
        }
        return false;
    }

    // ToString
    @Override
    public String toString() {
        String output = id + ",";
        output += ((name != null) ? name : "") + ",";
        output += ((surname != null) ? surname : "") + ",";
        output += ((birthday != null) ? birthday.toString() : "") + ",";
        output += ((gender != null) ? gender : "") + ",";
        output += ((birthplace != null) ? birthplace : "") + ",";
        output += ((home != null) ? home : "") + ",";
        output += ((studiedAt != null) ? StringArrayListToString(studiedAt) : "") + ",";
        output += ((workplaces != null) ? StringArrayListToString(workplaces) : "") + ",";
        output += ((movies != null) ? StringArrayListToString(movies) : "") + ",";
        output += groupcode;

        return output;
    }

    public static ArrayList<User> sortArrayListofUser(ArrayList<User> in) {
        //Sorts an ArrayList of Users using the lexicographic order of the following : birthplace,surname,name
        Collections.sort(in, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                if (u1.getBirthplace().compareTo(u2.getBirthplace()) > 0)
                    return 1;
                else if (u1.getSurname().compareTo(u2.getSurname()) > 0)
                    return 1;
                else if (u1.getName().compareTo(u2.getName()) > 0)
                    return 1;
                else
                    return -1;
            }

        });
        return in;
    }

    public static ArrayList<User> sortArrayListofUserbyId(ArrayList<User> in) {
        //Sorts an ArrayList of Users using the lexicographic order of the following : birthplace,surname,name
        Collections.sort(in, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                if (u1.getId().compareTo(u2.getId()) > 0)
                    return 1;
                else
                    return -1;
            }
        });
        return in;
    }

}