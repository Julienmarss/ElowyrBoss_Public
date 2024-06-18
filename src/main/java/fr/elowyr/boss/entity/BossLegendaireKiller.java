package fr.elowyr.boss.entity;

import fr.elowyr.boss.ElowyrBoss;
import fr.elowyr.boss.fireworks.InstantFirework;
import fr.elowyr.boss.items.CustomHeads;
import fr.elowyr.boss.items.ItemBuilder;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BossLegendaireKiller extends EntityZombie {

    BossLegendaireKiller(World world) {
        super(((CraftWorld) world).getHandle());
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();

        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(15.0D);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(1500.D);

        this.setCustomName(ChatColor.translateAlternateColorCodes('&', ElowyrBoss.getInstance().getConfig().getString("boss.legendaire.name")));
        this.setCustomNameVisible(true);
        this.setBaby(false);
        this.setVillager(false);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.40D);
    }

    @Override
    public void die(DamageSource damageSource) {

        List<String> commands = new ArrayList<>();

        this.getBukkitEntity().getWorld().getEntities().remove(this.getBukkitEntity());

        for (int i = 0; i < 1; i++) {
            commands.add(ElowyrBoss.getInstance().getConfig().getStringList("items.legendaire")
                    .get(ThreadLocalRandom.current().nextInt(ElowyrBoss.getInstance().getConfig().getStringList("items.legendaire").size())));
        }

        commands.forEach(line -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), line.replace("%player%", killer.getName()));
        });

        InstantFirework.spawn(this.getBukkitEntity().getLocation(), FireworkEffect.builder().withColor(Color.GREEN, Color.RED, Color.WHITE).build());
        this.getBukkitEntity().remove();
        super.die(damageSource);

        Bukkit.broadcastMessage(
                ChatColor.translateAlternateColorCodes(
                        '&', ElowyrBoss.getInstance().getConfig().getString("boss.legendaire.killed-broadcast").replace("%killer%", killer.getName())));
    }

    @Override
    protected void dropDeathLoot(boolean flag, int i) {

    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (!this.isAlive()) return false;
        if (damagesource.getEntity() instanceof EntityPlayer) {
            int heal = (int) this.getHealth();
            int healMax = (int) this.getMaxHealth();

            Player player = (Player) damagesource.getEntity().getBukkitEntity();
            ElowyrBoss.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', ElowyrBoss.getInstance().getConfig().getString("boss.legendaire.life")
                    .replace("%heal%", String.valueOf(heal)).replace("%healmax%", String.valueOf(healMax))));
        }
        return super.damageEntity(damagesource, f);
    }

    public static Zombie spawn(Location location) {
        net.minecraft.server.v1_8_R3.World world = ((CraftWorld) location.getWorld()).getHandle();
        BossLegendaireKiller bossEpiqueKiller = new BossLegendaireKiller(world.getWorld());

        bossEpiqueKiller.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(bossEpiqueKiller, CreatureSpawnEvent.SpawnReason.CUSTOM);

        ItemStack helmet = CustomHeads.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZGJjNTQ1MTEzYWQ5NmE2MGEzZjA1OTE5NTc0Zjc3MDljMjc2YzlkODQ2YzJhMDFiMDFjMzgyN2YyNWQ5MiJ9fX0=");
        ItemStack chestPlate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setLeatherArmorColor(Color.RED)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .toItemStack();

        ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setLeatherArmorColor(Color.RED)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .toItemStack();

        ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                .setLeatherArmorColor(Color.RED)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .toItemStack();

        ItemStack hand = new ItemBuilder(Material.DIAMOND_SWORD)
                .addUnsafeEnchantment(Enchantment.KNOCKBACK, 3)
                .addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1)
                .toItemStack();

        bossEpiqueKiller.setEquipment(0, CraftItemStack.asNMSCopy(hand));
        bossEpiqueKiller.setEquipment(4, CraftItemStack.asNMSCopy(helmet));
        bossEpiqueKiller.setEquipment(3, CraftItemStack.asNMSCopy(chestPlate));
        bossEpiqueKiller.setEquipment(2, CraftItemStack.asNMSCopy(leggings));
        bossEpiqueKiller.setEquipment(1, CraftItemStack.asNMSCopy(boots));

        bossEpiqueKiller.getAttributeInstance(GenericAttributes.maxHealth).setValue(1500.0D);
        bossEpiqueKiller.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(13.0D);

        bossEpiqueKiller.setHealth(1500);

        return (Zombie) bossEpiqueKiller.getBukkitEntity();

    }
}
