package main.java.com.codepath.salesforce;

public enum CommandResponse {
    COMMAND("Command:"),
    COLON(":"),
    DIR("dir"),
    UP("up"),
    MKDIR("mkdir"),
    NOT_EXIST("Subdirectory does not exist"),
    ALREADY_EXIST("Subdirectory already exists"),
    DISPLAY_DIRECTOR("Directory of"),
    CANNOT_MOVE_UP("Cannot move up from root directory"),
    BACK_SLASH("\\\\"),
    BACK_SLASH_SINGLE("\\"),
    NEW_LINE("\n"),
    NO_SUBDIRECTORIES("No subdirectories"),
    ROOT("root"),
    WHITE_SPACE(" ");

    private String command;

    CommandResponse(String response) {
        this.command = response;
    }

    public String getCommand() {
       return this.command;
    }
}
