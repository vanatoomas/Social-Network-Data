package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliApliBase {
	public static interface Command {
		public void performAction();
	}

	Scanner sc = new Scanner(System.in);
	private static final Exception InvalidArgumenException = null;

	private Map<String, Command> commands = new HashMap<>();

	private Command[] functions;
	private String[] options;
	private String[] commandNames;
	private String[] exitKey = { "q", "exit", "quit" };;
	private boolean finished = false;

	private String mainMessage = "Please input the either the option number or the corresponding command.";
	private String endMessage = "Goodbye!";

	// Minimal constructor
	public CliApliBase(String[] commandNames, Command[] functions, String[] options) throws Exception {
		// Checks for critical arguments' correctness
		if (functions.length != commandNames.length || functions.length != options.length) {
			throw InvalidArgumenException;
		}
		// Sets up main data structure
		this.functions = functions;
		this.commandNames = commandNames;
		for (int i = 0; i < functions.length; i++) {
			commands.put(commandNames[i], functions[i]);
		}
		this.options = options;
	}

	// Prints options and reads user input
	public void readUserInput() {
		// Instantiates Scanner
		while (true) {
			// Print
			printInfo();
			// Handles input
			String input = sc.next();
			boolean isNumber = isNumber(input);
			if (isExitKey(input)) {
				System.out.println(endMessage);
				finished = true;
				break;
			} else if (handleInputString(input, isNumber))
				break;

		}
	}

	// Performs appropriate action to user input
	private boolean handleInputString(String input, boolean isNumber) {
		boolean output;
		if (isNumber) {
			if (Integer.parseInt(input) - 1 >= 0 && Integer.parseInt(input) - 1 < functions.length) {
				functions[Integer.parseInt(input) - 1].performAction();
				output = true;
			} else {
				output = false;
				System.out.println("Invalid Number.");
			}
		} else {
			if (commands.containsKey(input.trim())) {
				commands.get(input.trim()).performAction();
				output = true;
			} else {
				output = false;
				System.out.println("Command not recognised");
			}
		}
		return output;
	}

	// Returns true if input is contained in exitKey
	private boolean isExitKey(String input) {
		for (String s : exitKey) {
			if (s.equals(input))
				return true;
		}
		return false;
	}

	// Returns true if input string represents an integer
	private static boolean isNumber(String str) {
		try {
			@SuppressWarnings("unused")
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	// Prints to std out the corresponding number and commandName for every option
	private void printInfo() {
		System.out.println(mainMessage);
		for (int i = 0; i < functions.length; i++)
			System.out.println((i + 1) + "-" + options[i] + "      " + commandNames[i]);
	}

	// Various Getters & Setters
	public boolean isFinished() {
		return finished;
	}

	public void setMainMessage(String mainMessage) {
		this.mainMessage = mainMessage;
	}

	public void setEndMessage(String endMessage) {
		this.endMessage = endMessage;
	}

	public void setExitKey(String[] exitKey) {
		this.exitKey = exitKey;
	}

}
