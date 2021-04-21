package me.hypherionmc.claptrap.util.databasehelpers;

public class CurseCacheItem {

    private final Integer dbID;
    private final Integer projectID;
    private final String guild_id;
    private final String channelID;
    private final Integer version;

    public CurseCacheItem(Integer id, Integer projectID, String guild_id, String channelID, int version) {
        this.dbID = id;
        this.projectID = projectID;
        this.guild_id = guild_id;
        this.channelID = channelID;
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


    public String getChannelID() {
        return channelID;
    }

    public Integer getVersion() {
        return version;
    }
}
