package net.badlion.cosmetics.morphs;

import net.badlion.common.libraries.EnumCommon;
import net.badlion.cosmetics.Cosmetics;
import net.badlion.cosmetics.utils.MorphUtil;
import net.badlion.gberry.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PigMorph extends Morph {

    public Map<UUID, Long> lastPigSneakTimes = new HashMap<>();

    public PigMorph() {
        super("pig_morph", ItemRarity.COMMON, ItemStackUtil.createItem(Material.MONSTER_EGG, 1, (byte) 90, ChatColor.GREEN + "Pig Morph",
                Arrays.asList(ChatColor.GRAY + "Left click to oik.", ChatColor.GRAY + "Right click to vomit pork.")));
        this.morphType = MorphUtil.MorphType.PIG;

    }

    @Override
    protected void handleLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), EnumCommon.getEnumValueOf(Sound.class, "PIG_IDLE", "ENTITY_PIG_AMBIENT"), 1.0f, 1.0f);
    }

    @Override
    protected void handleRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (lastPigSneakTimes.containsKey(player.getUniqueId()) && System.currentTimeMillis() - lastPigSneakTimes.get(player.getUniqueId()) <= 1000 * 2) {
            player.sendMessage(ChatColor.RED + "Please wait " + (2 - (Math.round(System.currentTimeMillis() - lastPigSneakTimes.get(player.getUniqueId()))) / 1000) + " seconds to do this again.");
            return;
        }
        final Item pork = player.getWorld().dropItem(player.getLocation().add(0.0D, 0.5D, 0.0D), new ItemStack(Material.PORK));
        pork.setMetadata("takeable", new FixedMetadataValue(Cosmetics.getInstance(), "takeable"));
        pork.setVelocity(player.getLocation().getDirection());
        player.playSound(player.getLocation(), EnumCommon.getEnumValueOf(Sound.class, "BURP", "ENTITY_PLAYER_BURP"), 1.0f, 1.0f);
        Bukkit.getScheduler().runTaskLater(Cosmetics.getInstance(), new Runnable() {
            @Override
            public void run() {
                pork.remove();
            }
        }, 20L * 2);

        lastPigSneakTimes.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @Override
    public void setMorph(Player player) {
        new MorphUtil(MorphUtil.MorphType.PIG, player).sendServerSetMorph();
    }
}
