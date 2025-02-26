package coloradohusky.rajavaintegration.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * API response from a login2 request to RA.
 */
public class LoginResponse extends BaseResponse {
    @SerializedName("Messages")
    private int messages;

    @SerializedName("Permissions")
    private int perms;

    @SerializedName("Score")
    private int score;

    @SerializedName("SoftcoreScore")
    private int softScore;

    @SerializedName("AccountType")
    private String accountType;

    @SerializedName("Token")
    private String token;

    @SerializedName("User")
    private String user;

    public int getMessages() {
        return messages;
    }

    public int getPerms() {
        return perms;
    }

    public int getScore() {
        return score;
    }

    public int getSoftScore() {
        return softScore;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getToken() {
        return token;
    }

    public String getUser() {
        return user;
    }
}
