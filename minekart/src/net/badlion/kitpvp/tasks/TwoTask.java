package net.badlion.kitpvp.tasks;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.tinywebteam.badlion.MineKart;

public class TwoTask extends BukkitRunnable {
	
	private MineKart plugin;
	private ArrayList<Player> players;
	
	public TwoTask(MineKart plugin, ArrayList<Player> players) {
		this.plugin = plugin;
		this.players = players;
	}
	
	@Override
	public void run() {
		for (Player player : this.players) {
			player.sendMessage(ChatColor.GREEN + "2");
			player.playSound(player.getLocation(), Sound.ARROW_HIT, 1, 1);
		}
	}

}
