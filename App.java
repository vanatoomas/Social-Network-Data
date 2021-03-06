package project;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.naming.directory.InvalidAttributesException;

import dsa.CliApliBase;
import dsa.CliApliBase.Command;

public class App {

    // Scanner used to read input from user
    static private Scanner sc = new Scanner(System.in);

    // Social Network Data Structures & Information
    static String currentDir;
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

    //Function that asks for two users until both found.
    private static User[] askUsers(){
        User[] users = new User[2];
        User u1 = null;
        User u2 = null;
        String user1 = null;
        String user2 = null;
        while (true) {
            if (u1 == null) {
                System.out.println("Please enter the id of first user");
                user1 = sc.next();
            }
            if(u2 == null) {
                System.out.println("Please enter the id of second user");
                user2 = sc.next();
            }
            ArrayList<User> results1 = new ArrayList<>();
            ArrayList<User> results2 = new ArrayList<>();
            try {
                results1 = UserMap.findValue("id", user1);
                results2 = UserMap.findValue("id", user2);

            } catch (InvalidAttributesException e) {
                e.printStackTrace();
            }
            u1 = results1.get(0);
            u2 = results2.get(0);
            if (u1 != null && u2 != null){
                users[0] = u1;
                users[1] = u2;
                return users;
            }
            else if (u1 == null){
                System.out.println("First user not found");
            }
            else if(u2 == null){
                System.out.println("Second user not found");
            }
        }
    }

    // Functions called via the cli commands
    //Sets current wrking directory
    static Command setCurrentDir = () -> {
		System.out.println("Please introduce the desired directory to read from.");
		String newPath = sc.nextLine();
		if (askYesNo("Is " + newPath + " the directory you want to set?")) {
			currentDir = newPath;
			System.out.println("Updated dir.");
		} else
			System.out.println("Dir to read not updated.");
	};
    
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
            System.out.println(UserMap.addUsers(project.ReadFile.readUsers(currentDir + inFilePath)) + " users added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    // Reads friendships from inFilePath
    static Command readFriendships = () -> {
        try {
            System.out.println(FriendshipMap.addFriendship(ReadFile.readFriends(currentDir + inFilePath)) + " friendships added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    // Prints users to outFilePath
    static Command saveUsers = () -> {
        try {
            WriteFile.write(currentDir + outFilePath, UserMap.print());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Users saved to " + currentDir + outFilePath + ".");
    };
    // Prints friendships to outFilePath
    static Command saveFriends = () -> {
        try {
            WriteFile.write(currentDir + outFilePath, FriendshipMap.print());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Friends saved to " + currentDir + outFilePath + ".");
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
            Residential.findPeopleHometown(currentDir + inFilePath);
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
                    WriteFile.write(currentDir + outFilePath, toFile.toString());
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
    //Retrieve the people who were born between dates D1 and D2
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
    //Finds shortest path between two people
    static Command shortestPath = () -> {
        User[] users = askUsers();
        if (!users[0].equals(users[1])) {
            if (shortestChain.BFS(users[0], users[1]) != 0) {
                System.out.println(shortestChain.BFS(users[0], users[1]));
            } else {
                System.out.println("User " + users[0].getId() + " and user " + users[1].getId() + " are not connected.");

            }
        }
        else{
            System.out.println(0);
        }
    };
    //Finds longest path between two people
    static Command longestPath = () -> {
        User[] users = askUsers();
        int result = longestChain.findLongest(users[0], users[1]);
        if ( result != 0) {
            System.out.println(result);
        } else if (result == 0 && users[1].equals(users[0])){
            System.out.println(result);
        }
        else{
            System.out.println("User " + users[0].getId() + " and user " + users[1].getId() + " are not connected.");
        }
    };
    //Finds all cliques bigger than 4 people
    static Command findClique = () -> {
        Set<Clique> allPossibleCliques = new HashSet<>();
        ArrayList<User> allUsers = UserMap.allUsers();
        for (User u:allUsers){
            ArrayList<Clique> userCliques = findCliques.findCliq(u);
            allPossibleCliques.addAll(userCliques);
        }

        for (Clique c:allPossibleCliques){
            System.out.println(c);
        }
    };
        // App configuration variables
        // Names for the accepted commands
        static String[] commandNames = {"readUsers", "readFriends", "saveUsers", "saveFriends", "setRead", "setWrite",
                "search", "printUsers", "printFriends", "readResidential", "friendsOfUser", "listOfMovieClasses", "bornBetween",
                "shortestPath", "longestPath","findClique","setCurrentDir"};
        // Test to display to user explaining options
        static String[] options = {"Read users from file", "Read friendship relationships from file",
                "Save users to a file", "Save friendships to a file", "Set read file path", "Set write file path",
                "Search users", "Displays all user data", "Displays all friendship relationships.", "Find all people from the same town as people in the file.",
                "Find friends of the user", "Splits users into classes based on their favorite movies and creates a list", "Retrieve the people who were born between dates D1 and D2",
                "Retrieves the shortest chain relating two people", "Retrieves the longest chain relating two people", "Finds all cliques of friends bigger than 4 users","Changes current working directory."};
        // Functions to be called upon command execution
        static Command[] test = {readUsers, readFriendships, saveUsers, saveFriends, setRead, setWrite, search, printUsers,
                printFriends, readResidential, friendsOfUser, listOfMovieClasses, bornBetween, shortestPath, longestPath, findClique, setCurrentDir};
        // App instance declaration
        static CliApliBase app;

        public static void main (String[]args) throws Exception {
            // Initialize app
            app = new CliApliBase(commandNames, test, options);
            // Set setCurrentDir to be executed at the start by default
            app.setInitAction(setCurrentDir);
            // Set the initial message of the aplication
            app.setStartMessage(
                    "Welcome to the programming project for DSA made by the group G611837. This program emulates a social network.\n"
                            + "-------------------------------------------------------------------------------------------------------------");
            // Main loop
            app.startReading();

            sc.close();
        }
}
