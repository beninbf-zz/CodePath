package main.java.com.codepath.salesforce;

public class Main {

    public static void main(String[] args) {
        String[] input = {
            "up",
            "mkdir sub601",
            "mkdir sub602",
            "mkdir sub603",
            "mkdir sub604",
            "cd sub601",
            "dir",
            "up",
            "dir",
            "cd sub601",
            "mkdir sub606",
            "cd sub606",
            "dir"
        };

        CommandManager cm = new CommandManager();
        for (String s: input) {
            String[] inputCommands = s.split(CommandResponse.WHITE_SPACE.getCommand(), 2);
            Command command = Command.findCommand((inputCommands[0]));
            if (command != null) {
                switch (command) {
                    case UP:
                        System.out.println(cm.up());
                        break;
                    case DIR:
                        System.out.println(cm.dir());
                        break;
                    case MKDIR:
                        if (inputCommands.length == 2) {
                            System.out.println(cm.mkdir(inputCommands[1]));
                        }
                        break;
                    case CD:
                        if (inputCommands.length == 2) {
                            String response = cm.changeDirectory(inputCommands[1]);
                            if (!response.isEmpty()) {
                                System.out.println(response);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
