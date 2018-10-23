package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliApliBase {
	// Basic Interface
	public static interface Command {
		public void performAction();
	}

	// Others
	Scanner sc = new Scanner(System.in);
	private static final Exception InvalidArgumenException = null;

	private Map<String, Command> commands = new HashMap<>();
	// Command functionality
	private Command[] functions;
	private String[] options;
	private String[] commandNames;
	private String[] exitKey = { "q", "exit", "quit" };
	// Messages & Visualization
	private String startMessage = "Welcome to this command line interface based application!";
	private String mainMessage = "Please input the either the option number or the corresponding command.";
	private String endMessage = "Goodbye!";
	private int maxOptionLength;

	private Command initAction, endAction;
	private boolean finished = false;

	// Constructor
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
		maxOptionLength = findMaxOptionLength();
	}

	// One liner to handle starting and ending the app, along with a continuous
	// reading from user
	public void startReading() {
		startApp();

		while (!isFinished())
			readUserInput();

		endApp();

	}

	// Prints options and reads user input
	public void readUserInput() {
		while (true) {
			// Print
			printInfo();
			// Handles input
			String input = sc.next();
			boolean isNumber = isNumber(input);
			if (isExitKey(input)) {
				finished = true;
				sc.close();
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
		for (int i = 0; i < functions.length; i++) {
			String aux = new String(new char[maxOptionLength - options[i].length()]).replace("\0", " ");
			System.out.println((i + 1) + "-" + options[i] + aux + commandNames[i]);
		}
	}

	// Performs actions to be executed only at the start
	private void startApp() {
		System.out.println(startMessage);
		if (initAction != null)
			initAction.performAction();
	}

	// Performs actions to be executed only at the end
	private void endApp() {
		System.out.println(endMessage);
		if (endAction != null)
			endAction.performAction();
	}

	// Finds and returns the length of the largest option message
	private int findMaxOptionLength() {
		int max = 0;
		for (String s : options)
			max = (s.length() > max) ? s.length() : max;
		return (max > 45) ? max + 5 : 50;
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

	public void setStartMessage(String startMessage) {
		this.startMessage = startMessage;
	}

	public void setExitKey(String[] exitKey) {
		this.exitKey = exitKey;
	}

	public void setInitAction(Command in) {
		initAction = in;
	}

	public void setEndAction(Command end) {
		endAction = end;
	}
}
