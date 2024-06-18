package fr.elowyr.boss;

import fr.elowyr.boss.commands.BossEpiqueCommand;
import fr.elowyr.boss.commands.BossLegendaireCommand;
import fr.elowyr.boss.commands.BossRareCommand;
import fr.elowyr.boss.entity.BossEpiqueKiller;
import fr.elowyr.boss.entity.BossLegendaireKiller;
import fr.elowyr.boss.entity.BossRareKiller;
import fr.elowyr.boss.listeners.EggListener;
import fr.elowyr.boss.server.ServerUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ElowyrBoss extends JavaPlugin {
    private static ElowyrBoss instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        ServerUtils.registerCustomEntity(BossRareKiller.class, "BossRareKiller", 54);
        ServerUtils.registerCustomEntity(BossEpiqueKiller.class, "BossEpiqueKiller", 54);
        ServerUtils.registerCustomEntity(BossLegendaireKiller.class, "BossLegendaireKiller", 54);

        this.getCommand("bossrare").setExecutor(new BossRareCommand());
        this.getCommand("bossepique").setExecutor(new BossEpiqueCommand());
        this.getCommand("bosslegendaire").setExecutor(new BossLegendaireCommand());
        this.getServer().getPluginManager().registerEvents(new EggListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static ElowyrBoss getInstance() {
        return instance;
    }

    public static void sendActionBar(final Player player, final String message) {
        sendPacket(player, new PacketPlayOutChat(fromText(message), (byte)2));
    }

    public static void sendPacket(final Player player, final Packet<?> packet) {
        if (player == null || !player.isOnline() || !(player instanceof CraftPlayer)) {
            return;
        }
        final PlayerConnection con = ((CraftPlayer)player).getHandle().playerConnection;
        if (con != null) {
            con.sendPacket(packet);
        }
    }

    private static IChatBaseComponent fromText(final String text) {
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}");
    }
}
