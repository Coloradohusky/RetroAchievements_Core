package coloradohusky.retroachievements_core.handlers;

import coloradohusky.retroachievements_core.api.IWorldInfoMixin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerEventHandler {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        World world = player.world;
        WorldInfo worldInfo = world.getWorldInfo();

        boolean challengeModeEnabled = ((IWorldInfoMixin) worldInfo).isChallengeModeEnabled();
        String message = challengeModeEnabled
                ? TextFormatting.GREEN + "Challenge Mode is ENABLED for this world!"
                : TextFormatting.RED + "Challenge Mode is DISABLED for this world.";

        player.sendMessage(new TextComponentString(message));
    }
}
