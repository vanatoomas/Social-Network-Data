package project;

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
        String newPath = sc.next();
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
        String value = sc.next();
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

    // App configuration variables
    // Names for the accepted commands
    static String[] commandNames = { "readUsers", "readFriends", "saveUsers", "saveFriends", "setRead", "setWrite",
            "search", "printUsers", "printFriends", "readResidential" };
    // Test to display to user explaining options
    static String[] options = { "Read users from file", "Read friendship relationships from file",
            "Save users to a file", "Save friendships to a file", "Set read file path", "Set write file path",
            "Search users", "Displays all user data", "Displays all friendship relationships.", "Find all people from the same town as people in the file." };
    // Functions to be called upon command execution
    static Command[] test = { readUsers, readFriendships, saveUsers, saveFriends, setRead, setWrite, search, printUsers,
            printFriends, readResidential };
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
