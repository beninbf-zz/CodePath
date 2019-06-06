package main.java.com.codepath.salesforce;

public class CommandManager {

    private Directory directory;

    public CommandManager() {
        this.directory = new Directory(null, null);
    }
    public String changeDirectory(String directory) {
        Directory dir = this.directory.cd(directory);
        if (dir == null) {
            return CommandResponse.NOT_EXIST.getCommand();
        }
        this.directory = dir;
        return "";
    }

    public String mkdir(String directory) {
        return this.directory.mkdir(directory);
    }

    public String dir() {
        return this.directory.dir();
    }

    public String up() {
        Directory directory = this.directory.up();
        if (directory != null) {
            this.directory = directory;
        }
        CommandResponseFormatter cmd = new CommandResponseFormatter();
        StringBuilder sb = new StringBuilder();
        sb.append(cmd.getCommandResponse(CommandResponse.UP, null));
        if (directory == null) {
            sb.append(CommandResponse.CANNOT_MOVE_UP.getCommand());
        }
        return sb.toString();
    }
}
