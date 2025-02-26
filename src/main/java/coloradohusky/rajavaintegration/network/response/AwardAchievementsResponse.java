package coloradohusky.rajavaintegration.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List; /**
 * API response from an awardachievements request to RA.
 */
public class AwardAchievementsResponse extends BaseResponse {
    @SerializedName("Score")
    private int score;

    @SerializedName("SoftcoreScore")
    private int softScore;

    @SerializedName("ExistingIDs")
    private List<Integer> existingIds;

    @SerializedName("SuccessfulIDs")
    private List<Integer> successfulIds;

    public int getScore() {
        return score;
    }

    public int getSoftScore() {
        return softScore;
    }

    public List<Integer> getExistingIds() {
        return existingIds;
    }

    public List<Integer> getSuccessfulIds() {
        return successfulIds;
    }
}
