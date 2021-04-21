package me.hypherionmc.claptrap.util.databasehelpers;

public class CurseProjectItem {

    private final Integer dbID;
    private final Integer projectID;
    private final String guild_id;
    private final String channelID;
    private final String authorID;
    private final String projectTitle;
    private final Integer version;

    /***
     * Create a new CurseForge Project Database Item
     * @param id - Item ID (Set to 0 for new items)
     * @param projectID - ID of the CurseForge Project
     * @param guild_id - Discord Guild ID
     * @param channelID - Discord Channel ID
     * @param authorID - Discord Author ID
     * @param projectTitle - CurseForge project Title
     * @param version - CurseForge Project Version
     */
    public CurseProjectItem(Integer id, Integer projectID, String guild_id, String channelID, String authorID, String projectTitle, Integer version) {
        this.dbID = id;
        this.projectID = projectID;
        this.guild_id = guild_id;
        this.channelID = channelID;
        this.authorID = authorID;
        this.projectTitle = projectTitle;
        this.version = version;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public Integer getDbID() {
        return dbID;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public Integer getVersion() {
        return version;
    }
}
