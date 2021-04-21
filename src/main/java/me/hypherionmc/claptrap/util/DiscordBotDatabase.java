package me.hypherionmc.claptrap.util;

import me.hypherionmc.claptrap.util.databasehelpers.CurseProjectItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscordBotDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordBotDatabase.class);

    public static void createNewDatabase() {

        try (Connection conn = DriverManager.getConnection(References.BOT_DB)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(References.BOT_DB);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean addCurseforgeProject(CurseProjectItem project) throws SQLException {
        // language=SQLite
        String initsql = "CREATE TABLE IF NOT EXISTS curseforge (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "guild_id VARCHAR(20) NOT NULL," +
                "projectid INTEGER NOT NULL," +
                "authorid VARCHAR(100) NOT NULL," +
                "channelid INTEGER NOT NULL," +
                "projecttitle VARCHAR(100) NOT NULL," +
                "projectver INTEGER NOT NULL" +
                ");";

        Statement statement = getConnection().createStatement();
        statement.execute(initsql);
        LOGGER.info("CurseForge Table initialised");

        ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM curseforge WHERE projectid = '" + project.getProjectID() + "' AND channelid = '" + project.getChannelID() +  "' AND guild_id = '" + project.getGuild_id() + "'");
        if (rs.getInt("total") > 0) {
            System.out.println("LINK EXISTS");
            return false;
        }

        String sql = "INSERT INTO curseforge (guild_id, projectid, authorid, channelid, projecttitle, projectver) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = statement.getConnection().prepareStatement(sql);
        stmt.setString(1, project.getGuild_id());
        stmt.setInt(2, project.getProjectID());
        stmt.setString(3, project.getAuthorID());
        stmt.setString(4, project.getChannelID());
        stmt.setString(5, CurseForgeHelper.getProjectInfo(project.getProjectID()).name());
        stmt.setInt(6, project.getVersion());

        //addCurseProjectCache(statement, project.getGuild_id(), project.getChannelID(), CurseForgeHelper.getProjectInfo(project.getProjectID()));
        Boolean res = !stmt.execute();
        System.out.println("CURSEFORGE ADDED STATUS " + res);

        return res;
    }

    public static boolean deleteCurseforgeProject(Integer projectID, String guild_id, String channelid) throws SQLException {
        // language=SQLite
        String initsql = "CREATE TABLE IF NOT EXISTS curseforge (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "guild_id VARCHAR(20) NOT NULL," +
                "projectid INTEGER NOT NULL," +
                "authorid VARCHAR(100) NOT NULL," +
                "channelid INTEGER NOT NULL," +
                "projecttitle VARCHAR(100) NOT NULL," +
                "projectver INTEGER NOT NULL" +
                ");";

        Statement statement = getConnection().createStatement();
        statement.execute(initsql);
        LOGGER.info("CurseForge Table initialised");

        return !statement.execute("DELETE FROM curseforge WHERE projectid = '" + projectID + "' AND guild_id = '" + guild_id + "' AND channelid = '" + channelid + "'");
    }

    public static List<CurseProjectItem> loadCurseforgeProjects(String guildID, String channelID) throws SQLException {
        List<CurseProjectItem> items = new ArrayList<>();
        // language=SQLite
        String initsql = "CREATE TABLE IF NOT EXISTS curseforge (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "guild_id VARCHAR(20) NOT NULL," +
                "projectid INTEGER NOT NULL," +
                "authorid VARCHAR(100) NOT NULL," +
                "channelid INTEGER NOT NULL," +
                "projecttitle VARCHAR(100) NOT NULL," +
                "projectver INTEGER NOT NULL" +
                ");";

        Statement statement = getConnection().createStatement();
        statement.execute(initsql);
        LOGGER.info("CurseForge Table initialised");

        ResultSet rs = statement.executeQuery("SELECT * FROM curseforge WHERE guild_id = '" + guildID + "' and channelid = '" + channelID + "'");
        while (rs.next()) {
            CurseProjectItem itm = new CurseProjectItem(rs.getInt("id"), rs.getInt("projectid"), rs.getString("guild_id"), rs.getString("channelid"), rs.getString("authorid"), rs.getString("projecttitle"), rs.getInt("projectver"));
            items.add(itm);
        }
        return items;
    }

    public static boolean deleteCurseforgeProjects(String guild_id, String channelid) throws SQLException {
        // language=SQLite
        String initsql = "CREATE TABLE IF NOT EXISTS curseforge (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "guild_id VARCHAR(20) NOT NULL," +
                "projectid INTEGER NOT NULL," +
                "authorid VARCHAR(100) NOT NULL," +
                "channelid INTEGER NOT NULL," +
                "projecttitle VARCHAR(100) NOT NULL," +
                "projectver INTEGER NOT NULL" +
                ");";

        Statement statement = getConnection().createStatement();
        statement.execute(initsql);
        LOGGER.info("CurseForge Table initialised");

        return !statement.execute("DELETE FROM curseforge WHERE guild_id = '" + guild_id + "' AND channelid = '" + channelid + "'");

    }

    public static List<CurseProjectItem> getCurseforgeCache() throws SQLException {
        List<CurseProjectItem> items = new ArrayList<>();
        // language=SQLite
        String initsql = "CREATE TABLE IF NOT EXISTS curseforge (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "guild_id VARCHAR(20) NOT NULL," +
                "projectid INTEGER NOT NULL," +
                "authorid VARCHAR(100) NOT NULL," +
                "channelid INTEGER NOT NULL," +
                "projecttitle VARCHAR(100) NOT NULL," +
                "projectver INTEGER NOT NULL" +
                ");";

        Statement statement = getConnection().createStatement();
        statement.execute(initsql);
        LOGGER.info("CurseForge Table initialised");

        ResultSet rs = statement.executeQuery("SELECT * FROM curseforge");
        while (rs.next()) {
            CurseProjectItem project = new CurseProjectItem(rs.getInt("id"), rs.getInt("projectid"), rs.getString("guild_id"), rs.getString("channelid"), rs.getString("authorid"), rs.getString("projecttitle"), rs.getInt("projectver"));
            items.add(project);
        }
        return items;
    }

    public static void updateCache(int ID, int version) throws SQLException {
        // language=SQLite
        String initsql = "CREATE TABLE IF NOT EXISTS curseforge (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "guild_id VARCHAR(20) NOT NULL," +
                "projectid INTEGER NOT NULL," +
                "authorid VARCHAR(100) NOT NULL," +
                "channelid INTEGER NOT NULL," +
                "projecttitle VARCHAR(100) NOT NULL," +
                "projectver INTEGER NOT NULL" +
                ");";

        Statement statement = getConnection().createStatement();
        statement.execute(initsql);
        LOGGER.info("CurseForge Table initialised");

        statement.executeUpdate("UPDATE curseforge SET projectver = '" + version + "' WHERE id = '" + ID + "'");
    }

}
