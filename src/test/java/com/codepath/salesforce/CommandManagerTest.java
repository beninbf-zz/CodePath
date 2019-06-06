package test.java.com.codepath.salesforce;


import main.java.com.codepath.salesforce.Command;
import main.java.com.codepath.salesforce.CommandManager;
import main.java.com.codepath.salesforce.CommandResponse;
import org.junit.Before;
import org.junit.Test;

public class CommandManagerTest {

    private CommandManager testObj;

    @Before
    public void setUp() {
        this.testObj = new CommandManager();
    }

    @Test
    public void testCommandManager() {
        String[] input = {
            "dir",
            "mkdir sub601",
            "mkdir sub601",
            "mkdir sub601",
            "mkdir sub601"
        };

        for (String s: input) {
            String[] inputCommands = s.split(CommandResponse.WHITE_SPACE.getCommand(), 2);
            Command command = Command.findCommand((inputCommands[0]));
            if (command != null) {
                switch (command) {
                    case UP:
                        System.out.println(this.testObj.up());
                        break;
                    case DIR:
                        System.out.println(this.testObj.dir());
                        break;
                    case MKDIR:
                        System.out.println(this.testObj.mkdir(inputCommands[1]));
                        break;
                    case CD:
                        System.out.println(this.testObj.changeDirectory(inputCommands[1]));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Test
    public void testCommandManager1() {
        String[] input = {
            "dir",
            "mkdir sub601",
            "mkdir sub602",
            "mkdir sub603",
            "mkdir sub604",
            "dir"
        };

        for (String s: input) {
            String[] inputCommands = s.split(CommandResponse.WHITE_SPACE.getCommand(), 2);
            Command command = Command.findCommand((inputCommands[0]));
            if (command != null) {
                switch (command) {
                    case UP:
                        System.out.println(this.testObj.up());
                        break;
                    case DIR:
                        System.out.println(this.testObj.dir());
                        break;
                    case MKDIR:
                        System.out.println(this.testObj.mkdir(inputCommands[1]));
                        break;
                    case CD:
                        System.out.println(this.testObj.changeDirectory(inputCommands[1]));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Test
    public void testCommandManager2() {
        String[] input = {
            "up",
            "mkdir sub601",
            "mkdir sub602",
            "mkdir sub603",
            "mkdir sub604",
            "cd sub601",
            "dir",
            "up",
            "dir"
        };

        for (String s: input) {
            String[] inputCommands = s.split(CommandResponse.WHITE_SPACE.getCommand(), 2);
            Command command = Command.findCommand((inputCommands[0]));
            if (command != null) {
                switch (command) {
                    case UP:
                        System.out.println(this.testObj.up());
                        break;
                    case DIR:
                        System.out.println(this.testObj.dir());
                        break;
                    case MKDIR:
                        if (inputCommands.length == 2) {
                            System.out.println(this.testObj.mkdir(inputCommands[1]));
                        }
                        break;
                    case CD:
                        if (inputCommands.length == 2) {
                            String response = this.testObj.changeDirectory(inputCommands[1]);
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

    @Test
    public void testCommandManager3() {
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

        for (String s: input) {
            String[] inputCommands = s.split(CommandResponse.WHITE_SPACE.getCommand(), 2);
            Command command = Command.findCommand((inputCommands[0]));
            if (command != null) {
                switch (command) {
                    case UP:
                        System.out.println(this.testObj.up());
                        break;
                    case DIR:
                        System.out.println(this.testObj.dir());
                        break;
                    case MKDIR:
                        if (inputCommands.length == 2) {
                            System.out.println(this.testObj.mkdir(inputCommands[1]));
                        }
                        break;
                    case CD:
                        if (inputCommands.length == 2) {
                            String response = this.testObj.changeDirectory(inputCommands[1]);
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
