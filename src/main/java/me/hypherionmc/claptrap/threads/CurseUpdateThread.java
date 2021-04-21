package me.hypherionmc.claptrap.threads;

import com.therandomlabs.curseapi.CurseException;
import com.therandomlabs.curseapi.project.CurseProject;
import me.hypherionmc.claptrap.ClaptrapBot;
import me.hypherionmc.claptrap.util.CurseForgeHelper;
import me.hypherionmc.claptrap.util.DiscordBotDatabase;
import me.hypherionmc.claptrap.util.SizeConverter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class CurseUpdateThread extends Thread {

    @Override
    public void run() {
        while (true) {

            ClaptrapBot.cache.forEach((curseProject -> {
                CurseProject project = CurseForgeHelper.getProjectInfo(curseProject.getProjectID());
                try {
                    if (curseProject.getVersion() != project.files().first().id()) {

                        TextChannel channel = ClaptrapBot.jda.getTextChannelById(curseProject.getChannelID());
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setAuthor(project.name(), null, project.logo().url().toString());
                        builder.setDescription("New release");
                        builder.setColor(Color.GREEN);
                        builder.addField("Release Type", project.files().first().releaseType().toString(), true);
                        builder.addField("File name", project.files().first().nameOnDisk(), true);
                        builder.addField("Display name", project.files().first().displayName(), true);
                        builder.addField("Game Version(s)", project.files().first().gameVersionStrings().toString(), true);
                        builder.addField("Size", SizeConverter.humanReadableByte(project.files().first().fileSize()), false);

                        if (!project.files().first().changelogPlainText().isEmpty()) {
                            builder.addField("Changelog", project.files().first().changelogPlainText(), false);
                        }

                        builder.addField("Download", project.files().first().url().toString(), false);
                        channel.sendMessage(builder.build()).queue();

                        System.out.println("New update found!");

                        DiscordBotDatabase.updateCache(curseProject.getDbID(), project.files().first().id());
                        ClaptrapBot.cache.clear();
                        ClaptrapBot.cache = DiscordBotDatabase.getCurseforgeCache();
                        sleep(TimeUnit.SECONDS.toMillis(1000));

                    }
                } catch (CurseException | InterruptedException | SQLException e) {

                    e.printStackTrace();
                    this.resume();

                }
            }));

        }
    }
}
