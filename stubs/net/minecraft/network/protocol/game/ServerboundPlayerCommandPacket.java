package net.minecraft.network.protocol.game;
public class ServerboundPlayerCommandPacket implements net.minecraft.network.protocol.Packet<Object> {
  public ServerboundPlayerCommandPacket(net.minecraft.world.entity.Entity e, Action a) {}
  public enum Action { START_FALL_FLYING }
}
