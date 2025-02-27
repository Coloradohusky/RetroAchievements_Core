package coloradohusky.retroachievements_core.mixins;

import coloradohusky.retroachievements_core.api.IWorldInfoMixin;
import coloradohusky.retroachievements_core.config.RetroAchievements_Core_Config;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldInfo.class)
public class WorldInfoMixin implements IWorldInfoMixin {
    private boolean challengeModeEnabled;
    @Shadow
    public boolean allowCommands;
    @Shadow
    public EnumDifficulty difficulty;
    @Shadow
    private GameType gameType;
    @Shadow
    private boolean mapFeaturesEnabled;
    @Shadow
    private WorldType terrainType;
    @Shadow
    private GameRules gameRules;

    @Override
    public boolean isChallengeModeEnabled() {
        return challengeModeEnabled;
    }

    @Override
    public void setChallengeModeEnabled(boolean enabled) {
        challengeModeEnabled = enabled;
    }

    @Unique
    public boolean checkChallengeModeConditions() {
        boolean conditions = RetroAchievements_Core_Config.challengeMode;
        if (conditions) {
            if (allowCommands) {
                System.out.println("[RetroAchievements] Disabled Challenge Mode: Commands Enabled");
                conditions = false;
            } else if (difficulty.equals(EnumDifficulty.PEACEFUL)) {
                System.out.println("[RetroAchievements] Disabled Challenge Mode: Difficulty is Peaceful");
                conditions = false;
            } else if (gameType != GameType.SURVIVAL) {
                System.out.println("[RetroAchievements] Disabled Challenge Mode: Game Type is not Survival");
                conditions = false;
            } else if (terrainType.equals(WorldType.FLAT) || terrainType.equals(WorldType.CUSTOMIZED) || terrainType.equals(WorldType.DEBUG_ALL_BLOCK_STATES)) {
                System.out.println("[RetroAchievements] Disabled Challenge Mode: Disallowed Terrain Type");
                conditions = false;
            } else if (!mapFeaturesEnabled) {
                System.out.println("[RetroAchievements] Disabled Challenge Mode: Map Features are disabled");
                conditions = false;
            }
        } else {
            System.out.println("[RetroAchievements] Disabled Challenge Mode: Disabled in config settings");
        }
        return conditions;
    }

    // private void updateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt)
    @Inject(method = "updateTagCompound", at = @At("HEAD"))
    protected void onUpdateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt, CallbackInfo ci) {
        nbt.setBoolean("ChallengeModeEnabled", this.challengeModeEnabled);
        System.out.println("[RetroAchievements] Saved Challenge Mode: " + this.challengeModeEnabled);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/WorldSettings;Ljava/lang/String;)V", at = @At("RETURN"))
    protected void onWorldInfo(WorldSettings settings, String name, CallbackInfo ci) {
        // possibly add challenge mode to WorldSettings? todo
        this.challengeModeEnabled = checkChallengeModeConditions();
        System.out.println("[RetroAchievements] Loaded Challenge Mode: " + this.challengeModeEnabled);
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/NBTTagCompound;)V", at = @At("RETURN"))
    protected void onWorldInfo(NBTTagCompound nbt, CallbackInfo ci) {
        this.challengeModeEnabled = RetroAchievements_Core_Config.challengeMode;
        if (nbt.hasKey("ChallengeModeEnabled", 99))
        {
            this.challengeModeEnabled = nbt.getBoolean("ChallengeModeEnabled");
        } else {
            System.out.println("[RetroAchievements] Disabled Challenge Mode: Not Found in NBT");
            this.challengeModeEnabled = false;
        }
        if (this.challengeModeEnabled) {
            this.challengeModeEnabled = checkChallengeModeConditions();
        }
        System.out.println("[RetroAchievements] Loaded Challenge Mode from NBT: " + this.challengeModeEnabled);
    }
}
