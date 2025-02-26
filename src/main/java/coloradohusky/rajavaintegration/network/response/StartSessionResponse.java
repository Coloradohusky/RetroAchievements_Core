package coloradohusky.rajavaintegration.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List; /**
 * API response from a startsession request to RA.
 */
public class StartSessionResponse extends BaseResponse {
    @SerializedName("ServerNow")
    private int serverTime;

    @SerializedName("HardcoreUnlocks")
    private List<AchievementUnlock> hardcoreUnlocks;

    @SerializedName("Unlocks")
    private List<AchievementUnlock> softcoreUnlocks;

    public int getServerTime() {
        return serverTime;
    }

    public List<AchievementUnlock> getHardcoreUnlocks() {
        return hardcoreUnlocks;
    }

    public List<AchievementUnlock> getSoftcoreUnlocks() {
        return softcoreUnlocks;
    }

    public List<Integer> getUnlockedAchIds(boolean hardcore) {
        List<Integer> unlockedAchs = new java.util.ArrayList<>();
        List<AchievementUnlock> unlocks = hardcore ? hardcoreUnlocks : softcoreUnlocks;

        if (unlocks != null) {
            for (AchievementUnlock unlock : unlocks) {
                if (!hardcore && unlock.id == 101000001) continue; // Ignore outdated warning
                unlockedAchs.add(unlock.id);
            }
        }

        return unlockedAchs;
    }

    static class AchievementUnlock {
        @SerializedName("ID")
        private int id;

        @SerializedName("When")
        private int when;
    }
}
