package coloradohusky.retroachievements_core.systems;

import coloradohusky.rajavaintegration.network.response.ApiResponse;
import coloradohusky.rajavaintegration.network.response.LoginResponse;
import coloradohusky.rajavaintegration.network.NetworkInterface;
import coloradohusky.rajavaintegration.network.NetworkRequest;
import coloradohusky.retroachievements_core.RetroAchievements_Core;

import coloradohusky.retroachievements_core.config.RetroAchievements_Core_Config;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import okhttp3.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NetworkSystem {
    private static OkHttpClient client = new OkHttpClient();
    private static final NetworkRequest.RequestHeader header = new NetworkRequest.RequestHeader();

    private static void setupUserAgent() {
        // RA requires a user agent of {Standalone name}/{x.y.z Version}
        client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request requestWithUserAgent = original.newBuilder()
                            .header("User-Agent", "MinecraftRetroAchievements/" + RetroAchievements_Core.VERSION)
                            .build();
                    return chain.proceed(requestWithUserAgent);
                }).build();
    }

    public static CompletableFuture<Void> login(String user, String pass) {
        setupUserAgent();
        return CompletableFuture.runAsync(() -> {
            header.user = user;
            header.host = RetroAchievements_Core_Config.host;
            CompletableFuture<ApiResponse<LoginResponse>> apiResponse = NetworkInterface.tryLogin(client, header, pass);
            try {
                if (!apiResponse.get().isSuccess()) {
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Unable to login: " + apiResponse.get().getFailure()));
                    return;
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            LoginResponse response = null;
            try {
                response = apiResponse.get().getResponse();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (!response.isSuccess()) {
                try {
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Unable to login: " + apiResponse.get().getFailure()));
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            header.token = response.getToken();
            RetroAchievements_Core_Config.username = header.user;
            RetroAchievements_Core_Config.apiKey = header.token;
            net.minecraftforge.common.config.ConfigManager.sync(RetroAchievements_Core.MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(user + " has successfully logged in!"));
//          todo Trigger session start (this should match your C# event system)
//            startSession();
        });
    }
}
