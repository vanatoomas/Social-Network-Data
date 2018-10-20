package project;

import java.io.IOException;
import java.util.Scanner;

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
	static Command readUsers = () -> {
		try {
			project.ReadFile.readUsers(inFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Users added");
	};
	static Command saveUsers = () -> {
		System.out.println("Users saved.");
	};
	static Command setRead = () -> {
		String newPath = sc.next();
		if (askYesNo("Is " + newPath + " the path you want to set as the read path?")) {
			inFilePath = newPath;
			System.out.println("Updated file path to read.");
		} else
			System.out.println("File path to read not updated.");
	};
	static Command setWrite = () -> {
		String newPath = sc.next();
		if (askYesNo("Is " + newPath + " the path you want to set as the write path?")) {
			outFilePath = newPath;
			System.out.println("Updated file path to write.");
		} else
			System.out.println("File path to write not updated.");
	};
	static Command search = () -> {
		System.out.println("This are the users that match");
	};

	// App configuration variables
	// Names for the accepted commands
	static String[] commandNames = { "readUsers", "saveUsers", "setRead", "setWrite", "search" };
	// Test to display to user explaining options
	static String[] options = { "Read users from file", "Save users to a file", "Set read file path",
			"Set write file path", "Search users" };
	// Functions to be called upon command execution
	static Command[] test = { readUsers, saveUsers, setRead, setWrite, search };
	// App instance declaration
	static CliApliBase app;

	public static void main(String[] args) throws Exception {
		app = new CliApliBase(commandNames, test, options);
		// Main loop
		while (!app.isFinished())
			app.readUserInput();

		sc.close();
	}

}
