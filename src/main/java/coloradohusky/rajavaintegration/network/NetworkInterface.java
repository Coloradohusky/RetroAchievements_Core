package coloradohusky.rajavaintegration.network;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import coloradohusky.rajavaintegration.data.RA_Achievement;
import coloradohusky.rajavaintegration.network.response.ApiResponse;
import coloradohusky.rajavaintegration.network.response.AwardAchievementResponse;
import coloradohusky.rajavaintegration.network.response.AwardAchievementsResponse;
import coloradohusky.rajavaintegration.network.response.BaseResponse;
import coloradohusky.rajavaintegration.network.response.LoginResponse;
import coloradohusky.rajavaintegration.network.response.StartSessionResponse;
import coloradohusky.rajavaintegration.network.response.UploadAchievementResponse;
import okhttp3.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class NetworkInterface {

    private static final Gson gson = new Gson();

    /**
     * Try to login a user.
     */
    public static CompletableFuture<ApiResponse<LoginResponse>> tryLogin(OkHttpClient client, NetworkRequest.RequestHeader header, String pass) {
        URI requestUri = NetworkRequest.buildLoginRequest(header, pass);
        System.out.println(requestUri);
        return getApiResponse(client, requestUri, LoginResponse.class);
    }

    /**
     * Try to start a session for the game.
     */
    public static CompletableFuture<ApiResponse<StartSessionResponse>> tryStartSession(OkHttpClient client, NetworkRequest.RequestHeader header, int game) {
        URI requestUri = NetworkRequest.buildStartSessionRequest(header, game);
        return getApiResponse(client, requestUri, StartSessionResponse.class);
    }

    /**
     * Try to send an activity ping for the game.
     */
    public static CompletableFuture<ApiResponse<BaseResponse>> tryPing(OkHttpClient client, NetworkRequest.RequestHeader header, String rp) {
        RequestBody body = new FormBody.Builder().add("rp", rp).build();
        URI requestUri = NetworkRequest.buildPingRequest(header, rp);
        return getApiResponse(client, requestUri, BaseResponse.class, body);
    }

    /**
     * Try to award an achievement for the user.
     */
    public static CompletableFuture<ApiResponse<AwardAchievementResponse>> tryAwardAchievement(OkHttpClient client, NetworkRequest.RequestHeader header, int ach) {
        URI requestUri = NetworkRequest.buildAwardAchievementRequest(header, ach);
        return getApiResponse(client, requestUri, AwardAchievementResponse.class);
    }

    /**
     * Try to award a batch of achievements.
     */
    public static CompletableFuture<ApiResponse<AwardAchievementsResponse>> tryAwardAchievements(OkHttpClient client, NetworkRequest.RequestHeader header, List<Integer> achs) {
        RequestBody body = new FormBody.Builder().add("achievements", achs.toString()).build();
        URI requestUri = NetworkRequest.buildAwardAchievementsRequest(header, achs);
        return getApiResponse(client, requestUri, AwardAchievementsResponse.class, body);
    }

    /**
     * Try to upload a game achievement.
     */
    public static CompletableFuture<ApiResponse<UploadAchievementResponse>> tryUploadAchievement(OkHttpClient client, NetworkRequest.RequestHeader header, RA_Achievement ach) {
        URI requestUri = NetworkRequest.buildUploadAchievementRequest(header, ach);
        return getApiResponse(client, requestUri, UploadAchievementResponse.class);
    }

    /**
     * Sends an API request asynchronously and returns the parsed response.
     */
    private static <T> CompletableFuture<ApiResponse<T>> getApiResponse(OkHttpClient client, URI requestUri, Class<T> responseType) {
        return getApiResponse(client, requestUri, responseType, null);
    }

    /**
     * Sends an API request asynchronously with optional request body.
     */
    private static <T> CompletableFuture<ApiResponse<T>> getApiResponse(OkHttpClient client, URI requestUri, Class<T> responseType, RequestBody body) {
        CompletableFuture<ApiResponse<T>> future = new CompletableFuture<>();

        Request.Builder requestBuilder = new Request.Builder().url(requestUri.toString());
        if (body != null) {
            requestBuilder.post(body);
        }

        Request request = requestBuilder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                future.complete(ApiResponse.failed(e.getMessage()));
                future.complete(ApiResponse.failed(e.toString()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        future.complete(ApiResponse.failed("HTTP error: " + response.code()));
                        return;
                    }
                    String json = responseBody.string();
                    T data = gson.fromJson(json, responseType);
                    future.complete(ApiResponse.succeeded(data));
                } catch (JsonSyntaxException | IOException e) {
                    future.complete(ApiResponse.failed("Failed to parse JSON response: " + e.getMessage()));
                }
            }
        });

        return future;
    }
}
