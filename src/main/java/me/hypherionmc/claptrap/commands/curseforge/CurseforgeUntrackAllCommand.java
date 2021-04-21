package me.hypherionmc.claptrap.commands.curseforge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.SneakyThrows;
import me.hypherionmc.claptrap.util.DiscordBotDatabase;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CurseforgeUntrackAllCommand extends Command {

    public CurseforgeUntrackAllCommand() {
        this.name = "!cfuntrackall";
        this.aliases = new String[]{"!cfuntrackall"};
        this.help = "This command untracks all CurseForge Projects in a channel";
    }

    @SneakyThrows
    @Override
    protected void execute(CommandEvent event) {
            String[] args = event.getArgs().split(" ");
            if (DiscordBotDatabase.deleteCurseforgeProjects(event.getGuild().getId(), event.getChannel().getId())) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("All projects have been untracked.");
                builder.setColor(Color.GREEN);
                event.reply(builder.build());
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Could not untrack all projects. Either there are no projects or something went wrong");
                builder.setColor(Color.RED);
                event.reply(builder.build());
            }
    }
}
