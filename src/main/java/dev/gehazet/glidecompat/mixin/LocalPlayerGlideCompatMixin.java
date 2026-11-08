package dev.gehazet.glidecompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * NeoOrigins natural_glide + Clockwork compatibility.
 *
 * Both NeoOrigins and Clockwork @Redirect ItemStack#canElytraFly inside LocalPlayer#aiStep,
 * so Mixin drops one of them (usually NeoOrigins) and origins like Elytrian can't start gliding.
 * This mixin uses MixinExtras @ModifyExpressionValue, which chains on top of whichever
 * redirect won, and additionally allows gliding when the player has the natural_glide capability.
 */
@Mixin(value = LocalPlayer.class, priority = 500)
public abstract class LocalPlayerGlideCompatMixin {

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z",
                     remap = false),
            remap = false,
            require = 0)
    private boolean glidecompat$allowNaturalGlide(boolean original) {
        if (original) return true;
        try {
            return com.cyberday1.neoorigins.client.ClientActivePowers.hasCapability("natural_glide");
        } catch (Throwable t) {
            return false;
        }
    }
}
