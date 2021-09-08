package by.epamtc.sinitsyna.controller.command;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();

	private CommandProvider() {
		commands.put(CommandName.VALIDATION, new ValidationCommand());
		commands.put(CommandName.PARSING, new ParsingCommand());
		commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
	}

	private static class SingletonHelper {
		private static final CommandProvider INSTANCE = new CommandProvider();
	}

	public static CommandProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public Command getCommand(String commandName) {
		CommandName command = CommandName.valueOf(commandName.toUpperCase());
		if (command == null) {
			return commands.get(CommandName.NO_SUCH_COMMAND);
		}
		return commands.get(command);
	}
}
