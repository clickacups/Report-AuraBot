package main;


import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class NPC {

	private MinecraftServer server;
	private WorldServer world;
	private GameProfile gameProfile;
	private EntityPlayer entityPlayer; 
	
	
	public NPC(World world, String name) {
		
		this.server = ((CraftServer) Bukkit.getServer()).getServer();
		this.world = ((CraftWorld) Bukkit.getWorld(world.getName())).getHandle();
		this.gameProfile = new GameProfile(UUID.randomUUID(), name);
		
		
	}
	

	public void setLocation(Location location) {
		this.entityPlayer.setLocation(location.getX(), location.getY(), location.getZ()
				, location.getYaw(), location.getPitch());
		
	}
	
	public void spawn(Location location) {
		
		this.entityPlayer = new EntityPlayer(this.server, this.world, this.gameProfile, new PlayerInteractManager(this.world));
		this.entityPlayer.setLocation(location.getX(), location.getY(), location.getZ()
				, location.getYaw(), location.getPitch());
		
	}
	
	public void show(Player player) {
		

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.entityPlayer));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entityPlayer));
		connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.entityPlayer, (byte) (this.entityPlayer.yaw * 256 / 360)));
	}
	
	public void hide(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		
		PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(this.entityPlayer.getId());
		
		connection.sendPacket(destroyPacket);
		
	}
	
	
	
}
