package beasthunter.beasthunter;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerUtility {

    private static  Map<UUID, Quest> playerMemory = new HashMap<>();
    private Main main;

    public PlayerUtility(Main main) {
        this.main = main;
    }


    public static Quest getPlayerMemory(Player p) {
        if (!playerMemory.containsKey(p.getUniqueId())) {
            Quest ps = new Quest();
            playerMemory.put(p.getUniqueId(), ps);
            return ps;
        }
        return playerMemory.get(p.getUniqueId());
    }

        public static void setPlayerMemory(Player p, Quest ps) {
        if (ps == null) {
            playerMemory.remove(p.getUniqueId());
        }
        else playerMemory.put(p.getUniqueId(), ps);
    }
}
