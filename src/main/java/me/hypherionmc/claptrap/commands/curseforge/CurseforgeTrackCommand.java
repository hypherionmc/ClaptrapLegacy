package me.hypherionmc.claptrap.commands.curseforge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.therandomlabs.curseapi.project.CurseProject;
import me.hypherionmc.claptrap.util.CurseForgeHelper;
import me.hypherionmc.claptrap.util.DiscordBotDatabase;
import me.hypherionmc.claptrap.util.References;
import me.hypherionmc.claptrap.util.databasehelpers.CurseProjectItem;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CurseforgeTrackCommand extends Command {

    public CurseforgeTrackCommand() {
        this.name = "!cftrack";
        this.aliases = new String[]{""};
        this.help = "This command allows you to track a CurseForge project for updates and changes";
    }

    @lombok.SneakyThrows
    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Claptrap CurseForge");
            builder.setDescription(this.help);
            builder.setColor(Color.GREEN);
            builder.addField("ct!cftrack <projectid>", "Tracks a specific project. You need to provide the project ID", false);
            builder.setFooter(References.BOT_FOOTER);
            event.reply(builder.build());
        } else {
            String[] args = event.getArgs().split(" ");
            if (CurseForgeHelper.curseProjectExists(Integer.parseInt(args[0]))) {
                CurseProject project = CurseForgeHelper.getProjectInfo(Integer.parseInt(args[0]));
                CurseProjectItem itm = new CurseProjectItem(0, Integer.parseInt(args[0]), event.getGuild().getId(), event.getChannel().getId(), event.getAuthor().getId(), project.name(), project.files().first().id());
                if (DiscordBotDatabase.addCurseforgeProject(itm)) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Found project by " + project.author().name());
                    builder.setColor(Color.GREEN);
                    builder.addField(project.name() + " (" + project.id() + ")", "Project will be tracked", false);
                    event.reply(builder.build());
                    return;
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Something went wrong in adding your project. It could be that it's already tracked. Check ct!cflist to see if it exists otherwise please contact the bot owner");
                    builder.setColor(Color.red);
                    event.reply(builder.build());
                    return;
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("CurseForge Project " + args[0] + " cannot be located. Are you sure it's correct?");
                builder.setColor(Color.red);
                event.reply(builder.build());
                return;
            }
        }
    }
}
