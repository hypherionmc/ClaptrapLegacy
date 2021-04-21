package me.hypherionmc.claptrap.util.commands;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

import java.util.List;

public class CommandRegistration {

    public static void RegisterCommands(CommandClientBuilder builder, List<CommandItem> commands) {

        for (CommandItem cmd : commands) {
            if (cmd.getEnabled()) {
                builder.addCommand(cmd.getCommandClass());
            }
        }
    }

}
