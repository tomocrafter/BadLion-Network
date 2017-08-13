package net.badlion.uhc.listeners.gamemodes;

import net.badlion.combattag.events.CombatTagKilledEvent;
import net.badlion.uhc.BadlionUHC;
import net.badlion.uhc.events.PlayerDeathItemEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CoallessGameMode implements GameMode {

    private Random random = new Random();

    public ItemStack getExplanationItem() {
        ItemStack item = new ItemStack(Material.COAL);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Coalless");

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.AQUA + "- You cannot mine coal");
        lore.add(ChatColor.AQUA + "- You cannot blast mine coal");
        lore.add(ChatColor.AQUA + "- Everytime you kill someone they drop 2 coal");

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    public String getAuthor() {
        return "MaccaTacca";
    }

    @EventHandler
    public void onCoalBroken(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.COAL_ORE) {
            event.setCancelled(true);

	        event.getBlock().setType(Material.AIR);

            // Give EXP still
            BadlionUHC.getInstance().createExpOrb(event.getBlock().getLocation(), this.random.nextInt(3));
        }
    }

    @EventHandler
    public void onCoalBlastMined(EntityExplodeEvent event) {
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if (block.getType() == Material.COAL_ORE) {
                block.setType(Material.AIR);
                it.remove();
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
	    if (BadlionUHC.getInstance().getState() != BadlionUHC.BadlionUHCState.STARTED) return;

        ItemStack[] itemStacks = new ItemStack[] {new ItemStack(Material.COAL, 2)};

        for (ItemStack itemStack : itemStacks) {
            PlayerDeathItemEvent playerDeathItemEvent = new PlayerDeathItemEvent(itemStack);
            BadlionUHC.getInstance().getServer().getPluginManager().callEvent(playerDeathItemEvent);

            if (!playerDeathItemEvent.isCancelled()) {
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), playerDeathItemEvent.getItemStack());
            }
        }
    }

    @EventHandler
    public void onCombatLoggerDeath(CombatTagKilledEvent event) {
        Zombie zombie = event.getLoggerNPC().getEntity();

        ItemStack[] itemStacks = new ItemStack[] {new ItemStack(Material.COAL, 2)};

        for (ItemStack itemStack : itemStacks) {
            PlayerDeathItemEvent playerDeathItemEvent = new PlayerDeathItemEvent(itemStack);
            BadlionUHC.getInstance().getServer().getPluginManager().callEvent(playerDeathItemEvent);

            if (!playerDeathItemEvent.isCancelled()) {
                zombie.getWorld().dropItemNaturally(zombie.getLocation(), playerDeathItemEvent.getItemStack());
            }
        }
    }

    @Override
    public void unregister() {
        BlockBreakEvent.getHandlerList().unregister(this);
        EntityExplodeEvent.getHandlerList().unregister(this);
        PlayerDeathEvent.getHandlerList().unregister(this);
        CombatTagKilledEvent.getHandlerList().unregister(this);
    }
}
