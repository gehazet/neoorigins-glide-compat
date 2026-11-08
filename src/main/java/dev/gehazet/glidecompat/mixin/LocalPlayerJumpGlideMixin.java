package dev.gehazet.glidecompat.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * For NeoOrigins natural gliders only: jump mid-air always starts gliding, even when another mod (e.g. FlightLib / Create Jetpack
 * "Active Elytra" keybind) took over the vanilla jump check. Runs at the end of aiStep, after
 * vanilla and other mods, and repeats vanilla's own start-gliding logic only if gliding has
 * not started yet. tryToStartFallFlying() still does all the usual checks (ground, water,
 * elytra / NeoOrigins natural glide / Clockwork wings), so nothing new is unlocked.
 */
@Mixin(value = LocalPlayer.class, priority = 1500)
public abstract class LocalPlayerJumpGlideMixin {

    @Unique
    private boolean glidecompat$wasJumping;

    @Inject(method = "aiStep", at = @At(value = "HEAD", remap = false), remap = false, require = 0)
    private void glidecompat$rememberJump(CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        glidecompat$wasJumping = self.input != null && self.input.jumping;
    }

    @Inject(method = "aiStep", at = @At(value = "RETURN", remap = false), remap = false, require = 0)
    private void glidecompat$jumpStartsGlide(CallbackInfo ci) {
        try {
            LocalPlayer self = (LocalPlayer) (Object) this;
            if (self.input == null || !self.input.jumping || glidecompat$wasJumping) return;
            if (self.onGround() || self.isFallFlying() || self.isPassenger() || self.onClimbable()) return;
            if (self.getAbilities().flying) return;
            // Only for NeoOrigins natural gliders (Elytrian, Phantom...). Players using a normal
            // elytra or Clockwork wings keep the default behaviour of Create Jetpack / vanilla.
            if (!com.cyberday1.neoorigins.client.ClientActivePowers.hasCapability("natural_glide")) return;
            if (self.tryToStartFallFlying()) {
                self.connection.send(new ServerboundPlayerCommandPacket(self, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
            }
        } catch (Throwable ignored) {
        }
    }
}
