package fr.elowyr.boss.server;

import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.lang.reflect.Field;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class ServerUtils {

    public static Set<SpawnReason> ALLOWED_SPAWN_REASONS = EnumSet.of(SpawnReason.SPAWNER_EGG, SpawnReason.EGG,
        SpawnReason.SPAWNER, SpawnReason.BREEDING, SpawnReason.CUSTOM, SpawnReason.SLIME_SPLIT);

    public static void registerCustomEntity(Class<?> clazz, String name, int id) {
        setEntityTypesField("d", clazz, name);
        setEntityTypesField("f", clazz, id);
    }

    private static void setEntityTypesField(String fieldName, Object value1, Object value2) {
        try {
            Field field = EntityTypes.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            Map map = (Map) field.get(null);
            map.put(value1, value2);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void changeServerSlots(int amount) {
        PlayerList playerList = ((CraftServer) Bukkit.getServer()).getHandle();

        try {
            Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");
            maxPlayersField.setAccessible(true);

            maxPlayersField.set(playerList, amount);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
