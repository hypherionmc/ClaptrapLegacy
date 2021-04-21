package me.hypherionmc.claptrap;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.hypherionmc.claptrap.commands.curseforge.CurseforgeListCommand;
import me.hypherionmc.claptrap.commands.curseforge.CurseforgeTrackCommand;
import me.hypherionmc.claptrap.commands.curseforge.CurseforgeUntrackAllCommand;
import me.hypherionmc.claptrap.commands.curseforge.CurseforgeUntrackCommand;
import me.hypherionmc.claptrap.threads.CurseUpdateThread;
import me.hypherionmc.claptrap.util.DiscordBotDatabase;
import me.hypherionmc.claptrap.util.References;
import me.hypherionmc.claptrap.util.commands.CommandItem;
import me.hypherionmc.claptrap.util.commands.CommandRegistration;
import me.hypherionmc.claptrap.util.databasehelpers.CurseProjectItem;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.cli.*;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.*;

public class ClaptrapBot {
    public static JDA jda;
    public static List<CurseProjectItem> cache = new ArrayList<>();

    public static List<CommandItem> commands = new ArrayList<CommandItem>() {{

        add(new CommandItem("cftrack", new CurseforgeTrackCommand(), true));
        add(new CommandItem("cflist", new CurseforgeListCommand(), true));
        add(new CommandItem("cfuntrack", new CurseforgeUntrackCommand(), true));
        add(new CommandItem("cfuntrackall", new CurseforgeUntrackAllCommand(), true));

    }};


    public static void main(String[] args) throws LoginException, ParseException {

        final Options o = new Options();
        final Option token = new Option("token", true, "Provides the bot token");

        token.setRequired(true);
        o.addOption(token);

        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(o, args);

        String botkey = "";
        if (line.hasOption("token")) {
            botkey = line.getOptionValue("token");
        }

        jda = new JDABuilder(AccountType.BOT).setToken(botkey).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId("354707828298088459");
        builder.setPrefix(References.BOT_PREFIX);
        CommandRegistration.RegisterCommands(builder, commands);
        builder.setHelpWord("!help");
        CommandClient client = builder.build();
        jda.addEventListener(client);
        DiscordBotDatabase.createNewDatabase();

        try {
            cache = DiscordBotDatabase.getCurseforgeCache();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        new CurseUpdateThread().start();

    }

}
