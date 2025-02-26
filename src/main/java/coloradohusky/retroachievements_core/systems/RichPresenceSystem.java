package coloradohusky.retroachievements_core.systems;

import coloradohusky.retroachievements_core.RetroAchievements_Core;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RichPresenceSystem {

    /**
     * Get the Rich Presence of the current game state
     * @return Rich presence string
     */
    public static String getRichPresence() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null) {
            return "Minecraft Main Menu";
        }

        EntityPlayerSP player = mc.player;
        World world = mc.world;

        String playerInfo = getPlayerInfo(player);
        String worldInfo = getWorldInfo(world);
        String biomeInfo = getBiomeInfo(world, player);

        String version = RetroAchievements_Core.VERSION;
        GameType gameMode = mc.playerController.getCurrentGameType();

        switch (gameMode) {
            case SPECTATOR:
                return I18n.format("menu.spectator_mode");

            case CREATIVE:
                return String.format("v%s • Playing Creative • %s • %s • %s", version, playerInfo, worldInfo, biomeInfo);

            case ADVENTURE:
                return String.format("v%s • Playing Adventure Mode • %s • %s • %s", version, playerInfo, worldInfo, biomeInfo);

            case SURVIVAL:
                return String.format("v%s • Playing Survival Mode • %s • %s • %s", version, playerInfo, worldInfo, biomeInfo);

            default:
                return "Playing Minecraft";
        }
    }

    /** Get player stats */
    private static String getPlayerInfo(EntityPlayerSP player) {
        String dimension = "none";
        try {
            dimension = Minecraft.getMinecraft().world.getWorldInfo().getDimensionData(player.dimension).getString("name");
            assert Minecraft.getMinecraft().getIntegratedServer() != null;
            for (WorldServer worldserver : Minecraft.getMinecraft().getIntegratedServer().worlds)  {
                if (worldserver != null) {
                    if (worldserver.provider.getDimension() == player.dimension) {
                        // todo: cleanup dimension names possibly
                        dimension = worldserver.provider.getDimensionType().getName();
                    }
                }
            }
        } catch (NullPointerException e) {
            dimension = "nope";
        }
        // WorldServer.getServer().worldServers[dimensionId].provider.getDimensionName()
        int health = Math.round(player.getHealth());
        int food = player.getFoodStats().getFoodLevel();
        String heldItem = player.getHeldItemMainhand().isEmpty() ? "Empty Handed" : player.getHeldItemMainhand().getDisplayName();
        return String.format("Player: Dimension: %s | %d HP | %d Hunger | Holding: %s", dimension, health, food, heldItem);
    }

    /** Get world details */
    private static String getWorldInfo(World world) {
        // long seed = world.getSeed(); // todo fix
        String difficulty = world.getDifficulty() == EnumDifficulty.PEACEFUL ? "Peaceful" : world.getDifficulty() == EnumDifficulty.EASY ? "Easy" : world.getDifficulty() == EnumDifficulty.NORMAL ? "Normal" : "Hard";
        String time = world.isDaytime() ? "Day" : "Night";

        return String.format("World: Difficulty: %s | Time: %s", difficulty, time);
    }

    /** Get the biome info */
    private static String getBiomeInfo(World world, EntityPlayerSP player) {
        Biome biome = world.getBiome(player.getPosition());
        return String.format("Biome: %s", biome.getBiomeName());
    }
}

