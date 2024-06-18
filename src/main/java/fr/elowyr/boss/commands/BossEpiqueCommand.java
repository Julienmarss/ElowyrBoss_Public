package fr.elowyr.boss.commands;

import fr.elowyr.boss.ElowyrBoss;
import fr.elowyr.boss.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BossEpiqueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("elowyrboss.give")) return true;

        if (args.length == 0) {
            sender.sendMessage("§6§lElowyr §7◆ §c/bossepique <name> <number>.");
            return true;
        }

        try {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("§6§lElowyr §7◆ §cLe joeur renseigné n'est pas connecté.");
                return true;
            }

            int number = Integer.parseInt(args[1]);

            for (int i = 0; i < number; i++) {
                ItemStack itemStack = new ItemBuilder(Material.MONSTER_EGG, 1, (byte) 55)
                        .setName(ChatColor.translateAlternateColorCodes('&', ElowyrBoss.getInstance().getConfig().getString("boss.epique.name")))
                        .addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1)
                        .toItemStack();

                target.getInventory().addItem(itemStack);
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("§6§lElowyr §7◆ §cLe nombre renseigné n'est pas valide.");
            return true;
        }
        return true;
    }
}
