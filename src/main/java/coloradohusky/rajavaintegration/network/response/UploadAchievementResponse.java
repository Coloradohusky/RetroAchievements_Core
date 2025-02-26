package coloradohusky.rajavaintegration.network.response;

import com.google.gson.annotations.SerializedName; /**
 * API response from an uploadachievement request to RA.
 */
public class UploadAchievementResponse extends BaseResponse {
    @SerializedName("AchievementID")
    private int achId;

    public int getAchId() {
        return achId;
    }
}
