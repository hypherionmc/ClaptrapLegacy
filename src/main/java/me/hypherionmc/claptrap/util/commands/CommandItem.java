package me.hypherionmc.claptrap.util.commands;

import com.jagrosh.jdautilities.command.Command;

public class CommandItem {
    private final String commandString;
    private final Command commandClass;
    private final Boolean isEnabled;

    public CommandItem(String command, Command commandClass, Boolean isEnabled) {
        this.commandString = command;
        this.commandClass = commandClass;
        this.isEnabled = isEnabled;
    }

    public String getCommandString() {
        return commandString;
    }

    public Command getCommandClass() {
        return commandClass;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }
}
