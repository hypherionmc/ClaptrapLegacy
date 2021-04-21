package me.hypherionmc.claptrap.commands.curseforge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.SneakyThrows;
import me.hypherionmc.claptrap.util.DiscordBotDatabase;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CurseforgeUntrackCommand extends Command {

    public CurseforgeUntrackCommand() {
        this.name = "!cfuntrack";
        this.aliases = new String[]{"!cfuntrack"};
        this.help = "This command untracks a CurseForge Project";
    }

    @SneakyThrows
    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription(this.help);
            builder.addField("ct!cfuntrack <projectid>", "Untracks a stored CurseForge Project", true);
            event.reply(builder.build());
        } else {
            String[] args = event.getArgs().split(" ");
            if (DiscordBotDatabase.deleteCurseforgeProject(Integer.parseInt(args[0]), event.getGuild().getId(), event.getChannel().getId())) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Project " + args[0] + " will no longer be tracked");
                builder.setColor(Color.GREEN);
                event.reply(builder.build());
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Could not untrack project " + args[0] + ". Double check the project ID and check if it exists in the list of tracked projects");
                builder.setColor(Color.RED);
                event.reply(builder.build());
            }
        }
    }

}
