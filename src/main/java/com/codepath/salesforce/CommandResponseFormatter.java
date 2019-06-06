package main.java.com.codepath.salesforce;

import java.util.List;


public class CommandResponseFormatter {

    private static final int ARGUMENT_WIDTH = 9;

    private static final int LINE_CAPACITY = 10;

    private static final int COMMAND_SPACE_LIMIT = 18;

    public CommandResponseFormatter() {}

    public String formatOutput(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        int length = strings.size();
        int count = 0;
        for (int i = 0; i < length; i++) {
            String str = strings.get(i);
            sb.append(getFormattedString(str));
            count++;
            if (count == LINE_CAPACITY) {
                sb.append(CommandResponse.NEW_LINE.getCommand());
                count = 0;
            }
        }
        return sb.toString();
    }

    public String getWhiteSpaces(int numberOfWhiteSpace) {
        StringBuilder sb = new StringBuilder();
        while (numberOfWhiteSpace != 0) {
            sb.append(CommandResponse.WHITE_SPACE.getCommand());
            numberOfWhiteSpace--;
        }
        return sb.toString();
    }

    public String getCommandResponse(CommandResponse command, String directoryPathWay) {
        StringBuilder sb = new StringBuilder();
        sb.append(CommandResponse.COMMAND.getCommand());
        sb.append(CommandResponse.WHITE_SPACE.getCommand());
        sb.append(command.getCommand());
        sb.append(CommandResponse.WHITE_SPACE.getCommand());

        // subtract 1 for the 0 offset
        int whiteSpaces = COMMAND_SPACE_LIMIT - 1 - sb.length();
        sb.append(getWhiteSpaces(whiteSpaces));
        if (directoryPathWay != null) {
            sb.append(directoryPathWay);
        }
        return sb.toString();
    }

    public String directoryAlreadyExists() {
        return CommandResponse.ALREADY_EXIST.getCommand();
    }

    public String directoryDoesntExist() {
        return CommandResponse.NEW_LINE.getCommand();
    }

    public String getDirectoryOf(String directoryPath) {
        StringBuilder sb = new StringBuilder();
        sb.append(CommandResponse.DISPLAY_DIRECTOR.getCommand());
        sb.append(CommandResponse.WHITE_SPACE.getCommand());
        String dir = directoryPath == null ? "root" : directoryPath;
        sb.append(dir);
        sb.append(CommandResponse.COLON.getCommand());
        return sb.toString();
    }

    private String getFormattedString(String str) {
        StringBuilder sb = new StringBuilder();
        int whiteSpaces = ARGUMENT_WIDTH - str.length();
        sb.append(str);
        sb.append(this.getWhiteSpaces(whiteSpaces));
        return sb.toString();
    }
}
