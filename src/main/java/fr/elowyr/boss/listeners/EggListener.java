package fr.elowyr.boss.listeners;

import fr.elowyr.boss.ElowyrBoss;
import fr.elowyr.boss.entity.BossEpiqueKiller;
import fr.elowyr.boss.entity.BossLegendaireKiller;
import fr.elowyr.boss.entity.BossRareKiller;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EggListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (itemStack == null || itemStack.getType() != Material.MONSTER_EGG) return;

        if (!itemStack.hasItemMeta()) return;

        if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',
                ElowyrBoss.getInstance().getConfig().getString("boss.rare.name")))) {

            event.setCancelled(true);

            if (itemStack.getAmount() > 1)
                itemStack.setAmount(itemStack.getAmount() - 1);
            else player.getInventory().setItemInHand(new ItemStack(Material.AIR));

            BossRareKiller.spawn(event.getClickedBlock().getLocation().add(0, 1, 0));
        }

        if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',
                ElowyrBoss.getInstance().getConfig().getString("boss.epique.name")))) {

            event.setCancelled(true);

            if (itemStack.getAmount() > 1)
                itemStack.setAmount(itemStack.getAmount() - 1);
            else player.getInventory().setItemInHand(new ItemStack(Material.AIR));

            BossEpiqueKiller.spawn(event.getClickedBlock().getLocation().add(0, 1, 0));
        }

        if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',
                ElowyrBoss.getInstance().getConfig().getString("boss.legendaire.name")))) {

            event.setCancelled(true);

            if (itemStack.getAmount() > 1)
                itemStack.setAmount(itemStack.getAmount() - 1);
            else player.getInventory().setItemInHand(new ItemStack(Material.AIR));

            BossLegendaireKiller.spawn(event.getClickedBlock().getLocation().add(0, 1, 0));
        }
    }

}
