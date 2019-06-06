package main.java.com.codepath.salesforce;

public enum Command {
    UP("up"),
    DIR("dir"),
    MKDIR("mkdir"),
    CD("cd");

    private String command;

    Command(String command) {
        this.command = command;
    }

    public static Command findCommand(String command) {
        for (Command cmd: Command.values()) {
            if (cmd.getCommand().equals(command)) {
                return cmd;
            }
        }
        return null;
    }

    public String getCommand() {
        return this.command;
    }
}
