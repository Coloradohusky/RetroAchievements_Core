package coloradohusky.retroachievements_core.achievements;

public class MinecraftAchievementData {
        /// <summary>
    /// Game-related information
    /// </summary>
    private MinecraftAchievementGame Game;

    /// <summary>
    /// List of all achievements
    /// </summary>
    private MinecraftAchievement[] Achievements;

    public MinecraftAchievementGame getGame() {
        return Game;
    }

    public void setGame(MinecraftAchievementGame game) {
        this.Game = game;
    }

    public MinecraftAchievement[] getAchievements() {
        return Achievements;
    }

    public void setAchievements(MinecraftAchievement[] achievements) {
        this.Achievements = achievements;
    }
}
