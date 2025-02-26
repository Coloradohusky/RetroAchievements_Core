package coloradohusky.retroachievements_core.commands;

import coloradohusky.rajavaintegration.network.NetworkInterface;
import coloradohusky.rajavaintegration.network.response.ApiResponse;
import coloradohusky.rajavaintegration.network.response.LoginResponse;
import coloradohusky.retroachievements_core.RetroAchievements_Core;
import coloradohusky.retroachievements_core.config.RetroAchievements_Core_Config;
import coloradohusky.retroachievements_core.systems.RichPresenceSystem;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;
import java.util.function.Consumer;

public class CommandRA extends CommandBase {
    private static Consumer<LoginEventArgs> loginEventHandler;
    private static Runnable logoutEventHandler;

    @Override
    public String getName() {
        return "ra";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/ra <login|logout|rp> [args]";
    }

    public static void setLoginEventHandler(Consumer<LoginEventArgs> handler) {
        loginEventHandler = handler;
    }

    public static void setLogoutEventHandler(Runnable handler) {
        logoutEventHandler = handler;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(new TextComponentString("Usage: /ra <login|logout|rp> [args]"));
            return;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "login":
                if (args.length < 3) {
                    sender.sendMessage(new TextComponentString("Usage: /ra login <username> <password>"));
                    return;
                }
                String username = args[1];
                String password = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                RetroAchievements_Core_Config.username = username;
                if (loginEventHandler != null) {
                    loginEventHandler.accept(new LoginEventArgs(username, password));
                }
//                sender.sendMessage(new TextComponentString("Logging in " + username + "..."));
                net.minecraftforge.common.config.ConfigManager.sync(RetroAchievements_Core.MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
                break;

            case "logout":
                if (args.length != 1) {
                    sender.sendMessage(new TextComponentString("Usage: /ra logout"));
                    return;
                }
                RetroAchievements_Core_Config.apiKey = "";
//                net.minecraftforge.common.config.ConfigManager.sync(RetroAchievements_Core.MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
//                sender.sendMessage(new TextComponentString("Logged out successfully!"));
                break;

            case "rp":
                if (args.length != 1) {
                    sender.sendMessage(new TextComponentString("Usage: /ra rp"));
                    return;
                }
                sender.sendMessage(new TextComponentString("Current Rich Presence\n" + RichPresenceSystem.getRichPresence()));
                // MessageTool.ChatLog($"Current Rich Presence\n{RichPresenceSystem.GetRichPresence().Replace("• ", "\n")}");
                break;

            case "host":
                if (args.length != 1) {
                    sender.sendMessage(new TextComponentString("Usage: /ra host"));
                    return;
                }
                sender.sendMessage(new TextComponentString("The current RA host is " + RetroAchievements_Core_Config.host));
                break;

            default:
                sender.sendMessage(new TextComponentString("Unknown subcommand. Use /ra <login|logout|rp>"));
        }
    }
}
