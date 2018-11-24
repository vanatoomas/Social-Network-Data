package project;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.directory.InvalidAttributesException;

import dsa.CliApliBase;
import dsa.CliApliBase.Command;

public class App {

    // Scanner used to read input from user
    static private Scanner sc = new Scanner(System.in);

    // Social Network Data Structures & Information
    static String inFilePath;
    static String outFilePath;

    // Various utility functions
    // Retrieves a yes/no answer from the user through stdin
    private static boolean askYesNo(String toAsk) {
        while (true) {
            System.out.println(toAsk);
            String answer = sc.next();
            sc.nextLine();
            if (answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes")
                    || answer.toLowerCase().equals("confirm")) {
                return true;
            } else if (answer.toLowerCase().equals("n") || answer.toLowerCase().equals("no")
                    || answer.toLowerCase().equals("cancel")) {
                return false;
            } else {
                System.out.println("Answer not recognised, please answer y/n.");
            }
        }
    }

    // Functions called via the cli commands
    // Sets the value of inFilePath
    static Command setRead = () -> {
        System.out.println("Please introduce the desired path to read from.");
        String newPath = sc.nextLine();
        if (askYesNo("Is " + newPath + " the path you want to set as the read path?")) {
            inFilePath = newPath;
            System.out.println("Updated file path to read.");
        } else
            System.out.println("File path to read not updated.");
    };
    // Sets the value of outFilePath
    static Command setWrite = () -> {

        System.out.println("Please introduce the desired path to write to.");
        String newPath = sc.nextLine();
        if (askYesNo("Is " + newPath + " the path you want to set as the write path?")) {
            outFilePath = newPath;
            System.out.println("Updated file path to write.");
        } else
            System.out.println("File path to write not updated.");
    };
    // Reads users from inFilePath
    static Command readUsers = () -> {
        try {
            System.out.println(UserMap.addUsers(project.ReadFile.readUsers(inFilePath)) + " users added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    // Reads friendships from inFilePath
    static Command readFriendships = () -> {
        try {
            System.out.println(FriendshipMap.addFriendship(ReadFile.readFriends(inFilePath)) + " friendships added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    // Prints users to outFilePath
    static Command saveUsers = () -> {
        try {
            WriteFile.write(outFilePath, UserMap.print());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Users saved to " + outFilePath + ".");
    };
    // Prints friendships to outFilePath
    static Command saveFriends = () -> {
        try {
            WriteFile.write(outFilePath, FriendshipMap.print());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Friends saved to " + outFilePath + ".");
    };
    // Searches for users containing a given value in the specified field and prints
    // them to stdout
    static Command search = () -> {
        System.out.println(
                "Please introduce the name of the field you are searching :\n id, groupcode, name, surname, birthplace, home, gender, birthday, studiedAt, workplaces, movies");
        String field = sc.next();
        sc.nextLine();
        System.out.println("Please introduce the value you are searching:");
        String value = sc.nextLine().trim();
        sc.nextLine();
        ArrayList<User> results = new ArrayList<>();
        try {
            results = UserMap.findValue(field, value);
        } catch (InvalidAttributesException e) {
            e.printStackTrace();
        }
        if (!results.isEmpty()) {
            System.out.println("This are the " + results.size() + " that match:");
            for (User u : results) {
                if (u != null)
                    System.out.println(u.toString());
            }
            System.out.println("\n");
        } else
            System.out.println("No users match.\n");
    };
    // Prints users to stdout
    static Command printUsers = () -> {
        System.out.println(UserMap.print() + "\n");
    };
    // Prints friendships to stdout
    static Command printFriends = () -> {
        System.out.println(FriendshipMap.print() + "\n");
    };
    //Reads residential file
    static Command readResidential = () -> {
        try {
            Residential.findPeopleHometown(inFilePath);
        }catch (IOException e){
            e.printStackTrace();
        }
    };
    //Finds all the friends of person/persons
    static Command friendsOfUser = () -> {
        System.out.println("Please enter the surname of the user:");
        String surname = sc.next();
        ArrayList<User> usersWithSurname= new ArrayList<>();
        try {
            usersWithSurname = UserMap.findValue("surname", surname);
        } catch (InvalidAttributesException e) {
            e.printStackTrace();
        }
        if (!usersWithSurname.isEmpty()){
            System.out.println("Print results to console (c) or write to file (w)?");
            String print = sc.next();
            StringBuilder toFile = new StringBuilder();
            //if (print.equals("c")){
                for (User user: usersWithSurname) {
                    if (print.equals("c")) {
                        System.out.println("Friends of " + user.getName() + " " + user.getSurname());
                    }
                    else if (print.equals("w")){
                        toFile.append("Friends of " + user.getName() + " " + user.getSurname() + "\n");
                    }
                    ArrayList<Friendship> friends = user.getFriends();
                    for (Friendship friendship: friends) {
                        if (friendship.getFriend1().getId() == user.getId()) {
                            if (print.equals("c")){
                            System.out.println(friendship.getFriend2().getId() + " " + friendship.getFriend2().getSurname());
                            }
                            else if (print.equals("w")){
                                toFile.append(friendship.getFriend2().getId() + " " + friendship.getFriend2().getSurname() + "\n");
                            }
                        }
                        else if (friendship.getFriend2().getId() == user.getId()){
                            if (print.equals("c")) {
                                System.out.println(friendship.getFriend1().getId() + " " + friendship.getFriend1().getSurname());
                            }
                            else if (print.equals("w")){
                                toFile.append(friendship.getFriend1().getId() + " " + friendship.getFriend1().getSurname() + "\n");
                            }
                        }
                    }
                }
                if (print.equals("w")){
                    try {
                        WriteFile.write(outFilePath, toFile.toString());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            //}
        }
        else {
            System.out.println("No users match.\n");
        }
    };

    //Splits users into classes based on their favorite movies and creates a list of classes
    static Command listOfMovieClasses = () -> {
        ArrayList<User> allUsers = UserMap.allUsers();
        ArrayList<MovieProfiles> result = new ArrayList<>();
        for (User u : allUsers){
            ArrayList<String> movies = u.getMovies();
            movies.sort(String::compareToIgnoreCase);
            boolean found = false;
            for (int i = 0; i < result.size(); i++) {
                if(movies.equals(result.get(i).getMovies())){
                    result.get(i).addUsers(u);
                    found = true;
                }
            }
            if (found == false || result.size() == 0){
                MovieProfiles profile = new MovieProfiles(movies);
                profile.addUsers(u);
                result.add(profile);
            }
        }
        for (MovieProfiles profile: result) {
            System.out.println(profile.toString());
        }
    };

    static Command bornBetween = () -> {
        System.out.println("Please enter the dates in form: dd-mm-yyyy,dd-mm-yyyy or yyyy,yyyy");
        String response = sc.next().trim();
        String[] dates = new String[2];
        if(response.contains("-") && response.length() == 21){
            dates = response.split(",");
           dates[0] =  dates[0].substring(dates[0].length()-4);
            dates[1] = dates[1].substring(dates[1].length()-4);
        }
        else if ( response.length() == 9){
            dates = response.split(",");
        }
        Integer date1 = Integer.parseInt(dates[0]);
        Integer date2 = Integer.parseInt(dates[1]);

        ArrayList<User> results = new ArrayList<>();
        for (int i = date1; i < date2; i++) {
            try {
                String value = Integer.toString(i);
                ArrayList<User> resultsMid = new ArrayList<>();
                resultsMid = UserMap.findValue("birthdayByYear", value);
                results.addAll(resultsMid);
            } catch (InvalidAttributesException e) {
                e.printStackTrace();
            }
        }
        if (!results.isEmpty()) {
            User.sortArrayListofUser(results);
            System.out.println("These are the " + results.size() + " that match:");
            for (User u : results) {
                if (u != null)
                    System.out.println(u.toString());
            }
            System.out.println("\n");
        } else
            System.out.println("No users match.\n");
    };

    // App configuration variables
    // Names for the accepted commands
    static String[] commandNames = { "readUsers", "readFriends", "saveUsers", "saveFriends", "setRead", "setWrite",
            "search", "printUsers", "printFriends", "readResidential", "friendsOfUser", "listOfMovieClasses","bornBetween" };
    // Test to display to user explaining options
    static String[] options = { "Read users from file", "Read friendship relationships from file",
            "Save users to a file", "Save friendships to a file", "Set read file path", "Set write file path",
            "Search users", "Displays all user data", "Displays all friendship relationships.", "Find all people from the same town as people in the file.",
    "Find friends of the user", "Splits users into classes based on their favorite movies and creates a list", "Retrieve the people who were born between dates D1 and D2"};
    // Functions to be called upon command execution
    static Command[] test = { readUsers, readFriendships, saveUsers, saveFriends, setRead, setWrite, search, printUsers,
            printFriends, readResidential, friendsOfUser, listOfMovieClasses, bornBetween };
    // App instance declaration
    static CliApliBase app;

    public static void main(String[] args) throws Exception {
        // Initialize app
        app = new CliApliBase(commandNames, test, options);
        // Set setRead to be executed at the start by default
        app.setInitAction(setRead);
        // Set the initial message of the aplication
        app.setStartMessage(
                "Welcome to the programming project for DSA made by the group G611837. This program emulates a social network.\n"
                        + "-------------------------------------------------------------------------------------------------------------");
        // Main loop
        app.startReading();

        sc.close();
    }

}