package coloradohusky.retroachievements_core.config;

import coloradohusky.retroachievements_core.RetroAchievements_Core;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static coloradohusky.retroachievements_core.RetroAchievements_Core.MODID;

@Mod.EventBusSubscriber(modid = RetroAchievements_Core.MODID)
@Config(modid = MODID, name = "RetroAchievements") // will be used by all subsets as well
public class RetroAchievements_Core_Config {
//    @Config.RequiresMcRestart
    @Config.Name("Challenge Mode")
    @Config.Comment("Enable challenge mode (also known as hardcore mode)")
    public static boolean challengeMode = true;

    @Config.Comment("RetroAchievements Username")
    @Config.Name("Username")
    public static String username = "";

    @Config.Comment("RetroAchievements API Key")
    @Config.Name("API Key")
    public static String apiKey = "";

    // add list of subsets

//    @Config.RequiresMcRestart
    @Config.Name("Host")
    @Config.Comment("Host")
    public static String host = "retroachievements.org";

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(RetroAchievements_Core.MODID)) {
            ConfigManager.sync(RetroAchievements_Core.MODID, Config.Type.INSTANCE);
        }
    }
}
