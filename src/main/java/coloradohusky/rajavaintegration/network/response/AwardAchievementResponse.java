package coloradohusky.rajavaintegration.network.response;

import com.google.gson.annotations.SerializedName; /**
 * API response from an awardachievement request to RA.
 */
public class AwardAchievementResponse extends BaseResponse {
    @SerializedName("AchievementsRemaining")
    private int achsRemaining;

    @SerializedName("AchievementID")
    private int achId;

    @SerializedName("Score")
    private int score;

    @SerializedName("SoftcoreScore")
    private int softScore;

    public int getAchsRemaining() {
        return achsRemaining;
    }

    public int getAchId() {
        return achId;
    }

    public int getScore() {
        return score;
    }

    public int getSoftScore() {
        return softScore;
    }
}
