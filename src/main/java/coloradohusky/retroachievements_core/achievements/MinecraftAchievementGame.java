package coloradohusky.retroachievements_core.achievements;

import coloradohusky.rajavaintegration.data.RA_Game;

import java.util.Dictionary;

public class MinecraftAchievementGame {
    /// <summary>
    /// List of all mods that are achievement subsets
    /// </summary>
    private Dictionary<String, String> SubsetMods;

    /// <summary>
    /// List of all mods that are allowed with this game
    /// </summary>
    private String[] WhitelistedMods;

    /// <summary>
    /// RA-related game information
    /// </summary>
    private RA_Game RA;

    public Dictionary<String, String> getSubsetMods() {
        return SubsetMods;
    }

    public void setSubsetMods(Dictionary<String, String> subsetMods) {
        SubsetMods = subsetMods;
    }

    public String[] getWhitelistedMods() {
        return WhitelistedMods;
    }

    public void setWhitelistedMods(String[] whitelistedMods) {
        this.WhitelistedMods = whitelistedMods;
    }

    public RA_Game getRA() {
        return RA;
    }

    public void setRA(RA_Game RA) {
        this.RA = RA;
    }
}
