package me.hypherionmc.claptrap.commands.curseforge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.therandomlabs.curseapi.project.CurseProject;
import lombok.SneakyThrows;
import me.hypherionmc.claptrap.util.CurseForgeHelper;
import me.hypherionmc.claptrap.util.DiscordBotDatabase;
import me.hypherionmc.claptrap.util.databasehelpers.CurseProjectItem;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CurseforgeListCommand extends Command {

    public CurseforgeListCommand() {
        this.name = "!cflist";
        this.aliases = new String[]{"!cflist"};
        this.help = "This command lists all the tracked CurseForge Projects in the current channel";
    }

    @SneakyThrows
    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Tracked CurseForge Projects");

        for (CurseProjectItem item : DiscordBotDatabase.loadCurseforgeProjects(event.getGuild().getId(), event.getChannel().getId())) {
            CurseProject project = CurseForgeHelper.getProjectInfo(item.getProjectID());
            builder.setColor(Color.GREEN);
            builder.addField(project.name() + " (" + item.getProjectID().toString() + ")", project.url().toString(), false);
        }

        if (DiscordBotDatabase.loadCurseforgeProjects(event.getGuild().getId(), event.getChannel().getId()).isEmpty()) {
            builder.setColor(Color.red);
            builder.setDescription("No projects tracked here");
        }
        //builder.setFooter(References.BOT_FOOTER);
        event.reply(builder.build());
    }
}
