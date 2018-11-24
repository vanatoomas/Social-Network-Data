package project;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javax.naming.directory.InvalidAttributesException;

public class UserMap {
    // Main data structure
    static private Map<String, User> userMap = new HashMap<String, User>();

    /*
     * Function to add a single user returns true if it was added successfully
     */
    public static boolean addUser(User u) {
        if (userMap.containsKey(u.getId()))
            return false;
        userMap.put(u.getId(), u);
        return true;
    }

    /*
     * Function to add an arraylist of users returns the amount of successful
     * additions
     */
    public static int addUsers(ArrayList<User> usersIn) {
        int count = 0;
        for (User u : usersIn) {
            if (addUser(u))
                count++;
        }
        return count;
    }

    /* Returns the user with the given key */
    public static User get(String key) {
        return userMap.get(key);
    }

    /* Returns an arraylist of users that contain @param value in the given field */
    // TODO -> Clean it up and fix arraylist fields' inconsistencies
    public static ArrayList<User> findValue(String field, String value) throws InvalidAttributesException {
        ArrayList<User> output = new ArrayList<>();
        switch (field) {
            case "id":
                output.add(userMap.get(value));
                break;
            case "groupcode":
                for (User u : userMap.values()) {
                    if (u.getGroupcode().equals(value))
                        output.add(u);
                }
                break;
            case "name":
                for (User u : userMap.values()) {
                    if (u.getName().equals(value))
                        output.add(u);
                }
                break;
            case "surname":
                for (User u : userMap.values()) {
                    if (u.getSurname().equals(value))
                        output.add(u);
                }
                break;
            case "birthplace":
                for (User u : userMap.values()) {
                    if (u.getBirthplace().equals(value))
                        output.add(u);
                }
                break;
            case "home":
                for (User u : userMap.values()) {
                    if (u.getHome().equals(value))
                        output.add(u);
                }
                break;
            case "gender":
                for (User u : userMap.values()) {
                    if (u.getGender().equals(value))
                        output.add(u);
                }
                break;
            case "birthday":
                for (User u : userMap.values()) {
                    if (u.getBirthday().equals(ReadFile.convertDate(value)))
                        output.add(u);
                }
                break;

            case "birthdayByYear":
                for(User u : userMap.values()){
                    Date date = u.getBirthday();
                    LocalDate dateCompare = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if(dateCompare.getYear() == Integer.parseInt(value)){
                        output.add(u);
                    }
                }
            case "studiedAt":
                for (User u : userMap.values()) {
                    if (u.hasSchool(value))
                        output.add(u);
                }
                break;
            case "workplaces":
                for (User u : userMap.values()) {
                    if (u.hasWorkplace(value))
                        output.add(u);
                }
                break;
            case "movies":
                for (User u : userMap.values()) {
                    if (u.hasMovie(value.trim())) {
                        output.add(u);
                    }
                }
                break;
            default:
                throw new NoSuchFieldError("The introduced field doesn't exist.");

        }
        return output;
    }

    // Returns true if the user exits
    public static boolean verifyUser(String id) {
        return userMap.containsKey(id);
    }

    public static boolean verifyUser(User u) {
        return verifyUser(u.getId());
    }

    // Returns true if both users exit
    public static boolean verifyPair(String id1, String id2) {
        return verifyUser(id1) && verifyUser(id2);
    }

    public static boolean verifyPair(User u1, User u2) {
        return verifyUser(u1) && verifyUser(u2);
    }

    // Returns an string made from the contents of the userMap formatted
    public static String print() {
        String output = "idperson,name,lastname,birthdate,gender,birthplace,home,studiedat,workplaces,films,groupcode";
        for (User u : userMap.values()) {
            output += "\n" + u.toString();
        }
        return output;
    }
    public static ArrayList<User> allUsers(){
        ArrayList<User> result = new ArrayList<>();

        for (User u : userMap.values()){
            result.add(u);
        }
        return result;
    }
}