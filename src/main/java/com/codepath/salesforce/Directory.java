package main.java.com.codepath.salesforce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Directory {
    private Map<String, Directory> innerDirectories;

    private String label;

    private Directory parent;

    private CommandResponseFormatter cmdFormatter;

    public Directory(String label, Directory parent) {
        this.innerDirectories = new HashMap<>();
        this.cmdFormatter = new CommandResponseFormatter();
        this.label = label;
        this.parent = parent;
    }

    public String dir() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.cmdFormatter.getCommandResponse(CommandResponse.DIR, null));
        sb.append(CommandResponse.NEW_LINE.getCommand());



        String relativeLocation = getRelativeLocation();
        sb.append(cmdFormatter.getDirectoryOf(relativeLocation));

        sb.append(CommandResponse.NEW_LINE.getCommand());

        Set<String> keys = this.innerDirectories.keySet();
        if (keys.isEmpty()) {
            sb.append(CommandResponse.NO_SUBDIRECTORIES.getCommand());
            return sb.toString();
        }
        List<String> listOfDir = new ArrayList<>(keys);
        Collections.sort(listOfDir);
        sb.append(cmdFormatter.formatOutput(listOfDir));


        return sb.toString();
    }

    private String getRelativeLocation() {
        Stack<String> pathWay = new Stack<>();
        StringBuilder sb = new StringBuilder();
        Directory current = this;
        while (current != null) {
            if (current.label != null) {
                pathWay.add(current.label);
            } else {
                pathWay.add(CommandResponse.ROOT.getCommand());
            }
            current = current.parent;
        }
        String prefix = "";
        while (!pathWay.empty()) {
            sb.append(prefix);
            sb.append(pathWay.pop());
            prefix = CommandResponse.BACK_SLASH_SINGLE.getCommand();
        }
        return sb.toString();
    }


    public String mkdir(String directoryPathWay) {
        String[] directories = directoryPathWay.split(CommandResponse.BACK_SLASH.getCommand());
        Map<String, Directory> current = this.innerDirectories;
        Directory parent = this;
        for (String dir: directories) {
            if (!current.containsKey(dir)) {
                Directory directory = new Directory(dir, parent);
                parent = directory;
                current.put(dir, directory);
                current = directory.getInnerDirectories();
            } else {
                return this.cmdFormatter.directoryAlreadyExists();
            }
        }
        return this.cmdFormatter.getCommandResponse(CommandResponse.MKDIR, directoryPathWay);
    }

    public Directory cd(String directoryPathWay)  {
        String[] directories = directoryPathWay.split(CommandResponse.BACK_SLASH.getCommand());
        Map<String, Directory> current = this.innerDirectories;
        Directory directory = this;
        for (String dir: directories) {
            if (!current.containsKey(dir)) {
                return null;
            } else {
                directory = current.get(dir);
            }
        }
        return directory;
    }

    public Map<String, Directory> getInnerDirectories() {
        return this.innerDirectories;
    }

    public Directory up() {
        return this.parent;
    }
}
