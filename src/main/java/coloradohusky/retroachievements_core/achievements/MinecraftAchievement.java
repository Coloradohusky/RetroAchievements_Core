package coloradohusky.retroachievements_core.achievements;

import coloradohusky.rajavaintegration.data.RA_Achievement;

public class MinecraftAchievement {
    /// <summary>
    /// Internal name of the achievement (BENCHED, etc.)
    /// </summary>
    private String Name;

    /// <summary>
    /// RA-related achievement information
    /// </summary>
    private RA_Achievement RA;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public RA_Achievement getRA() {
        return RA;
    }

    public void setRA(RA_Achievement RA) {
        this.RA = RA;
    }
}
