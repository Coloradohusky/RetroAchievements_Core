package coloradohusky.rajavaintegration.network.response;

import com.google.gson.annotations.SerializedName; /**
 * Base API response included in all API requests from RA.
 */
public class BaseResponse {
    @SerializedName("Success")
    private boolean success;

    @SerializedName("Error")
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
