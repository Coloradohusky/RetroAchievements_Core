package coloradohusky.retroachievements_core;

import coloradohusky.retroachievements_core.commands.CommandRA;
import coloradohusky.retroachievements_core.api.IWorldInfoMixin;
import coloradohusky.retroachievements_core.handlers.PlayerEventHandler;
import coloradohusky.retroachievements_core.systems.NetworkSystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

import java.util.concurrent.ExecutionException;


@Mod(modid = RetroAchievements_Core.MODID, name = RetroAchievements_Core.NAME, version = RetroAchievements_Core.VERSION)
public class RetroAchievements_Core implements IMixinConnector {
    public static final String MODID = "retroachievements_core";
    public static final String NAME = "RetroAchievements Core";
    public static final String VERSION = "1.0";
    public static final Logger LOGGER = LogManager.getLogger(RetroAchievements_Core.MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Mixins.addConfiguration("mixins.retroachievements_core.json");
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info(RetroAchievements_Core.NAME + " says hi!");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRA());
        CommandRA.setLoginEventHandler(args -> {
            // Handle login logic
            System.out.println("Logging in user: " + args.username);
            try {
                NetworkSystem.login(args.username, args.password).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            // Call your login function here (e.g., send API request)
        });
        CommandRA.setLogoutEventHandler(() -> {
            // Handle logout logic
            System.out.println("User logged out");
            // Call your logout function here
        });
    }

    @Override
    public void connect() {
        Mixins.addConfiguration("mixins.retroachievements_core.json");
    }
}
